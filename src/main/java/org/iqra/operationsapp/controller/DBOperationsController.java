/**
 * 
 */
package org.iqra.operationsapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.iqra.operationsapp.entity.DBOperations;
import org.iqra.operationsapp.export.view.ExcelView;
import org.iqra.operationsapp.export.view.PdfView;
import org.iqra.operationsapp.service.DBOperationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Abdul 28-Feb-2018
 * 
 */

@Controller
public class DBOperationsController {

	@Autowired
	private DBOperationsService dbOperatoinsService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/dbQueryPanel", method = RequestMethod.GET)
	public String showDBQueryPanelPage(HttpServletRequest request, ModelMap model) {
		logger.debug("Begin : DynAdminController --> /dbQueryPanel --> GET");

		model.addAttribute("dBOperations", new DBOperations());
		
		logger.debug("End : DynAdminController --> /dbQueryPanel --> GET");
		return "db-operations/dbquery-operations";
	}

	@RequestMapping(value = "/dbQueryPanel", method = RequestMethod.POST)
	public String showDBQueryResultsPage(ModelMap model, @Valid DBOperations dBOperations, BindingResult result,
			HttpServletRequest request) {
		
		logger.debug("Begin : DynAdminController --> /dbQueryPanel --> POST");
		
		if (result.hasErrors()) {
			model.addAttribute("exceptionMsg", result.getErrorCount());
		}
		String dbQuery = dBOperations.getDbQuery().trim();
		String queryPrefix = dbQuery.substring(0, dbQuery.indexOf(" ")).toUpperCase();
		if (queryPrefix.equals("CREATE") || queryPrefix.equals("RENAME") || queryPrefix.equals("DROP")
				|| queryPrefix.equals("ALTER")) {
			// isDDLQuery = true;
			if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_EDITUSER")) {
				DBOperations dBOperationsResult = dbOperatoinsService.executeQuery(dbQuery);
				dBOperationsResult.setDbQuery(dbQuery);
				model.addAttribute("dBOperations", dBOperationsResult);
			} else if (request.isUserInRole("ROLE_USER")) {
				dBOperations.setExceptionMsg("Your Role do not have Access to perform other than SELECT queries");
				model.addAttribute("dBOperations", dBOperations);
			}

			return "db-operations/dbquery-operations";
		} else if (queryPrefix.equals("INSERT") || queryPrefix.equals("UPDATE") || queryPrefix.equals("TRUNCATE")
				|| queryPrefix.equals("DELETE")) {

			if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_EDITUSER")) {

				DBOperations dBOperationsResult = dbOperatoinsService.executeQuery(dbQuery);
				dBOperationsResult.setDbQuery(dbQuery);
				model.addAttribute("dBOperations", dBOperationsResult);

			} else if (request.isUserInRole("ROLE_USER")) {

				dBOperations.setExceptionMsg("Your Role do not have Access to perform other than SELECT queries");
				model.addAttribute("dBOperations", dBOperations);
				return "db-operations/dbquery-operations";

			} else {

				DBOperations dBOperationsResult = dbOperatoinsService.executeQuery(dbQuery);
				dBOperationsResult.setDbQuery(dbQuery);
				model.addAttribute("dBOperations", dBOperationsResult);
			}

		} else if (queryPrefix.equals("SELECT")) {

			DBOperations dBOperationsResult = dbOperatoinsService.executeQuery(dbQuery);
			dBOperationsResult.setDbQuery(dbQuery);
			request.getSession().setAttribute("dbQuery", dbQuery);
			model.addAttribute("dBOperations", dBOperationsResult);
		}else {
			DBOperations dBOperationsResult = dbOperatoinsService.executeQuery(dbQuery);
			dBOperationsResult.setDbQuery(dbQuery);
			model.addAttribute("dBOperations", dBOperationsResult);
		}
		
		logger.debug("End : DynAdminController --> /dbQueryPanel --> POST");
		
		return "db-operations/dbquery-operations";
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView exportDBQueryResultsPage(ModelMap model, @Valid DBOperations dBOperations, BindingResult result,
			HttpServletRequest request) {
		
		logger.debug("Begin : DynAdminController --> /export --> GET");
		String format = request.getParameter("format");
		String dbQuery = request.getSession().getAttribute("dbQuery").toString();
		DBOperations dBOperationsResult = dbOperatoinsService.executeQuery(dbQuery);
		dBOperationsResult.setDbQuery(dbQuery);
		
		logger.debug("End : DynAdminController --> /export --> GET");
		if(format != null && format.equals("ToExcel")){
			   return new ModelAndView(new ExcelView(), "dBOperations", dBOperationsResult);
		} else if(format != null && format.equals("ToPDF")){
			   return new ModelAndView(new PdfView(), "dBOperations", dBOperationsResult);
		}
			  
		return new ModelAndView(new ExcelView(),"dBOperations", dBOperationsResult);
		
	}
	

}
