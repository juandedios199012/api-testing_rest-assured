package model.booking;

import com.github.javafaker.Faker;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;
import org.testng.asserts.SoftAssert;

public class Booking {

    @JsonProperty("firstname")
    private final String firstname;
    @JsonProperty("lastname")
    private final String lastname;
    @JsonProperty("totalprice")
    private final int totalprice;
    @JsonProperty("depositpaid")
    private final boolean depositpaid;
    @JsonProperty("additionalneeds")
    private final String additionalneeds;
    @JsonProperty("bookingdates")
    private final BookingDates bookingdates;

    public Booking() {
        var faker=new Faker();
        firstname= faker.name().firstName();
        lastname=faker.name().lastName();
        totalprice=faker.number().numberBetween(5,100);
        depositpaid=faker.bool().bool();
        additionalneeds=faker.animal().name();
        bookingdates=new BookingDates();
    }

    public void isEqualsTo(Booking booking){
        var sofAssert=new SoftAssert();
        sofAssert.assertEquals(firstname,booking.getFirstname());
        sofAssert.assertEquals(lastname,booking.getLastname());
        sofAssert.assertEquals(totalprice,booking.getTotalprice());
        sofAssert.assertEquals(depositpaid,booking.isDepositpaid());
        sofAssert.assertEquals(additionalneeds,booking.getAdditionalneeds());
        sofAssert.assertEquals(bookingdates.getCheckin(),booking.getbookingdates().getCheckin());
        sofAssert.assertEquals(bookingdates.getCheckout(),booking.getbookingdates().getCheckin());

        sofAssert.assertAll();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public BookingDates getbookingdates() {
        return bookingdates;
    }
}
