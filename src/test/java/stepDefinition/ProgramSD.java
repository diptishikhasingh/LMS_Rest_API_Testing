package stepDefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static io.restassured.RestAssured.*;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.junit.Assert.assertEquals;
import payload.ProgramPayload;
import requestBody.ProgramBody;
import utilities.XLUtils;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class ProgramSD{
	public static Response response;
	private static String baseURL;
	public String statusLine;
	public static String payload;
	public static Logger log = LogManager.getLogger();
	public static ProgramPayload ProgramPayload= new ProgramPayload();
	public static ResourceBundle Endpoints = ResourceBundle.getBundle("endpoints");
	public static ResourceBundle path = ResourceBundle.getBundle("path");
	public static XLUtils xlutils = new XLUtils(path.getString("Xlpath"));
	public static File programPutjson= xlutils.getJSONFile(path.getString("programPutSchemajson"));
	public static File programPostjson= xlutils.getJSONFile(path.getString("programPostSchemajson"));
	
	@Given("User is provided with the BaseUrl and the Endpoints")
	public void user_is_provided_with_the_BaseUrl_and_the_Endpoints() {
		baseURL= Endpoints.getString("BaseUrl");
		RestAssured.baseURI=baseURL;
		
		log.info("***User sends request with BaseURL***");
	}
	
	//Get By All
	@When("User send the HTTPsGET request")
	public void user_send_the_HTTPsGET_request() {
		response= given().when().get(Endpoints.getString("Program_GetAll_URL"));
		
		log.info("****Get all programs****");
	}
	
	//Get By Program ID
	@When("User send the HTTPsGET request with valid programID")
	public void user_send_the_HTTPsGET_request_with_valid_programID() {
		//response= RestAssured.get(baseURL+endpoint);
		response= given().when().get(Endpoints.getString("Program_GetbyID_URL")+ProgramPayload.getProgramId());
		
		log.info("****Get program with valid Program ID****");
	}
	
	//Post
	@When("User send the HTTPsPOST request to server with the payload with mandatory and additional fields")
	public void user_send_the_HTTPsPOST_request_to_server_with_the_payload_with_mandatory_and_additional_fields() throws IOException {
			ProgramPayload = ProgramBody.PostBody();
			response= given().contentType(ContentType.JSON).accept(ContentType.JSON).body(ProgramPayload).when().post(Endpoints.getString("Program_Post_URL"));
		    ProgramPayload.setProgramId((Integer) response.path("programId"));
		    
		    log.info("****************ProgramID : "+response.path("programId")); 
		    log.info("****New program is created with non-existing fields****");
	}
	
	//Put by programId
	@When("User send the HTTPsPUT request with valid programID and the payload")
	public void user_send_the_HTTPsPUT_request_with_valid_programID_and_the_payload() throws IOException {
		ProgramPayload = ProgramBody.PutBodyById();
		response= given().contentType(ContentType.JSON).accept(ContentType.JSON).body(ProgramPayload)
				.when().put(Endpoints.getString("Program_PutbyID_URL")+ProgramPayload.getProgramId());
		
		log.info("****Update program with Valid ProgramID****");
	}
	
	//Put by programName
	@When("User send the HTTPsPUT request with valid programName and payload")
	public void user_send_the_HTTPsPUT_request_with_valid_programName_and_payload() throws IOException {
		ProgramPayload = ProgramBody.PutBodyByName();
		response= given().contentType(ContentType.JSON).accept(ContentType.JSON).body(ProgramPayload)
				.when().put(Endpoints.getString("Program_PutbyName_URL")+ProgramPayload.getProgramName());
	    
		log.info("****Update program with Valid ProgramName****");
	}

	//Delete by programName
	@When("User send the HTTPsDELETE request with valid programName")
	public void user_send_the_HTTPsDELETE_request_with_valid_programName() {
		response= given().when().
				delete(Endpoints.getString("Program_DeletebyName_URL")+ProgramPayload.getProgramName()); 
		
		log.info("****Update program with valid Program Name****");
	}

	//Delete by programId
	@When("User send the HTTPsDELETE request with valid programId")
	public void user_send_the_HTTPsDELETE_request_with_valid_programId() {
		response= given().when().
				delete(Endpoints.getString("Program_DeletebyID_URL")+ProgramPayload.getProgramId());
		
		log.info("****Delete program with valid Program ID****");
	}
	
	//Validations
	//for get
	@Then("User validates the response with Status code {int} OK with response body")
	public void user_validates_the_response_with_Status_code_OK_with_response_body(Integer statuscode) {
		if(statuscode==200)
		{
			response.then().assertThat().statusCode(statuscode);
			
			log.info("The response body is "+response.getBody().asPrettyString());
			log.info("The response time is "+response.getTime());
			log.info("The status code is "+response.getStatusCode());
			log.info("The status line is "+response.getStatusLine());
		}
		else
			log.info("*********** Request Failed **************");
	}

	//post
	@Then("User validates the response with Status code {int} Created Status with response body")
	public void user_validates_the_response_with_Status_code_Created_Status_with_response_body(Integer statuscode) {
	    if(statuscode==201)
	    {
			response.then().assertThat().statusCode(statuscode)
			.body(matchesJsonSchema(programPostjson));
			
			assertEquals(ProgramPayload.getProgramName(),response.jsonPath().getString("programName"));
			assertEquals(ProgramPayload.getProgramStatus(),response.jsonPath().getString("programStatus"));
			assertEquals(ProgramPayload.getProgramDescription(),response.jsonPath().getString("programDescription"));
			
			log.info("The response body is "+response.getBody().asPrettyString());
			log.info("The response time is "+response.getTime());
			log.info("The status code is "+response.getStatusCode());
			log.info("The status line is "+response.getStatusLine());
		    
	    }
	    else {
	    	log.info("***************Request failed********************");
	    	log.error("*************400 bad Request******************");
	    }
	}

	//put
	@Then("User validates the response with Status code {int} OK status with updated value in response body")
	public void user_validates_the_response_with_Status_code_OK_status_with_updated_value_in_response_body(Integer statuscode) {
	    if(statuscode==200)
	    {
	    	response.then().assertThat()
	    	.statusCode(statuscode)
	    	.body(matchesJsonSchema(programPutjson));

	    	assertEquals(ProgramPayload.getProgramName(),response.jsonPath().getString("programName"));
			assertEquals(ProgramPayload.getProgramStatus(), response.jsonPath().getString("programStatus"));
			assertEquals(ProgramPayload.getProgramDescription(), response.jsonPath().getString("programDescription"));
			
			log.info("Program updated successfully with status code " + response.getStatusCode()) ;
			log.info("Program Respose body" +response.getBody().asPrettyString());
	    }
	    else
	    {
		    log.info("***************Request failed********************");
			log.error("*************400 bad Request******************");
	    }
	}

	//Delete
	
	@Then("User validates the response with Status code {int} OK status with message")
	public void user_validates_the_response_with_Status_code_OK_status_with_message(Integer statuscode) {
		if(statuscode==200)
	    {
	    	response.then().assertThat().statusCode(statuscode);
	    	
	    	log.info("Deleted successfully with status code " + response.getStatusCode());
			log.info("Deleted successfully message " + response.getBody().asString());
	    	
	    }
	    else
	    {
	    	log.info("*************** Request Failed ***************");
	    	log.info("*************** Not Found:404 ***************");
	    }	
	}
	
}