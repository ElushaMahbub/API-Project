package testData;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

public class ReadOneProduct {

	String baseURI;

	public  ReadOneProduct() {

		/*
		given: all input details(baseURI,Headers,Payload/Body,QueryParameters,Authorization)
        when:  submit api requests(Http method,Endpoint/Resource)
        then:  validate response(status code, Headers, responseTime, Payload/Body)
        02.ReadOneProduct
		HTTP Method: GET
		EndpointUrl: https://techfios.com/api-prod/api/product/read_one.php
		Authorization: Basic Auth
		Header:
		"Content-Type" : "application/json"
		QueryParam: (id is required)
		"id":"value"
		Status Code: 200
		response body
		*/

		baseURI = "https://techfios.com/api-prod/api/product";
	}

	@Test
	public void readOneProducts() {

		Response response =

	  given()
		   .baseUri(baseURI)
		   .header("Content-Type", "application/json")
		   .queryParam("id", "8768")
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
		
		String productName = jp.getString("name");
		System.out.println("Product Name:" + productName);
		Assert.assertEquals(productName, "Amazing Pillow 7.0 By E.M", "Product Names are NOT matching");
		
		
		String productDescription = jp.getString("description");
		System.out.println("Product description:" + productDescription);
		Assert.assertEquals(productDescription, "The best pillow for amazing programmers.", "Product description are NOT matching");
		
		String productPrice = jp.getString("price");
		System.out.println("Product price:" + productPrice);
		 Assert.assertEquals(productPrice, "399", "Product Price are NOT matching");
		
		
		
		}

	
}
