/**
 * 
 */
package org.iqra.operationsapp.dynadmin.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.client.ClientProtocolException;
import org.iqra.operationsapp.dynadmin.props.ATGApplicationsProps;
import org.iqra.operationsapp.dynadmin.service.DynAdminOperationsService;
import org.iqra.operationsapp.dynadmin.util.CacheInvalidator;
import org.iqra.operationsapp.dynadmin.util.CallableCacheInvalidator;
import org.iqra.operationsapp.dynadmin.util.CallableComponentValueGetter;
import org.iqra.operationsapp.dynadmin.util.CallableComponetValueSetter;
import org.iqra.operationsapp.dynadmin.util.CallableMethodInvoker;
import org.iqra.operationsapp.dynadmin.util.ComponentValueGetter;
import org.iqra.operationsapp.dynadmin.util.ComponetValueSetter;
import org.iqra.operationsapp.dynadmin.util.MethodInvoker;
import org.iqra.operationsapp.email.service.EmailService;
import org.iqra.operationsapp.entity.DashboardInfo;
import org.iqra.operationsapp.entity.DynAdminInfo;
import org.iqra.operationsapp.helper.PopulateDetailsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Abdul 06-Mar-2018
 * 
 */
@Service
public class DynAdminOperationsServiceImpl implements DynAdminOperationsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PopulateDetailsHelper populateDetailsHelper;

	@Autowired
	private ComponetValueSetter componetValueSetter;

	@Autowired
	private ComponentValueGetter componentValueGetter;

	@Autowired
	private CacheInvalidator cacheInvalidator;

	@Autowired
	private MethodInvoker methodInvoker;

	@Autowired
	private EmailService emailService;

	@Override
	public DynAdminInfo setComponentValues(DynAdminInfo dynAdminInfo) {

		logger.debug("Begin : DynAdminOperationsService --> setComponentValues()");
		long started = System.currentTimeMillis();
		String errors = new String();
		String errorsforEmail = new String();
		String applicationName = dynAdminInfo.getApplicationName();
		String mailSubject = "Change Triggered By --";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(cal.getTime());
		String comments = dynAdminInfo.getCmDetails();
		String messageDetails = "<br><br><b>Application Name</b>=" + applicationName
				+ "<br><br><b>Component Changed</b>=" + dynAdminInfo.getComponent() + "<br><br><b>Property Changed</b>="
				+ dynAdminInfo.getProperty() + "<br><br><b>Changed Value</b>=" + dynAdminInfo.getNewValue()
				+ "<br><br><b>Value Persisted in Property File</b>=" + dynAdminInfo.getSaveToFile()
				+ "<br><br><b>Comments : </b> " + comments;

		ATGApplicationsProps.ServerGroup serverGroup = populateDetailsHelper
				.getServerGroup(dynAdminInfo.getApplicationName());

		List<String> hostsList = populateDetailsHelper.generateServers(serverGroup.getHosts(), serverGroup.getPorts());

		boolean saveToFile = false;
		if (dynAdminInfo.getSaveToFile() != null && dynAdminInfo.getSaveToFile().equals("Yes")) {
			saveToFile = true;
		}

		if (dynAdminInfo.getParallelExecution() != null && dynAdminInfo.getParallelExecution().equals("Yes")) {

			logger.debug("Begin : DynAdminOperationsService --> setComponentValues() --> parallel Execution");

			ExecutorService executor;
			if (hostsList.size() < dynAdminInfo.getParallelThreadCount()
					|| dynAdminInfo.getParallelThreadCount() == 0) {
				if(hostsList.size()>30) {
					executor = Executors.newFixedThreadPool(30);
				}else {
					executor = Executors.newFixedThreadPool(hostsList.size());
				}
			} else {
				executor = Executors.newFixedThreadPool(dynAdminInfo.getParallelThreadCount());
			}

			List<Future<String>> errorList = new ArrayList<Future<String>>();
			for (String hostURL : hostsList) {
				Callable<String> worker = new CallableComponetValueSetter(populateDetailsHelper, hostURL,
						populateDetailsHelper.cleanseUpComponent(dynAdminInfo.getComponent()),
						dynAdminInfo.getProperty(), dynAdminInfo.getNewValue(), saveToFile);
				Future<String> submit = executor.submit(worker);
				errorList.add(submit);

			}

			for (Future<String> future : errorList) {

				try {
					String result = future.get();
					if (result != null) {
						errors = errors + result + "</br>";
						errorsforEmail = errorsforEmail + result + '-';
					}
				} catch (InterruptedException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());

				} catch (ExecutionException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());
				}

			}
			executor.shutdown();
			logger.debug("End : DynAdminOperationsService --> setComponentValues() --> parallel Execution");
		} else {

			logger.debug("Begin : DynAdminOperationsService --> setComponentValues() --> serial Execution");

			for (String hostURL : hostsList) {
				try {
					String result = componetValueSetter.setComponentValue(hostURL,
							populateDetailsHelper.cleanseUpComponent(dynAdminInfo.getComponent()),
							dynAdminInfo.getProperty(), dynAdminInfo.getNewValue(), saveToFile);
					if (result != null) {
						errors = errors + result + "</br>";
						errorsforEmail = errorsforEmail + result + '-';
					}

				} catch (UnsupportedEncodingException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());

				} catch (ClientProtocolException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());
				} catch (IOException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());
				}
			}

			logger.debug("End : DynAdminOperationsService --> setComponentValues() --> serial Execution");
		}
		if (errors.length() == 0)

		{
			dynAdminInfo.setSuccessMsg("Setting New Value for Component is Completed for All Servers");
			messageDetails += "<br><br><b>----------------Values Updated Sucessfully in all Servers---------</b><br>";

		} else {

			String errorWithNewLine = errorsforEmail.replace("-", "<br>");
			messageDetails += "<br><br><b>----------------Values not changed for---------</b><br>" + errorWithNewLine;

			dynAdminInfo.setSuccessMsg(
					"<font color = red>Setting New Value for Component is Completed, but not for below servers </font></br> </br>"
							+ errors);

		}
		/*
		 * try { emailService.sendEmail(dateStr, messageDetails, mailSubject, comments);
		 * dynAdminInfo.
		 * setMailStatusMsg("Mail Successfully Sent about the action perfromed "); }
		 * catch (Exception e) { dynAdminInfo.setMailStatusMsg(
		 * "Exception encontered while sending email about the action performed </br>" +
		 * e.getMessage());
		 * 
		 * }
		 */
		logger.debug("End : DynAdminOperationsService --> setComponentValues() Took " + (started - System.currentTimeMillis()) + 
				" milliseconds");
		return dynAdminInfo;
	}

	@Override
	public DynAdminInfo getComponentValue(DynAdminInfo dynAdminInfo) {

		logger.debug("Begin : DynAdminOperationsService --> getComponentValue()");

		long started = System.currentTimeMillis();
		String errors = new String();
		List<String> reportList = new ArrayList<>();
		ATGApplicationsProps.ServerGroup serverGroup = populateDetailsHelper
				.getServerGroup(dynAdminInfo.getApplicationName());

		List<String> hostsList = populateDetailsHelper.generateServers(serverGroup.getHosts(), serverGroup.getPorts());

		reportList.add(
				"<html><body><table border=\"1\" style=\"width:100%\"><tr><td colspan = 2><center><B><Font color = \"GREEN\">"
						+ dynAdminInfo.getApplicationName() + "</B></center></td></tr>"
						+ "<tr><td><center><B>Server Instance</B></center></td><td><B>Property Value</B></td>");

		for (String hostURL : hostsList) {

			reportList.add("<tr>");

			try {

				Map<String, String> resutlMap = componentValueGetter.getComponentValue(hostURL,
						dynAdminInfo.getComponent(), dynAdminInfo.getProperty());
				reportList.add("<td><a href=\"" + resutlMap.get("URL") + "\">" + resutlMap.get("URL") + "</a></td>");
				if (resutlMap.get("Error") != null)
					errors = errors + resutlMap.get("Error") + "\n";

				if (resutlMap.get("ComponentValue") != null) {
					reportList.add("<td><font color = blue>" + resutlMap.get("ComponentValue") + "</font></td>");

				} else {
					if (resutlMap.get("URL").toString().contains("uktul02atglf79v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atglf80v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgca53v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgca54v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgcs53v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgcs54v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgcw51v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgcw52v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgld51v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgld52v:8280".toUpperCase())) {

						reportList.add("<td><font color = green>Server Lock Manager</font></td>");

					} else {
						reportList.add("<td><font color = red>Slot Not Accessible</font></td>");
					}

				}

			} catch (UnsupportedEncodingException e) {
				dynAdminInfo.setErrorMsg(e.getMessage());

			} catch (ClientProtocolException e) {
				dynAdminInfo.setErrorMsg(e.getMessage());
			} catch (IOException e) {
				dynAdminInfo.setErrorMsg(e.getMessage());
			}
			reportList.add("</tr>");
		}
		reportList.add("</table></body></html>");

		dynAdminInfo.setReportList(reportList);
		if (errors.length() == 0)

		{
			dynAdminInfo.setSuccessMsg("Report Generation Successful for All Servers");

		} else {
			dynAdminInfo.setSuccessMsg("Report Generation Completed, but not for below servers </br> " + errors);

		}
		logger.debug("DynAdminOperationsService --> getComponentValue() Took " + (started - System.currentTimeMillis())
				+ " milliseconds");
		logger.debug("End : DynAdminOperationsService --> getComponentValue()");
		return dynAdminInfo;
	}

	@Override
	public DynAdminInfo getComponentValueWithConcurrentThreads(DynAdminInfo dynAdminInfo) {

		logger.debug("Begin : DynAdminOperationsService --> getComponentValueWithConcurrentThreads()");

		long started = System.currentTimeMillis();

		String errors = new String();
		List<String> reportList = new ArrayList<>();
		ATGApplicationsProps.ServerGroup serverGroup = populateDetailsHelper
				.getServerGroup(dynAdminInfo.getApplicationName());

		List<String> hostsList = populateDetailsHelper.generateServers(serverGroup.getHosts(), serverGroup.getPorts());

		reportList.add(
				"<html><body><table border=\"1\" style=\"width:100%\"><tr><td colspan = 2><center><B><Font color = \"GREEN\">"
						+ dynAdminInfo.getApplicationName() + "</B></center></td></tr>"
						+ "<tr><td><center><B>Server Instance</B></center></td><td><B>Property Value</B></td>");

		ExecutorService executor;
		if(hostsList.size() > 30) {
			executor = Executors.newFixedThreadPool(30);
		}else {
			executor = Executors.newFixedThreadPool(hostsList.size());
		}
		List<Future<Map<String, String>>> resultList = new ArrayList<Future<Map<String, String>>>();
		for (String hostURL : hostsList) {
			Callable<Map<String, String>> worker = new CallableComponentValueGetter(populateDetailsHelper, hostURL,
					dynAdminInfo.getComponent(), dynAdminInfo.getProperty());
			Future<Map<String, String>> submit = executor.submit(worker);
			resultList.add(submit);

		}

		for (Future<Map<String, String>> future : resultList) {

			reportList.add("<tr>");

			try {

				Map<String, String> resutlMap = future.get();
				reportList.add("<td><a href=\"" + resutlMap.get("URL") + "\">" + resutlMap.get("URL") + "</a></td>");
				if (resutlMap.get("Error") != null)
					errors = errors + resutlMap.get("Error") + "\n";

				if (resutlMap.get("ComponentValue") != null) {
					reportList.add("<td><font color = blue>" + resutlMap.get("ComponentValue") + "</font></td>");

				} else {
					if (resutlMap.get("URL").toString().contains("uktul02atglf79v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atglf80v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgca53v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgca54v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgcs53v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgcs54v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgcw51v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgcw52v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgld51v:8180".toUpperCase())
							|| resutlMap.get("URL").toString().contains("uktul02atgld52v:8280".toUpperCase())) {

						reportList.add("<td><font color = green>Server Lock Manager</font></td>");

					} else {
						reportList.add("<td><font color = red>Slot Not Accessible</font></td>");
					}

				}

			} catch (InterruptedException e) {
				dynAdminInfo.setErrorMsg(e.getMessage());

			} catch (ExecutionException e) {
				dynAdminInfo.setErrorMsg(e.getMessage());
			}
			reportList.add("</tr>");
		}
		reportList.add("</table></body></html>");

		dynAdminInfo.setReportList(reportList);
		if (errors.length() == 0)

		{
			dynAdminInfo.setSuccessMsg("Report Generation Successful for All Servers");

		} else {
			dynAdminInfo.setSuccessMsg("Report Generation Completed, but not for below servers </br> " + errors);

		}
		logger.debug("DynAdminOperationsService --> getComponentValueWithConcurrentThreads() Took "
				+ (started - System.currentTimeMillis()) + " milliseconds");
		logger.debug("End : DynAdminOperationsService --> getComponentValueWithConcurrentThreads()");
		executor.shutdown();
		return dynAdminInfo;
	}

	@Override
	public DynAdminInfo invokeMethod(DynAdminInfo dynAdminInfo) {

		logger.debug("Begin : DynAdminOperationsService --> invokeMethod()");
		long started = System.currentTimeMillis();
		
		String errors = new String();
		String errorsforEmail = new String();
		String applicationName = dynAdminInfo.getApplicationName();
		String mailSubject = "Method triggered by --";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(cal.getTime());
		String comments = dynAdminInfo.getCmDetails();
		String messageDetails = "<br><br><b>Application Name</b>=" + applicationName + "<br><br><b>Component Name </b>="
				+ dynAdminInfo.getComponent() + "<br><br><b>Method Invoked </b>=" + dynAdminInfo.getMethodName()
				+ "<br><br><b>Comments : </b> " + comments;

		ATGApplicationsProps.ServerGroup serverGroup = populateDetailsHelper
				.getServerGroup(dynAdminInfo.getApplicationName());

		List<String> hostsList = populateDetailsHelper.generateServers(serverGroup.getHosts(), serverGroup.getPorts());

		if (dynAdminInfo.getParallelExecution() != null && dynAdminInfo.getParallelExecution().equals("Yes")) {

			logger.debug("Begin : DynAdminOperationsService --> invokeMethod() --> parallel Execution");

			ExecutorService executor;
			if (hostsList.size() < dynAdminInfo.getParallelThreadCount()
					|| dynAdminInfo.getParallelThreadCount() == 0) {
				if(hostsList.size()>30) {
					executor = Executors.newFixedThreadPool(30);
				}else {
					executor = Executors.newFixedThreadPool(hostsList.size());
				}
			} else {
				executor = Executors.newFixedThreadPool(dynAdminInfo.getParallelThreadCount());
			}

			List<Future<String>> errorList = new ArrayList<Future<String>>();
			for (String hostURL : hostsList) {
				Callable<String> worker = new CallableMethodInvoker(populateDetailsHelper, hostURL,
						populateDetailsHelper.cleanseUpComponent(dynAdminInfo.getComponent()),
						dynAdminInfo.getMethodName());
				Future<String> submit = executor.submit(worker);
				errorList.add(submit);

			}

			for (Future<String> future : errorList) {

				try {
					String result = future.get();
					if (result != null) {
						errors = errors + result + "</br>";
						errorsforEmail = errorsforEmail + result + '-';
					}
				} catch (InterruptedException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());

				} catch (ExecutionException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());
				}

			}
			executor.shutdown();

			logger.debug("End : DynAdminOperationsService --> invokeMethod() --> parallel Execution");
		} else {

			logger.debug("Begin : DynAdminOperationsService --> invokeMethod() --> serial Execution");

			for (String hostURL : hostsList) {
				try {
					String result = methodInvoker.invokeMethod(hostURL,
							populateDetailsHelper.cleanseUpComponent(dynAdminInfo.getComponent()),
							dynAdminInfo.getMethodName());
					if (result != null) {
						errors = errors + result + "</br>";
						errorsforEmail = errorsforEmail + result + '-';
					}

				} catch (UnsupportedEncodingException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());

				} catch (ClientProtocolException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());
				} catch (IOException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());
				}
			}

		}
		if (errors.length() == 0)

		{
			dynAdminInfo.setSuccessMsg("Method Invocation Completed Successfully on All Servers");
			messageDetails += "<br><br><b>----------------Method Invoked for All Servers---------</b><br>";

		} else {
			dynAdminInfo.setSuccessMsg(
					"<font color = red>Method Invocation Completed, but not for below servers </font></br></br>"
							+ errors);
			String errorWithNewLine = errorsforEmail.replace("-", "<br>");
			messageDetails += "<br><br><b>----------------Method not Invoked for---------</b><br>" + errorWithNewLine;

		}
		/*
		 * try { emailService.sendEmail(dateStr, messageDetails, mailSubject, comments);
		 * dynAdminInfo.
		 * setMailStatusMsg("Mail Successfully Sent about the action perfromed "); }
		 * catch (Exception e) { dynAdminInfo.setMailStatusMsg(
		 * "Exception encontered while sending email about the action performed </br>" +
		 * e.getMessage());
		 * 
		 * }
		 */
		logger.debug("End : DynAdminOperationsService --> invokeMethod() Took " + (started - System.currentTimeMillis()) 
				+" milliseconds");
		return dynAdminInfo;
	}

	@Override
	public DynAdminInfo invalidateCache(DynAdminInfo dynAdminInfo) {

		logger.debug("Begin : DynAdminOperationsService --> invalidateCache()");
		long started = System.currentTimeMillis();
		
		String errors = new String();
		String errorsforEmail = new String();
		String applicationName = dynAdminInfo.getApplicationName();
		String mailSubject = "Cache Cleared by --";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(cal.getTime());
		String comments = dynAdminInfo.getCmDetails();
		String messageDetails = "<br><br><b>Application Name</b>=" + applicationName
				+ "<br><br><b>Cache Cleared for Component </b>=" + dynAdminInfo.getComponent()
				+ "<br><br><b>Comments : </b> " + comments;

		ATGApplicationsProps.ServerGroup serverGroup = populateDetailsHelper
				.getServerGroup(dynAdminInfo.getApplicationName());

		List<String> hostsList = populateDetailsHelper.generateServers(serverGroup.getHosts(), serverGroup.getPorts());

		if (dynAdminInfo.getParallelExecution() != null && dynAdminInfo.getParallelExecution().equals("Yes")) {

			logger.debug("Begin : DynAdminOperationsService --> invalidateCache() --> parallel Execution "
					+ dynAdminInfo.getParallelThreadCount());

			ExecutorService executor;
			if (hostsList.size() < dynAdminInfo.getParallelThreadCount()
					|| dynAdminInfo.getParallelThreadCount() == 0) {
				if(hostsList.size()>30) {
					executor = Executors.newFixedThreadPool(30);
				}else {
					executor = Executors.newFixedThreadPool(hostsList.size());
				}
			} else {
				executor = Executors.newFixedThreadPool(dynAdminInfo.getParallelThreadCount());
			}

			List<Future<String>> errorList = new ArrayList<Future<String>>();
			for (String hostURL : hostsList) {
				Callable<String> worker = new CallableCacheInvalidator(populateDetailsHelper, hostURL,
						populateDetailsHelper.cleanseUpComponent(dynAdminInfo.getRepository()));
				Future<String> submit = executor.submit(worker);
				errorList.add(submit);

			}

			for (Future<String> future : errorList) {

				try {
					String result = future.get();
					if (result != null) {
						errors = errors + result + "</br>";
						errorsforEmail = errorsforEmail + result + '-';
					}
				} catch (InterruptedException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());

				} catch (ExecutionException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());
				}

			}
			executor.shutdown();

			logger.debug("End : DynAdminOperationsService --> invalidateCache() --> parallel Execution");

		} else {

			logger.debug("Begin : DynAdminOperationsService --> invalidateCache() --> serial Execution");

			for (String hostURL : hostsList) {
				try {
					String result = cacheInvalidator.invalidateCache(hostURL,
							populateDetailsHelper.cleanseUpComponent(dynAdminInfo.getRepository()));
					if (result != null) {
						errors = errors + result + "</br>";
						errorsforEmail = errorsforEmail + result + '-';
					}

				} catch (UnsupportedEncodingException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());

				} catch (ClientProtocolException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());
				} catch (IOException e) {
					dynAdminInfo.setErrorMsg(e.getMessage());
				}
			}
			logger.debug("End : DynAdminOperationsService --> invalidateCache() --> serial Execution");
		}
		if (errors.length() == 0)

		{
			dynAdminInfo.setSuccessMsg("Cache Invalidation completed on All Servers");
			messageDetails += "<br><br><b>----------------Cache cleared for all servers---------</b><br>";

		} else {

			String errorWithNewLine = errorsforEmail.replace("-", "<br>");
			messageDetails += "<br><br><b>----------------Cache cleared for all servers but not for ---------</b><br>"
					+ errorWithNewLine;
			dynAdminInfo.setSuccessMsg("<font color = red>Cache Invalidation completed, but not for below servers</font></br></br> " + errors);

		}
		/*
		 * try { emailService.sendEmail(dateStr, messageDetails, mailSubject, comments);
		 * dynAdminInfo.
		 * setMailStatusMsg("Mail Successfully Sent about the action perfromed "); }
		 * catch (Exception e) { dynAdminInfo.setMailStatusMsg(
		 * "Exception encontered while sending email about the action performed </br>" +
		 * e.getMessage());
		 * 
		 * }
		 */
		logger.debug("End : DynAdminOperationsService --> invalidateCache() Took " + (started - System.currentTimeMillis()) + " milliseconds");
		return dynAdminInfo;
	}
	
	@Override
	public DashboardInfo getServersStatus(DashboardInfo dashboardInfo) {

		logger.debug("Begin : DynAdminOperationsService --> getComponentValueWithConcurrentThreads()");

		long started = System.currentTimeMillis();
		
		Map<String,String> serverStatusMap = new  TreeMap<String, String>(); 
		
		ATGApplicationsProps.ServerGroup serverGroup = populateDetailsHelper
				.getServerGroup(dashboardInfo.getApplicationName());

		List<String> hostsList = populateDetailsHelper.generateServers(serverGroup.getHosts(), serverGroup.getPorts());
		ExecutorService executor;
		if(hostsList.size() > 20) {
			executor = Executors.newFixedThreadPool(20);
		}else {
			executor = Executors.newFixedThreadPool(hostsList.size());
		}
		
		List<Future<Map<String, String>>> resultList = new ArrayList<Future<Map<String, String>>>();
		for (String hostURL : hostsList) {
			Callable<Map<String, String>> worker = new CallableComponentValueGetter(populateDetailsHelper, hostURL,
					"component", "property");
			Future<Map<String, String>> submit = executor.submit(worker);
			resultList.add(submit);

		}

		for (Future<Map<String, String>> future : resultList) {

			try {

				Map<String, String> resutlMap = future.get();
				String serverName = resutlMap.get("URL").substring(resutlMap.get("URL").indexOf("//")+2, resutlMap.get("URL").indexOf("/dyn"));
				

				if (resutlMap.get("ComponentValue") != null) {
					serverStatusMap.put(serverName, "1");
					
				}  else {
					serverStatusMap.put(serverName, "0");
				}

			} catch (InterruptedException e) {
				dashboardInfo.setErrorMsg(e.getMessage());

			} catch (ExecutionException e) {
				dashboardInfo.setErrorMsg(e.getMessage());
			}
			
		}
		
		dashboardInfo.setServersStatusMap(serverStatusMap);
		
		logger.debug("DynAdminOperationsService --> getComponentValueWithConcurrentThreads() Took "
				+ (started - System.currentTimeMillis()) + " milliseconds");
		logger.debug("End : DynAdminOperationsService --> getComponentValueWithConcurrentThreads()");
		executor.shutdown();
		return dashboardInfo;
	}
}
