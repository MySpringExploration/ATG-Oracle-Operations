/**
 * 
 */
package org.iqra.operationsapp.dynadmin.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
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
 * @author Abdul 11-Mar-2018
 * 
 */

public class CallableComponentValueGetter implements Callable<Map<String, String>> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private PopulateDetailsHelper populateDetailsHelper;
	private String hostURL;
	private String component;
	private String property;

	public CallableComponentValueGetter(PopulateDetailsHelper populateDetailsHelper, String hostURL, String component,
			String property) {
		super();
		this.populateDetailsHelper = populateDetailsHelper;
		this.hostURL = hostURL;
		this.component = component;
		this.property = property;
	}

	private static final Map<String, String> slots;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("8180", "slot1");
		map.put("8280", "slot2");
		map.put("8380", "slot3");
		map.put("8480", "slot4");
		map.put("8580", "slot5");
		map.put("8680", "slot6");

		slots = Collections.unmodifiableMap(map);
	}
	private static final String DEPLOYFOLDER = "\\d$\\Deployments\\ATG-Data\\servers\\";

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
	public Map<String, String> call() throws UnsupportedEncodingException, IOException, ClientProtocolException {

		logger.info("Begin : CallableComponentValueGetter --> call()");

		String errors = null;
		String componentValue = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		returnMap.put("ComponentValue", componentValue);
		returnMap.put("Error", errors);
		returnMap.put("URL", hostURL);
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

			httpclient.getCredentialsProvider().setCredentials(new AuthScope(getHost(hostURL), getPort(hostURL)),
					new UsernamePasswordCredentials(populateDetailsHelper.getDynAdminUser(),
							populateDetailsHelper.getDynAdminPwd()));

			url = "http://" + hostURL + "/dyn/admin/nucleus/" + component.trim() + "/?propertyName=" + property.trim();
			returnMap.put("URL", url);
			// System.out.println("URL to be hit: "+url);
			HttpPost httpost = new HttpPost(url);

			// Create a response handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpclient.execute(httpost, responseHandler);
			componentValue = ComponentValueParser.getComponetValue(responseBody);
			returnMap.put("ComponentValue", componentValue);

			System.out.println(" Done for : " + httpost.getURI());
		}

		catch (HttpResponseException e) {
			System.out.println("The login details are incorrect. Please check the details in the property files");
			errors = url;
			returnMap.put("Error", errors);

		} catch (UnknownHostException e) {
			System.out.println("The Connection String seems to be invalid : " + url);
			errors = url;
			returnMap.put("Error", errors);
		} catch (HttpHostConnectException e) {
			System.out.println("The Connection String seems to be invalid : " + url);
			errors = url;
			returnMap.put("Error", errors);

		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			if (httpclient != null) {
				httpclient.getConnectionManager().shutdown();

			}
		}

		logger.info("End : CallableComponentValueGetter --> call()");
		return returnMap;
	}

}
