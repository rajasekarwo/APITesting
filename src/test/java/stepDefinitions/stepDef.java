package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import pojo.BookingDates;
import pojo.BookingDetails;
import restAPI.herokuappTesting.BaseTest;
import utilities.ExcelUtils;

public class stepDef extends BaseTest{
	String token;
	String bookingId;
	Object expected;
	Object actual;
	Response res;
	BookingDetails bd;
	ExcelUtils excelData = new ExcelUtils("BookingIds");
	
	@Given("get Auth token to update the booking details")
	public void get_auth_token_to_update_the_booking_details() throws IOException {
		token = getAuthToken(getValidUserCredPayload());
	}
	
	@Given("get Booking Id by {int}")
	public void get_booking_id_by(int index) throws IOException {
	    bookingId = getBookingIdByIndex(index);
	}
	
	@Then("store the Booking Dates details of Booking Id to verify")
	public void store_the_booking_dates_details_of_booking_id_to_verify() throws IOException {
		BookingDetails bd = getBookingDetailsByBookingId(bookingId);
		expected = bd.getBookingdates();
	}
	
	@Then("update the {string} and {string} details for the Booking Id")
	public void update_the_and_details_for_the_booking_id(String checkin, String checkout) throws IOException {
		BookingDates bdates = new BookingDates();
		BookingDetails bdetails = new BookingDetails();
		bdetails.setBookingdates(bdates);
		bdetails.getBookingdates().setCheckin(checkin);
		bdetails.getBookingdates().setCheckout(checkout);
		
		res = updatePartialBookingDetailResponse(token, bdetails, bookingId);
		bd = res.as(BookingDetails.class);
		actual = bd.getBookingdates();
	}
	
	@Then("verify the Booking Id updated with {string} and {string} details")
	public void verify_the_booking_id_updated_with_and_details(String checkin, String chekout) {
		assertEquals(expected.equals(actual), false);
		assertEquals(bd.getBookingdates().getCheckin(), checkin);
		assertEquals(bd.getBookingdates().getCheckout(), chekout);
	}
	
	@Then("delete Booking Details by {string}")
	public void delete_booking_details_by(String dataId) throws IOException {
		dataId = excelData.getCellValue(dataId, "Booking Id");
		System.out.println("Booking Id : "+dataId);
		res = deleteBookingIdResponse(token, dataId);
	}
	
	@Then("verify the Booking Id gets deleted successfully")
	public void verify_the_booking_id_gets_deleted_successfully() throws IOException {
		assertEquals(res.statusCode(), 201);
	}
}
