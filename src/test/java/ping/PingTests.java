package ping;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.RequestFilter;

import static io.restassured.http.Method.GET;
import static org.hamcrest.Matchers.lessThan;

public class PingTests extends BaseTest {

    @Test
    public void healthCheckTest(){

        RestAssured.baseURI=baseURL;
        var requestSpecification=RestAssured.given();

        requestSpecification.contentType(ContentType.JSON);

        requestSpecification.filter(new RequestFilter());

        requestSpecification.basePath("ping");
        var response=requestSpecification.request(GET);

        response.then().assertThat()
                        .statusCode(201)
                        .time(lessThan(4000L));
    }
}
