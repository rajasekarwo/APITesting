package restAPI.herokuappTesting;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.BookingDetails;
import pojo.UserCredentials;
import resources.APIResources;
import utilities.BuildHeaders;
import utilities.Utility;

public class BaseTest extends Utility{
	public static RequestSpecification rs;
	public static BuildHeaders header = new BuildHeaders();
	
	public RequestSpecification requestSpec() throws IOException {
		if(rs == null) {
			rs = new RequestSpecBuilder()
					.setBaseUri(getGlobalValues("baseurl")).build();
		}
		return rs;
	}
	
	public Response getBookingIdResponseByIndex(int index) throws IOException {
		return given().spec(requestSpec())
				.when().get(APIResources.bookinAPI);
	}
	
	public Response getBookingDetailsResponseByBookingId(String bookingId) throws IOException {
		return given().spec(requestSpec())
				.headers(header.getMethodHeader())
				.when().get(APIResources.bookinAPI+bookingId);
	}
	
	public Response getAuthTokenResponse(UserCredentials payload) throws IOException {
		return given().spec(requestSpec())
				.headers(header.postMethodHeader())
				.body(payload)
				.when().post(APIResources.authAPI);
	}
	
	public Response updatePartialBookingDetailResponse(String token, BookingDetails payload, String bookingId) throws IOException {
		return given().spec(requestSpec())
				.headers(header.patchMethodHeader(token))
				.body(payload)
				.when().patch(APIResources.bookinAPI+bookingId);
	}
	
	public Response deleteBookingIdResponse(String token, String bookingId) throws IOException {
		return given().spec(requestSpec())
				.headers(header.deleteMethodHeader(token))
				.when().delete(APIResources.bookinAPI+bookingId);
	}
	
	public String getBookingIdByIndex(int index) throws IOException {
		Response res = getBookingIdResponseByIndex(index);
		assertEquals(res.statusCode(), 200);
		
		String bookingId = getJsonNodeValue(res, "["+index+"].bookingid");
		System.out.println("Booking Id at Index "+index+" is "+bookingId);
		return bookingId;
	}
	
	public BookingDetails getBookingDetailsByBookingId(String bookingId) throws IOException {
		Response res = getBookingDetailsResponseByBookingId(bookingId);
		assertEquals(res.statusCode(), 200);
		BookingDetails bd = res.as(BookingDetails.class);
		
		System.out.println("Booking Details:");
		printJsonClassObject(bd);
		return bd;
	}
	
	public String getAuthToken(UserCredentials payload) throws IOException {
		Response res = getAuthTokenResponse(payload);
		assertEquals(res.statusCode(), 200);
		
		String authToken = getJsonNodeValue(res, "token");
		return authToken;
	}
	
	public BookingDetails updatePartialBookingDetail(String token, BookingDetails bookingDetails, String bookingId) throws IOException {
		Response res = updatePartialBookingDetailResponse(token, bookingDetails, bookingId);
		assertEquals(res.statusCode(), 200);
		BookingDetails bd = res.as(BookingDetails.class);
		
		System.out.println("Updated Booking Details:");
		printJsonClassObject(bd);
		return bd;
	}
	
	public void deleteBookingId(String token, String bookingId) throws IOException {
		Response res = deleteBookingIdResponse(token, bookingId);
		assertEquals(res.statusCode(), 201);
	}
	
	public UserCredentials getValidUserCredPayload() throws IOException {
		UserCredentials userc = new UserCredentials();
		userc.setUsername(getGlobalValues("username"));
		userc.setPassword(getGlobalValues("password"));
		return userc;
	}
}
