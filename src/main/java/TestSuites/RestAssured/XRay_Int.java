package TestSuites.RestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class XRay_Int {

    @Test
    public void firstTestCase() {
        Response response= RestAssured.get("https://jsonplaceholder.typicode.com/todos/1");
//        Response response= RestAssured.get("https://smartergroup.atlassian.net/rest/api/3/issue/TSAND-29");
        System.out.println("Response\t:\n"+response.asString()+"\n");
        System.out.println("Status Code\t:\n"+response.getStatusCode()+"\n");
        System.out.println("Response Body\t:\n"+response.getBody().asString()+"\n");
        System.out.println("Time taken\t:\n"+response.getTime()+"\n");
        System.out.println("Headers\t:\n"+response.getHeaders()+"\n");
    }
}
