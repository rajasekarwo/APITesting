@UpdateBookingId
Feature: Update Booking Details

	Background:
		Given get Auth token to update the booking details

  @UpdateBookingDates
  Scenario Outline: verify able to Update Checkin/Checkout Date details in Booking details
    And get Booking Id by <index>
    Then store the Booking Dates details of Booking Id to verify
    And update the "<checkin>" and "<checkout>" details for the Booking Id
    Then verify the Booking Id updated with "<checkin>" and "<checkout>" details
    
    Examples: 
      | index | checkin    | checkout   |
      | 0     | 2018-06-15 | 2019-06-15 |

  @DeleteBookingId
  Scenario Outline: verify able to delete Booking Id Details
    And delete Booking Details by "<bookingId>"
    Then verify the Booking Id gets deleted successfully
    
    Examples: 
      | bookingId  |
      | bookingId1 |
      | bookingId2 |