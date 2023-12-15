package testData;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

public class ReadAllProducts {
	
	String baseURI;
	
	public ReadAllProducts() {
		
	 baseURI = "https://techfios.com/api-prod/api/product/read.php";
	}
	@Test
	public void readAllProducts() {

	/* given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization)
       when:  submit api requests(Http method,Endpoint/Resource)
       then:  validate response(status code, Headers, responseTime, Payload/Body)
       01.ReadAllProducts
       Http method: GET
       BaseUrl=https://techfios.com/api-prod/api/product
       Endpoint=/read.php
       
       "Content-type" : "application/json; charset=UTF"
	 */
		Response response =
	
	given()
		.baseUri(baseURI)
		.header("Content-Type","application/json; charset=UTF-8").
//		.log().all().
	when()
		.get("/read.php").
	then()
//	  	.log().all()
		.extract().response(); // it has to be the last action of the Response
		
		int statusCode = response.getStatusCode();
		System.out.println("Statuscode:" + statusCode);
		Assert.assertEquals(statusCode, 200, "Status codes are NOT matching!");
		
		String responseHeader = response.getHeader("Content-Type");
		System.out.println("Response Header:" + responseHeader);
		Assert.assertEquals(responseHeader, "application/json; charset=UTF-8", "NOT matching!");
		
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("Response Time:" + responseTime);
		
		if(responseTime <= 2000) {
			System.out.println("Response time is in range");
		}else {
			System.out.println("Response time out of range!");
		}
	
		String responseBody = response.getBody().asString(); 
		System.out.println("Response Body:" + responseBody);
		
		
		JsonPath jp = new JsonPath(responseBody);
		String firstProductId = jp.get("records[0].id");
	//	System.out.println("First Product Id:" + firstProductId);
		
		if(firstProductId != null) {
			System.out.println("FirstProduct Id is NOT null");
		}else {
			System.out.println("FirstProduct Id is Null !");
		}
		
		
		
	}
}
