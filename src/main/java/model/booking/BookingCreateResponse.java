package model.booking;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;
import model.booking.Booking;

public class BookingCreateResponse {

    public static String schemaFile="booking/bookingResponse.json";
    @JsonProperty("bookingid")
    private int bookingid;
    @JsonProperty("booking")
    private Booking booking;

    public int getBookingid() {
        return bookingid;
    }

    public Booking getBooking() {
        return booking;
    }
}
