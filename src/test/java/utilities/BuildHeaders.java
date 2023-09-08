package utilities;

import java.util.HashMap;

public class BuildHeaders {
	private static HashMap<String, Object> responseBodyType = new HashMap<>();
	private static HashMap<String, Object> requestPaylodType = new HashMap<>();
	private static HashMap<String, Object> authCookie = new HashMap<>();
	
	public BuildHeaders() {
		responseBodyType.put("Accept", "application/json");
		requestPaylodType.put("Content-Type", "application/json");
		authCookie.put("Cookie", "token=<replacetokenhere>");
	}
	
	public HashMap<String, Object> getMethodHeader() {
		return responseBodyType;
	}
	
	public HashMap<String, Object> postMethodHeader() {
		return requestPaylodType;
	}
	
	public HashMap<String, Object> patchMethodHeader(String token) {
		HashMap<String, Object> headers = new HashMap<>();
		headers.putAll(responseBodyType);
		headers.putAll(requestPaylodType);
		authCookie.replace("Cookie", getAuthCookieHeader(token));
		headers.putAll(authCookie);
		return headers;
	}
	
	public HashMap<String, Object> deleteMethodHeader(String token) {
		authCookie.replace("Cookie", getAuthCookieHeader(token));
		return authCookie;
	}
	
	private String getAuthCookieHeader(String token) {
		return "token="+token;
	}
}
