package booking;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import model.booking.Booking;
import model.booking.BookingCreateResponse;
import org.testng.annotations.Test;
import utilities.RequestFilter;

import static io.restassured.http.Method.*;
import static org.hamcrest.Matchers.lessThan;

public class BookerTests extends BaseTest {

    @Test
    public void crudBooking(){

        RestAssured.baseURI=baseURL;
        var requestSpecification=RestAssured.given();

        requestSpecification.contentType(ContentType.JSON);

        requestSpecification.filter(new RequestFilter());

        var booking=new Booking();
        requestSpecification.body(booking);

        var response=requestSpecification.post("booking");

        var  responseBody =response.then().assertThat()
                .statusCode(200)
                .time(lessThan(7000L))
                .body(JsonSchemaValidator.matchesJsonSchema(getSchema(BookingCreateResponse.schemaFile)))
                .extract().body().as(BookingCreateResponse.class);

        var id=responseBody.getBookingid();

        //GET
        var requestSpecificationGetBooking=RestAssured.given();
        requestSpecificationGetBooking.filter(new RequestFilter());
        requestSpecificationGetBooking.pathParams("bookingid",id);
        requestSpecificationGetBooking.basePath("booking/{bookingid}");
        var responseGetBooking=requestSpecificationGetBooking.request(GET);

        responseGetBooking.then().assertThat()
                .statusCode(200)
                .time(lessThan(7000L));

        //DELETE
        var requestSpecificationDeleteBooking=RestAssured.given();

        requestSpecificationDeleteBooking.auth().preemptive().basic("admin","password123");
        requestSpecificationDeleteBooking.filter(new RequestFilter());
        requestSpecificationDeleteBooking.pathParams("bookingid",id);
        //requestSpecificationDeleteBooking.basePath("booking/{bookingid}");
        var responseDeleteBooking=requestSpecificationDeleteBooking.delete("booking/{bookingid}");

        responseDeleteBooking.then().assertThat()
                .statusCode(201)
                .time(lessThan(7000L));

        //GET
        var requestSpecificationGetBookingDelete=RestAssured.given();
        requestSpecificationGetBookingDelete.filter(new RequestFilter());
        requestSpecificationGetBookingDelete.pathParams("bookingid",id);
        requestSpecificationGetBookingDelete.basePath("booking/{bookingid}");
        var responseGetBookingThenDelete=requestSpecificationGetBookingDelete.request(GET);

        responseGetBookingThenDelete.then().assertThat()
                .statusCode(404)
                .time(lessThan(7000L));
    }

    @Test
    public void getBookingIDInvalid(){

        RestAssured.baseURI=baseURL;

        var requestSpecification=RestAssured.given();
        requestSpecification.filter(new RequestFilter());
        requestSpecification.pathParams("bookingid",90009);
        requestSpecification.basePath("booking/{bookingid}");
        var response=requestSpecification.request(GET);

        response.then().assertThat()
                .statusCode(404)
                .time(lessThan(7000L));
    }

    @Test
    public void deleteBookingWithoutAuth(){

        RestAssured.baseURI=baseURL;
        var requestSpecification=RestAssured.given();

        requestSpecification.contentType(ContentType.JSON);

        requestSpecification.filter(new RequestFilter());

        var booking=new Booking();
        requestSpecification.body(booking);

        var response=requestSpecification.post("booking");

        var  responseBody =response.then().assertThat()
                .statusCode(200)
                .time(lessThan(7000L))
                .body(JsonSchemaValidator.matchesJsonSchema(getSchema(BookingCreateResponse.schemaFile)))
                .extract().body().as(BookingCreateResponse.class);

        var id=responseBody.getBookingid();

        //DELETE
        var requestSpecificationDeleteBooking=RestAssured.given();

        requestSpecificationDeleteBooking.filter(new RequestFilter());
        requestSpecificationDeleteBooking.pathParams("bookingid",id);
        //requestSpecificationDeleteBooking.basePath("booking/{bookingid}");
        var responseDeleteBooking=requestSpecificationDeleteBooking.delete("booking/{bookingid}");

        responseDeleteBooking.then().assertThat()
                .statusCode(403)
                .time(lessThan(7000L));

    }

    @Test
    public void deleteBookingIDInvalid(){

        RestAssured.baseURI=baseURL;

        var requestSpecification=RestAssured.given();

        requestSpecification.auth().preemptive().basic("admin","password123");
        requestSpecification.filter(new RequestFilter());
        requestSpecification.pathParams("bookingid",90009);
        //requestSpecificationDeleteBooking.basePath("booking/{bookingid}");
        var response=requestSpecification.delete("booking/{bookingid}");

        response.then().assertThat()
                .statusCode(405)
                .time(lessThan(7000L));
    }
}
