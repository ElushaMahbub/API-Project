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

public class DeleteOneProduct {

	String baseURI;
	HashMap<String,String> deletePayload;

	public  DeleteOneProduct() {

	

		baseURI = "https://techfios.com/api-prod/api/product";
		deletePayload = new HashMap<String,String>();
	}
	
	public Map<String, String> deletePayLoadMap(){
		deletePayload.put("id", "9193");
		
		return deletePayload;
	}

	@Test(priority=1)
	public void deleteOneProduct() {

		Response response =

	  given()
		   .baseUri(baseURI)
		   .header("Content-Type", "application/json; charset=UTF-8")
		   .auth().preemptive().basic("demo@techfios.com", "abc123")
		   .body(deletePayLoadMap()).
      when()
          .delete("/delete.php").
            
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
		Assert.assertEquals(productMessage, "Product was deleted.", "Product Names are NOT matching");
		
		
		
		}
	
	@Test(priority=2)
	public void readSameProduct() {

		Response response =

	  given()
		   .baseUri(baseURI)
		   .header("Content-Type", "application/json")
		   .queryParam("id", deletePayLoadMap().get("id"))
		   .auth().preemptive().basic("demo@techfios.com", "abc123").
      when()
          .get("/read_one.php").
            
      then()
          .extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("Statuscode:" + statusCode);
		Assert.assertEquals(statusCode, 404, "Status codes are NOT matching!");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		
		String actualProductMessage = jp.get("message");
		System.out.println("Actual Product message:" + actualProductMessage);
		Assert.assertEquals(actualProductMessage, "Product does not exist.", "Product messages are NOT matching!");
		
		 
		}

		

	
}
