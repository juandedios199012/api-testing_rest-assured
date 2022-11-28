package login;

import base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import model.login.Login;
import org.testng.annotations.Test;
import utilities.RequestFilter;

import static io.restassured.http.Method.POST;
import static org.hamcrest.Matchers.lessThan;

public class AuthTests extends BaseTest {
    public static String schemaFile="booking/authResponse.json";

    @Test
    public void authTest(){

        RestAssured.baseURI=baseURL;
        var requestSpecification=RestAssured.given();

        requestSpecification.contentType(ContentType.JSON);

        requestSpecification.filter(new RequestFilter());

        requestSpecification.basePath("auth");

        Login login=new Login();
        requestSpecification.body(login);

        var response=requestSpecification.request(POST);

        response.then().assertThat()
                .statusCode(200)
                .time(lessThan(7000L))
                .body(JsonSchemaValidator.matchesJsonSchema(getSchema(schemaFile)));

    }
}
