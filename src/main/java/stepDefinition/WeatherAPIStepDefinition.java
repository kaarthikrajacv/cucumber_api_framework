package stepDefinition;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import deserialisedClasses.WeatherDetails;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherAPIStepDefinition {
	
	Response response;
	
	ObjectMapper mapper = new ObjectMapper();
	
	RequestSpecification request;
	
	DataTable datatable;
	
	Scenario scenario;
	
	@Before
	public void initializeDataTable(Scenario scenario) throws Exception{
		
		this.scenario = scenario;
		
		datatable = new DataTable("src/main/resources/excel/weather.xls");
		
		datatable.createConnection();
	}
	
	@Given("I hit weather API with valid data")
	public void hitWeatherAPI() {
		
		RestAssured.baseURI = "http://api.openweathermap.org";
		request = RestAssured.given();
	}
	
	@When("I hit a GET API")
	public void hitGetMethod() throws Exception {
		
		FileInputStream fis = new FileInputStream("src/main/resources/properties/weather.properties");
		
		/**
		 * The steps are for reading from a JSON file
		 */
		FileInputStream jsonFis = new FileInputStream("src/main/resources/json/weather.json");
		ObjectMapper mapper =  new ObjectMapper();
		JsonNode weatherJson = mapper.readTree(jsonFis);
		
		System.out.println("The Json Node weather detail is "+ weatherJson);
		
		// Json reading end
		
//		Properties prop = new Properties();
//		prop.load(fis);
//		
//		prop.put("abc", "xyz");
		System.out.println("The scenario name is " + scenario.getName());
		String city = datatable.getDataFromExcel(scenario.getName(), "City");
		String appid = datatable.getDataFromExcel(scenario.getName(), "appid");
		
		System.out.println("The City is " + city +" and the app id is " + appid);
		
		Map<String, Object> query = new HashMap<>();
		query.put("q", city);
		query.put("appid", appid);
		
		response = request.given().queryParams(query).when().get("/data/2.5/weather");
		
		System.out.println(response.asString());
		
		fis.close();
		jsonFis.close();
	}


	@Then("Validate API response")
	public void validateAPIResponse() throws Exception {
		
		int statusCode = response.getStatusCode();
		
		System.out.println(statusCode);
		
		WeatherDetails weather = mapper.readValue(response.asString(), WeatherDetails.class);
		
		System.out.println("The base is " + weather.getBase());
		
		Assert.assertEquals(statusCode, 201);
		
		Assert.assertEquals(weather.getBase(), "stations");
	    
	}

}
