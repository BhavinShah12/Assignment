package base;

import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.aventstack.extentreports.gherkin.model.Scenario;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import utils.Logging;

public class Base 
{
	// Declarinng variables
	protected RequestSpecification httpRequest;
	protected String bodyAsString;
	protected ResponseBody<?> body;
	protected Response response;
	protected int actStatusCode;
	protected io.cucumber.core.api.Scenario scn=null;
	public static Logger logger;
	
	// Constructor 
	public Base() 
	{
		logger = Logging.getLogger(Logging.class);
	}
	
	// Method to return response
	public Response getResponse() 
	{
		return response;
	}
	
	// Method to retrieve json path
	public JsonPath getJsonPath() 
	{
		return response.jsonPath();
	}
	
	// Using hashmap to retrieve rates of exchanges
	public LinkedHashMap<String, String> getResponseMap() 
	{
		return new LinkedHashMap<String, String>();
	}

	// Method to verify if API is accessible
	public void isAPIAccessible(String baseURI, int expStatusCode) 
	{
		logger.info("Checking if API '" + baseURI + "' is accessible");
		try 
		{
			// Instance of Requestspecification object
			RestAssured.baseURI = baseURI;
			httpRequest = RestAssured.given();

			// Response object
			response = httpRequest.get();
			actStatusCode = response.getStatusCode();

			// Logging status code and response
			logger.info("API response received : " + response.asString());
			logger.info("Response Status code '" + actStatusCode + "'");

			// Verify that expected and actual status codes are equal
			Assert.assertEquals(expStatusCode, actStatusCode);
			logger.info("API '" + baseURI + "' is accessible");
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception caught : " + e.getMessage());
			throw new RuntimeException("TestBase::isAPIAccessible() -> Exception caught : " + e.getMessage()); 
		}
	}
	
	public void hitEndpoint(String endPoint) 
	{
		logger.info("Hitting endpoint '" + endPoint + "'");
		try {
			// Requestspecification object
			RestAssured.baseURI = endPoint;
			httpRequest = RestAssured.given();

			// Response object
			response = httpRequest.get();
			body = response.getBody();
			actStatusCode = response.getStatusCode();

			// Log response
			bodyAsString = body.asString();
			logger.info("Response received : " + bodyAsString);
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception caught : " + e.getMessage());
			throw new RuntimeException("TestBase::hitEndpoint() -> Exception caught : " + e.getMessage());
		}
	}
	
	public void hitEndpoint(String endPoint, String queryParamField, String queryParamValue) 
	{
		logger.info(
				"Hitting endpoint '" + endPoint + "', queryParams '" + queryParamField + "," + queryParamValue + "'");
		try 
		{
			// Requestspecification object
			RestAssured.baseURI = endPoint;
			httpRequest = RestAssured.given();

			// Setup query parameters
			response = httpRequest.queryParam(queryParamField, queryParamValue).get();

			// Get response and status code
			body = response.getBody();
			bodyAsString = body.asString();
			actStatusCode = response.getStatusCode();

			// log response and status code
			logger.info("Response received : " + bodyAsString);
			logger.info("Response status code : '" + actStatusCode + "'");
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception caught : " + e.getMessage());
			throw new RuntimeException("TestBase::hitEndpoint() -> Exception caught : " + e.getMessage());
		}
	}
}
