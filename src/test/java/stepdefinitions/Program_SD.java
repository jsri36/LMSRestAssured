package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;
import utilities.Excelreader;
import utilities.Excelwriter;
import utilities.LoggerLoad;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
//import java.io.IOException;
//import java.util.LinkedHashMap;
import java.util.Random;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
public class Program_SD {

	ConfigReader config = new ConfigReader();	
	String baseURL=config.getBaseUrl();
	String getallurl=config.getProperty_postall_Url();
	String getpost = config.getProperty_Post_Url ();
	String getById= config.getProperty_Getbyid_Url();
	String getByName = config.getProperty_Program_put_Url();
	String putById = config.getProperty_Progam_putbyid_Url();
	String deleteById = config.getProperty_Program_deletebyid_Url();
	String deleteByName = config.getProgram_deletebyname_Url();
	
	
	public static String programID;
	public static String ProgramID1;
	public static String ProgramID2;
	public static String ProgramName;
	public static String ProgramName1;
	public static String ProgramName2;
	String excelpath =".\\src/test/resources/data/Program.xlsx";
	String writeexcelpath =".\\src/test/resources/data/IdExcel.xlsx";
	//public int int1 = 0;
	public RequestSpecification request;
	public int rowval;
	Response response;
	Random random = new Random();
	int random_num = random.nextInt(50);
	String random_string = UUID.randomUUID().toString();
	public static String givenprogramID ;
	JSONObject requestbody = new JSONObject();
	
	LinkedHashMap<String, String> testdata;
	LinkedHashMap<String, String> iddata;
	JSONArray jsonArray = new JSONArray();
	
	@SuppressWarnings("unchecked")
	@Given("The POST endpoint and the reqeust payload from {string} and {int} for Assignment")
	public void the_post_endpoint_and_the_reqeust_payload_from_and_for_assignment(String string, Integer int1) throws IOException {
		LoggerLoad.info("********* Create Program *********");
		request = given().baseUri(baseURL);
		   
			Excelreader reader = new Excelreader();	
			
			testdata = reader.readexcelsheet(excelpath,"Sheet1",int1);
			rowval=int1;
			
					
			requestbody.put("programDescription",testdata.get("programDescription"));
			requestbody.put("programName",testdata.get("programName"));
			requestbody.put("programStatus",testdata.get("programStatus"));
						
			System.out.println(requestbody.toString());
	}

	@When("I send a POST reqeust for creating an program")
	public void i_send_a_post_reqeust_for_creating_an_program() {
	
		//response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().post("/saveprogram");
		response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().post(getpost);
		//response = request.when().body(requestbody.toJSONString()).
			//	post(baseURL+getpost).
				//then().
				//statusCode(201).log().all().extract().response();
		
		//response = request.when().body(requestbody.toJSONString()).get(baseURL+getpost);
		System.out.println("Response from  = " + response.asPrettyString());
			
			
	}
	
	
	@Then("The program is successfully created {int}")
	public void the_program_is_successfully_created(Integer int1) throws IOException {
		/*if (response.getStatusCode()==201) {
			System.out.println("Program Post Creation Success");
			
			System.out.println(requestbody.toString());
			programID = response.body().jsonPath().getString("programId");
			   System.out.println("programID from response = " + programID);
			*/
		
			   if (response.getStatusCode()==201) {
					System.out.println("User Post Creation Success");
				}
				System.out.println("rowval "+rowval);
				JsonPath js = new JsonPath(response.asString());
				Excelwriter writer = new Excelwriter();
				if (rowval==0) {
					
					//programID1 = js.getString("programId");
					ProgramID1 = response.body().jsonPath().getString("programId");
					writer.WriteExcel(writeexcelpath, "Sheet1", ProgramID1, 0);
					//System.out.println("programid1 from excel sheet "+iddata.get("programID1"));
					
					ProgramName1 = response.body().jsonPath().getString("programName");
					writer.WriteExcel(writeexcelpath, "Sheet1", ProgramName1, 2);
					//System.out.println("programname1 from excel sheet "+iddata.get("ProgramName1"));
					
				}
				else if (rowval==1) {
					//programID2 = js.getString("programId");
					ProgramID2 = response.body().jsonPath().getString("programId");
					writer.WriteExcel(writeexcelpath, "Sheet1", ProgramID2, 1);
					//System.out.println("programid2 from excel sheet "+iddata.get("programID2"));
					
					ProgramName2 = response.body().jsonPath().getString("programName");
					writer.WriteExcel(writeexcelpath, "Sheet1", ProgramName2, 3);
					//System.out.println("programname1 from excel sheet "+iddata.get("ProgramName2"));
					
		}
	}
	/* tag1  post 400 all ready created */
	@SuppressWarnings("unchecked")
	@Given("User creates POST Request for the LMS API endpoint")
	public void user_creates_post_request_for_the_lms_api_endpoint() throws IOException {
		LoggerLoad.info("********* Create Program 400 *********");
		   request = given().baseUri(baseURL);
	   
		   Excelreader reader = new Excelreader();	
		
		   testdata = reader.readexcelsheet(excelpath,"Sheet1",0);
			//rowval=int1;
		
				
					requestbody.put("programDescription",testdata.get("programDescription"));
					requestbody.put("programName",testdata.get("programName"));
					requestbody.put("programStatus",testdata.get("programStatus"));   
	}

	@When("send request body from excel")
	public void send_request_body_from_excel() {
		response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().post(getpost);  
	}

	@Then("response body should be {int} program already exists")
	public void response_body_should_be_program_already_exists(Integer int1) {
		response.then().statusCode(400).log().all();
	}

	
	/* tag 2  post 400 missing value */
	@SuppressWarnings("unchecked")
	@Given("User creates request for missing field")
	public void user_creates_request_for_missing_field() throws IOException {
		LoggerLoad.info("********* Create Program Missing Field *********");
		request = given().baseUri(baseURL);
		   
		   Excelreader reader = new Excelreader();	
		
		   testdata = reader.readexcelsheet(excelpath,"Sheet1",0);
			//rowval=int1;
		
				
					requestbody.put("programDescription",testdata.get("programDescription"));
					requestbody.put("programName",testdata.get("programName"));
					requestbody.put("programStatus"," "); 
	}

	@When("send the request body with null data")
	public void send_the_request_body_with_null_data() {
		response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().post(getpost);
	}

	@Then("response is {int} for missing data with boolean")
	public void response_is_for_missing_data_with_boolean(Integer int1) {
		response.then().statusCode(400).log().all();
	}
	


	
/*==tag 3 GEt all pogram===	*/
	
	
	@Given("There are program")
	public void there_are_program() throws IOException {
		LoggerLoad.info("********* Get All Program *********");
		 request = given().baseUri(baseURL);
		
	/*	Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		
		System.out.println("programid1 from excel sheet "+iddata.get("programID1"));
		System.out.println("programid2 from excel sheet "+iddata.get("programID2"));
		*/
	}

		@When("I fetch data with program id")
		public void i_fetch_data_with_program_id() {
		response = request.when().get(baseURL+getallurl);
		//response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().get("/allPrograms");
		System.out.println("Response from  = " + response.asPrettyString());
		
	}
	@Then("The program are listed")
	public void the_program_are_listed() {
		response.then().statusCode(200).log().all();
	}

	
	
/*==tag 4 Get data with program ID ==*/	
	
	@Given("send a get request by using programid")
	public void send_a_get_request_by_using_programid() {
		LoggerLoad.info("********* Get by Id*********");
		 request = given().baseUri(baseURL);
	}

	@When("store the program id and assign it as a endpoint")
	public void store_the_program_id_and_assign_it_as_a_endpoint() throws IOException {
		Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		
		//response = request.when().get(baseURL+"/programs/"+iddata.get("programID2")); 
		System.out.println("URL"+getById);
		System.out.println("URL"+baseURL+getById+iddata.get("ProgramID1"));
		response = request.when().get(baseURL+getById+iddata.get("ProgramID1"));
		System.out.println("Response from  = " + response.asPrettyString());
	}

	@Then("we need to get the program with {int} status code")
	public void we_need_to_get_the_program_with_status_code(Integer int1) {
		response.then().statusCode(200).log().all();
		
	  	}

	/*==tag 5  update  data with Program name 200 ==*/
	@SuppressWarnings("unchecked")
	@When("fetch the date with stored ProgramName")
	public void fetch_the_date_with_stored_program_name() throws IOException {
		 request = given().baseUri(baseURL);
		Excelreader reader = new Excelreader();
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		  testdata = reader.readexcelsheet(excelpath,"Sheet1",0);
		  
		  requestbody.put("programDescription",testdata.get("programDescription2"));
			requestbody.put("programName",testdata.get("programName"));
			requestbody.put("programStatus",testdata.get("programStatus"));
			requestbody.put("programId",iddata.get("programID1"));
			System.out.println(requestbody.toString());
		
		//response = request.when().put(baseURL+"/programs/"+testdata.get("programName")); 
		//response = 	request.when().put(baseURL+getByName+testdata.get("programName"));
			
		response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().put(getByName+testdata.get("programName"));
		//response = reqeust.when().get(baseURL+config.getProperty_Post_Url()+testdata.get(ProgramName));
		System.out.println("Response from  = " + response.asPrettyString());
	}

	@Then("response need to status code {int}")
	public void response_need_to_status_code(Integer int1) {
		response.then().statusCode(200).log().all();
	}

	
	
	/*** tag6 negative sceanrio  with programName 404 ***/
	@Given("User creates PUT Request for the LMS API endpoint  and Valid program Name")
	public void user_creates_put_request_for_the_lms_api_endpoint_and_valid_program_name() {
		LoggerLoad.info("********* Update by name *********");
		request = given().baseUri(baseURL);
	}
	@SuppressWarnings("unchecked")
	@When("User sends HTTPS Request  and  request Body with mandatory fields.")
	public void user_sends_https_request_and_request_body_with_mandatory_fields() throws IOException {
		Excelreader reader = new Excelreader();
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		  testdata = reader.readexcelsheet(excelpath,"Sheet1",0);
		  
		  requestbody.put("programDescription",testdata.get("programDescription2"));
			requestbody.put("programName",testdata.get("programName"));
			requestbody.put("programStatus",testdata.get("programStatus"));
			requestbody.put("programId",iddata.get("programID1"));
			System.out.println(requestbody.toString());
			ProgramName2	= iddata.get("ProgramName2");
		//response = request.when().put(baseURL+"/programs/"+testdata.get("programName")); 
		//response = reqeust.when().get(baseURL+config.getProperty_Post_Url()+testdata.get(ProgramName));
		
		//System.out.println("Put Invalid URL:"+baseURL+getByName+random_num);
		response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().put(getByName+random_num);
		//response = request.when().body(requestbody.toJSONString()).put(baseURL+getByName+random_num);
		System.out.println("Response from  = " + response.asPrettyString());
	}

	@Then("User receives {int} Not Found Status with message and boolean success details for program")
	public void user_receives_not_found_status_with_message_and_boolean_success_details_for_program(Integer int1) {
		response.then().statusCode(404).log().all();
	}

	
	/*** tag 7 negative sceanrio  with programName 400 missing value  ***/
	@Given("User creates PUT Request for the LMS API endpoint")
	public void user_creates_put_request_for_the_lms_api_endpoint() {
		LoggerLoad.info("********* Update bad request*********");
		request = given().baseUri(baseURL);
	}
	
	@SuppressWarnings("unchecked")
	@When("User sends HTTPS Request  and request Body")
	public void user_sends_https_request_and_request_body() throws IOException {
		
		Excelreader reader = new Excelreader();
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		  testdata = reader.readexcelsheet(excelpath,"Sheet1",0);
		  
		  //Mentioned in Test Resolution Docs
		  	requestbody.put(",","");

		  	requestbody.put("programName","");
			requestbody.put("programStatus","");
			requestbody.put("programId",iddata.get("ProgramID1"));
			System.out.println(requestbody.toString());
			System.out.println("URL:"+baseURL+getByName+testdata.get("programName"));
			//response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().put(getByName+testdata.get("programName"));
			response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().put(getByName+testdata.get("programName"));
			
			System.out.println("Response from  = " + response.asPrettyString());
	}

	@Then("User receives {int} Bad Request Status with message and boolean success details for program")
	public void user_receives_bad_request_status_with_message_and_boolean_success_details_for_program(Integer int1) {
	 
		response.then().statusCode(400).log().all();
	}
	
	/* --- put with invalid programID -404--tag8---*/
	
	@Given("User creates PUT Request with invalid programID")
	public void user_creates_put_request_with_invalid_program_id() {
		LoggerLoad.info("********* Update by invalid programid *********");
		request = given().baseUri(baseURL);  
	}
	@SuppressWarnings("unchecked")
	@When("User sends reqeuset body with mandatory fields")
	public void user_sends_reqeuset_body_with_mandatory_fields() throws IOException {
		Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		 testdata = reader.readexcelsheet(excelpath,"Sheet1",0);
		  requestbody.put("programDescription",testdata.get("programDescription"));
			requestbody.put("programName",testdata.get("programName"));
			requestbody.put("programStatus",testdata.get("programStatus"));
			requestbody.put("programId",iddata.get("programID1"));
			//System.out.println(requestbody.toString());
			//response = request.when().get(baseURL+putById+iddata.get("programID2"+random_num));
			//response = request.when().body(requestbody.toJSONString()).put(baseURL+putById+random_num);
			response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().put(putById+random_num);
			//response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().put("/putprogram/"+random_num);
		System.out.println("Response from  = " + response.asPrettyString());
	}

	@Then("user need to get {int} status code not found")
	public void user_need_to_get_status_code_not_found(Integer int1) {
		response.then().statusCode(404).log().all();
	}

	
	/*----------- put with pogramId with missing data 400 --tag9---*/
	
	@Given("User creates Request with valid programID")
	public void user_creates_request_with_valid_program_id() {
		LoggerLoad.info("********* Update programid missing value *********");
		request = given().baseUri(baseURL); 
	}
	@SuppressWarnings("unchecked")
	@When("user sends request with null value")
	public void user_sends_request_with_null_value() throws IOException {
	   
		Excelreader reader = new Excelreader();
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		  testdata = reader.readexcelsheet(excelpath,"Sheet1",0);
		  
		requestbody.put("programDescription",testdata.get("programddesNu"));
			requestbody.put("programName",testdata.get(" "));
			//String	subPs =testdata.get("programddesNu");
		requestbody.put("programStatus","");
			requestbody.put("programId","");
			ProgramID1	= iddata.get("programID1");
			System.out.println(requestbody.toString());
			//System.out.println("invalid subPS "+subPs);
		//response = request.when().get(baseURL+putById+iddata.get("programID1")); //method not allowed
			response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().put("/putprogram/"+ProgramID1);
			
			//response = request.when().body(requestbody.toJSONString()).put(baseURL+"/putprogram/"+programID1);
		System.out.println("Response from  = " + response.asPrettyString());
		
		
	}

	@Then("user need to get {int} mising data")
	public void user_need_to_get_mising_data(Integer int1) {
		response.then().statusCode(400).log().all();
	}
	
	
			/* put update by programID tag10*/
			
			@Given("User creates PUT Request with Valid programID")
	public void user_creates_put_request_with_valid_program_id() {
				 request = given().baseUri(baseURL);
	}
			@SuppressWarnings("unchecked")
	@When("user send request fields")
	public void user_send_request_fields() throws IOException {
		Excelreader reader = new Excelreader();
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		  testdata = reader.readexcelsheet(excelpath,"Sheet1",0);
		  
		  requestbody.put("programDescription",testdata.get("programDescription2"));
			requestbody.put("programName",testdata.get("programName"));
			requestbody.put("programStatus",testdata.get("programStatus"));
			requestbody.put("programId",iddata.get("ProgramID2"));
			//System.out.println(requestbody.toString());
			ProgramID2	= iddata.get("ProgramID2");
					//response = request.when().get(baseURL+putById+iddata.get("programID2"));
			
		//	response = request.when().body(requestbody.toJSONString()).put(baseURL+config.getProperty_Progam_putbyid_Url()+programID2);
			System.out.println("URL"+baseURL+config.getProperty_Progam_putbyid_Url()+ProgramID2);
		response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().put("/putprogram/"+iddata.get("ProgramID2"));
		System.out.println("Response1 from  = " + response.asPrettyString());
	}

	@Then("user need to get {int} status code")
	public void user_need_to_get_status_code(Integer int1) {
		response.then().statusCode(200).log().all();
	}

	/*** get by id 404 tag11***/
	
	@Given("user creates get request with invalid programID")
	public void user_creates_get_request_with_invalid_program_id() {
		request = given().baseUri(baseURL);
	}

	@When("user send request fields with invalid id")
	public void user_send_request_fields_with_invalid_id() throws IOException {
		Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		ProgramID2	= iddata.get("programID2");
		//response = request.when().get(baseURL+"/programs/"+iddata.get("programID2")); 
		response = request.when().get(baseURL+getById+random_num);
		System.out.println("Response from  = " + response.asPrettyString());  
	}

	@Then("response need to be {int} not found status")
	public void response_need_to_be_not_found_status(Integer int1) {
		response.then().statusCode(404).log().all();
	}
	
	
	/**** Delete with programName 200 tag12***/
	@Given("User creates DELETE Request for the LMS API endpoint  and  valid programName")
	public void user_creates_delete_request_for_the_lms_api_endpoint_and_valid_program_name() {
		LoggerLoad.info("********* Delete by program name *********");
		request = given().baseUri(baseURL);
	}
	
	@When("User will send the request body along with programName")
	public void user_will_send_the_request_body_along_with_program_name() throws IOException {
		Excelreader reader = new Excelreader();
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		 		  		  
			ProgramName1	= iddata.get("ProgramName1");
			System.out.println("URL"+baseURL+config.getProgram_deletebyname_Url()+iddata.get("ProgramName1"));
			response = request.when().delete(baseURL+config.getProgram_deletebyname_Url()+iddata.get("ProgramName1"));
			System.out.println("Response1 from  = " + response.asPrettyString());
		}

	@Then("response body {int} successfully deleted")
	public void response_body_successfully_deleted(Integer int1) {
		response.then().statusCode(200).log().all();
	}

	
	/**** Delete with programName 400  tag13 ***/
	@Given("User creates DELETE Request for the LMS API endpoint  and  invalid programName")
	public void user_creates_delete_request_for_the_lms_api_endpoint_and_invalid_program_name() {
		request = given().baseUri(baseURL);
	}
	
	@When("user send with invalid ProgramName")
	public void user_send_with_invalid_program_name() throws IOException {
		Excelreader reader = new Excelreader();
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		
		  	  
			ProgramName2	= iddata.get("ProgramName2");
			//response = request.header("","").contentType("application/json").accept("application/json").body(requestbody.toJSONString()).when().put("/deletebyprogname/"+"random_num");
			response = request.when().delete(baseURL+config.getProgram_deletebyname_Url()+random_string);
			System.out.println("Response1 from  = " + response.asPrettyString()); 
	}

	@Then("response body {int} program not found")
	public void response_body_program_not_found(Integer int1) {
		response.then().statusCode(404).log().all();
	}
 /***** deleteById 200 tag14 ****/
 
 @Given("User creates DELETE Request valid program ID")
 public void user_creates_delete_request_valid_program_id() {
	 LoggerLoad.info("********* Delete by id *********");
	 request = given().baseUri(baseURL);
 }

 @When("user will send the request with valid program iD")
 public void user_will_send_the_request_with_valid_program_i_d() throws IOException {
	 Excelreader reader = new Excelreader();
	 iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
	 ProgramID2 = iddata.get("ProgramID2");
	 System.out.println("URL:"+baseURL+config.getProperty_Program_deletebyid_Url()+iddata.get("ProgramID1"));
	 response = request.when().delete(baseURL+config.getProperty_Program_deletebyid_Url()+iddata.get("ProgramID1"));
		System.out.println("Response1 from  = " + response.asPrettyString()); 
 }

 @Then("Response body should be {int} successfully deleted")
 public void response_body_should_be_successfully_deleted(Integer int1) {
	 response.then().statusCode(200).log().all();
 }

 /***** deleteById 404 tag1 ****/
 
 @Given("User create delete request with invalid programID")
 public void user_create_delete_request_with_invalid_program_id() {
	 request = given().baseUri(baseURL);
 }

 @When("user will send the request with invalid program iD")
 public void user_will_send_the_request_with_invalid_program_i_d() throws IOException {
	 Excelreader reader = new Excelreader();
	 iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
	 ProgramID2 = iddata.get("programID2");
	 response = request.when().delete(baseURL+config.getProperty_Program_deletebyid_Url()+random_num);
		System.out.println("Response1 from  = " + response.asPrettyString());
 }

 @Then("Response body should be {int} program not found")
 public void response_body_should_be_program_not_found(Integer int1) {
	 response.then().statusCode(404).log().all();
 }

	
	
}
