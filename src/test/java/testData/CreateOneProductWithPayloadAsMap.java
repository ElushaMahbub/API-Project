package testData;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreateOneProductWithPayloadAsMap {

	String baseURI;
  //String filePath;
	String firstProductId;
	HashMap<String,String> createPayload;

	public  CreateOneProductWithPayloadAsMap() {

		baseURI = "https://techfios.com/api-prod/api/product";
	  //filePath = "src\\main\\java\\data\\CreatePayload.json";
		createPayload = new HashMap<String,String>();
	}
 
	
	
	public Map<String, String> createPayLoadMap(){
		createPayload.put("name", "Amazing Pillow 29.0 By Elusha");
		createPayload.put("price", "999");
		createPayload.put("description", "The best pillow for amazing programmers.");
		createPayload.put("category_id", "2");
		createPayload.put("category_name", "Electronics");
		
		
		return createPayload;
	}
	
	@Test(priority=1)
	public void createOneProducts() {

		Response response =

	  given()
		   .baseUri(baseURI)
		   .header("Content-Type", "application/json; charset=UTF-8")
		   .body(createPayLoadMap())
		   .header("Authorization","Bearer fhh24AwM58lOgh25123nP").
	       
      when()
          .post("/create.php").
            
      then()
          .extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Statuscode:" + statusCode);
		Assert.assertEquals(statusCode, 201, "Status codes are NOT matching!");

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
		Assert.assertEquals(productMessage, "Product was created.", "Product Names are NOT matching");
		
//		String productName = jp.getString("name");
//		System.out.println("Product Name:" + productName);
//		Assert.assertEquals(productName, "Amazing Pillow 7.0 By E.M", "Product Names are NOT matching");
//		
//		
//		String productDescription = jp.getString("description");
//		System.out.println("Product description:" + productDescription);
//		Assert.assertEquals(productDescription, "The best pillow for amazing programmers.", "Product description are NOT matching");
//		
//		String productPrice = jp.getString("price");
//		System.out.println("Product price:" + productPrice);
//		 Assert.assertEquals(productPrice, "599", "Product Price are NOT matching");
//		
		}

	@Test(priority=2)
	public void readAllProducts() {

	
		Response response =
	
	given()
		.baseUri(baseURI)
		.header("Content-Type","application/json; charset=UTF-8").

	when()
		.get("/read.php").
	then()

		.extract().response(); // it has to be the last action of the Response
		
	
		String responseBody = response.getBody().asString(); 
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		firstProductId = jp.get("records[0].id");
		System.out.println("First Product Id:" + firstProductId);
		
	}	
	
	@Test(priority=3)
	public void compareProductDetails() {

		Response response =

	  given()
		   .baseUri(baseURI)
		   .header("Content-Type", "application/json")
		   .queryParam("id", firstProductId)
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
		
		String expctedProductName = createPayLoadMap().get("name");
		System.out.println("Expcted Product Name:" + expctedProductName);
		
		String expectedProductDescription = createPayLoadMap().get("description");
		System.out.println("Expcted Product description:" + expectedProductDescription);
		
		
		String ExpectedProductPrice = createPayLoadMap().get("price");
		System.out.println("Expcted Product price:" + ExpectedProductPrice);
		
		 Assert.assertEquals(actualProductName, expctedProductName, "Product Names are NOT matching");
		 Assert.assertEquals(actualProductDescription, expectedProductDescription, "Product description are NOT matching");
		 Assert.assertEquals(actualProductPrice, ExpectedProductPrice, "Product Price are NOT matching");
		 
		}

		
}
