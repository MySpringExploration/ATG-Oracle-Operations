/**
 * 
 */
package org.iqra.operationsapp.dynadmin.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.iqra.operationsapp.helper.PopulateDetailsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abdul 08-Mar-2018
 * 
 */

public class CallableMethodInvoker implements Callable<String> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private PopulateDetailsHelper populateDetailsHelper;
	private String host;
	private String componentName;
	private String methodName;

	public CallableMethodInvoker(PopulateDetailsHelper populateDetailsHelper, String host, String componentName,
			String methodName) {
		super();
		this.populateDetailsHelper = populateDetailsHelper;
		this.host = host;
		this.componentName = componentName;
		this.methodName = methodName;
	}

	private static String getHost(String url) {
		String retvalue = url;

		retvalue = url.substring(0, url.indexOf(":"));
		return retvalue;
	}

	private static int getPort(String url) {
		String retvalue = url;
		retvalue = url.substring(url.indexOf(":") + 1);
		return Integer.parseInt(retvalue);
	}

	@Override
	public String call() throws UnsupportedEncodingException, IOException, ClientProtocolException {
		logger.info("Begin : CallableMethodInvoker --> call()");

		String errors = null;
		DefaultHttpClient httpclient = null;
		String url = null;
		try {

			HttpParams httpParameters = new BasicHttpParams();
			// Set the timeout in milliseconds until a connection is
			// established.
			// The default value is zero, that means the timeout is not used.
			int timeoutConnection = 20000;
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			// Set the default socket timeout (SO_TIMEOUT)
			// in milliseconds which is the timeout for waiting for data.
			int timeoutSocket = 25000;
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

			httpclient = new DefaultHttpClient(httpParameters);

			httpclient.getCredentialsProvider().setCredentials(new AuthScope(getHost(host), getPort(host)),
					new UsernamePasswordCredentials(populateDetailsHelper.getDynAdminUser(),
							populateDetailsHelper.getDynAdminPwd()));
			url = "http://" + host + "/dyn/admin/nucleus/" + componentName.trim() + "/";

			HttpPost httpost = new HttpPost(url);

			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
			nvps.add(new BasicNameValuePair("invokeMethod", methodName));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

			// Create a response handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			String responseBody = httpclient.execute(httpost, responseHandler);

			System.out.println(" Method " + methodName + "invoked for : " + url);

		}

		catch (HttpResponseException e) {
			System.out.println("The login details are incorrect. Please check the details in the property files");
			errors = url;

		} catch (UnknownHostException e) {
			System.out.println("The Connection String seems to be invalid : " + url);
			errors = url;
		} catch (HttpHostConnectException e) {
			System.out.println("The Connection String seems to be invalid : " + url);
			errors = url;

		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();

			}
		}
		logger.info("End : CallableMethodInvoker --> call()");
		return errors;
	}

}
