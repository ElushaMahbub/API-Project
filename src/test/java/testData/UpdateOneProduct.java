package testData;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UpdateOneProduct {

	String baseURI;
	HashMap<String,String> updatePayload;

	public  UpdateOneProduct() {

		/*
		given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization)
        when:  submit api requests(Http method,Endpoint/Resource)
        then:  validate response(status code, Headers, responseTime, Payload/Body)
        02.ReadOneProduct
		HTTP Method: GET
		EndpointUrl: https://techfios.com/api-prod/api/product/update.php
		Authorization: Basic Auth
		Header:
		"Content-Type" : "application/json"
		QueryParam: (id is required)
		"id":"value"
		Status Code: 200
		response body
		*/

		baseURI = "https://techfios.com/api-prod/api/product";
		updatePayload = new HashMap<String,String>();
	}
	
	public Map<String, String> updatePayLoadMap(){
		updatePayload.put("id", "9199");
		updatePayload.put("name", "Amazing Pillow 30.0 By E.M");
		updatePayload.put("price", "999");
		updatePayload.put("description", "The best pillow for amazing QA.");
		updatePayload.put("category_id", "2");
		updatePayload.put("category_name", "Electronics");
		
		
		return updatePayload;
	}

	@Test(priority=1)
	public void updateOneProduct() {

		Response response =

	  given()
		   .baseUri(baseURI)
		   .header("Content-Type", "application/json")
		   .auth().preemptive().basic("demo@techfios.com", "abc123")
		   .body(updatePayLoadMap()).
      when()
          .put("/update.php").
            
      then()
          .extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Statuscode:" + statusCode);
		Assert.assertEquals(statusCode, 200, "Status codes are NOT matching!");

		String responseHeader = response.getHeader("Content-Type");
		System.out.println("Response Header:" + responseHeader);
		Assert.assertEquals(responseHeader, "application/json; charset=UTF-8", "Response headers are NOT matching!");
		
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("ResponseTime:" + responseTime);
		
		if(responseTime <= 3000) {
			System.out.println("Response time is within range");
		
		}else 
			System.out.println("Response time is out of range");
		
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		
		String productMessage = jp.getString("message");
		System.out.println("Product Name:" + productMessage);
		Assert.assertEquals(productMessage, "Product was updated.", "Product Names are NOT matching");
		
		
		
		}
	
	@Test(priority=2)
	public void compareProductDetails() {

		Response response =

	  given()
		   .baseUri(baseURI)
		   .header("Content-Type", "application/json")
		   .queryParam("id", updatePayLoadMap().get("id"))
	//	   .queryParam("price", "200") if we have more than one querypram
		   .auth().preemptive().basic("demo@techfios.com", "abc123").
      when()
          .get("/read_one.php").
            
      then()
          .extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Statuscode:" + statusCode);
		Assert.assertEquals(statusCode, 200, "Status codes are NOT matching!");

		String responseHeader = response.getHeader("Content-Type");
		System.out.println("Response Header:" + responseHeader);
		Assert.assertEquals(responseHeader, "application/json", "NOT matching!");
		
		long responseTime = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("ResponseTime:" + responseTime);
		
		if(responseTime <= 3000) {
			System.out.println("Response time is within range");
		
		}else 
			System.out.println("Response time is out of range");
		
		
		String responseBody = response.getBody().asString();
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		
		String actualProductName = jp.getString("name");
		System.out.println("Actual Product Name:" + actualProductName);
		
		String actualProductDescription = jp.getString("description");
		System.out.println("Actual Product description:" + actualProductDescription);
		
		
		String actualProductPrice = jp.getString("price");
		System.out.println("Actual Product price:" + actualProductPrice);
		 
		
	//	JsonPath jp2 = new JsonPath(createPayLoadMap().toString());
		
		String expctedProductName = updatePayLoadMap().get("name");
		System.out.println("Expcted Product Name:" + expctedProductName);
		
		String expectedProductDescription = updatePayLoadMap().get("description");
		System.out.println("Expcted Product description:" + expectedProductDescription);
		
		
		String ExpectedProductPrice = updatePayLoadMap().get("price");
		System.out.println("Expcted Product price:" + ExpectedProductPrice);
		
		 Assert.assertEquals(actualProductName, expctedProductName, "Product Names are NOT matching");
		 Assert.assertEquals(actualProductDescription, expectedProductDescription, "Product description are NOT matching");
		 Assert.assertEquals(actualProductPrice, ExpectedProductPrice, "Product Price are NOT matching");
		 
		}

		

	
}
