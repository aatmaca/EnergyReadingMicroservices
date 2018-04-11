package tr.com.dvm.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpUtil {

	public static String getJsonString(String serviceUrl, String method) {
		return getJsonString(serviceUrl, null, method);
	}

	public static String getJsonString(String serviceUrl, Map<String, String> map, String method) {
		StringBuilder response = new StringBuilder();

		try {
			URL url = new URL(serviceUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setUseCaches(false);
			connection.setReadTimeout(60 * 1000);
			connection.setConnectTimeout(60 * 1000);
			connection.setRequestProperty("appKey", Constants.APP_KEY);
			connection.setRequestProperty("Accept", Constants.ACCEPT_TYPE_JSON);

			if (map != null) {
				String urlParameters = getFormDataString(map);
				byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
				int postDataLength = postData.length;
				
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
				connection.getOutputStream().write(postData);
			}

			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
			}
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response.toString();
	}

	private static String getFormDataString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean append = false;
		for (Map.Entry<String, String> entry : params.entrySet()) {

			if (append)
				result.append("&");
			else
				append = true;

			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}
		return result.toString();
	}
}