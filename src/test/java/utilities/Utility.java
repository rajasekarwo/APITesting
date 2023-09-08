package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Utility {
	
	public String getJsonNodeValue(Response response, String jsonPath) {
		JsonPath js = new JsonPath(response.body().asString());
		return js.get(jsonPath).toString();
	}
	
	public String getGlobalValues(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream file = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\global.properties");
		prop.load(file);
		return prop.getProperty(key);
	}
	
	public void printJsonClassObject(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			System.out.println(mapper.writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			System.out.println("Unable to print the Json Class Object");
		}
	}
}
