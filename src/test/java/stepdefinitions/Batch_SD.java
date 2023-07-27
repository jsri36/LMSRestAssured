package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;
import utilities.Excelreader;
import utilities.Excelwriter;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;
import utilities.LoggerLoad;

public class Batch_SD {
	
	ConfigReader config = new ConfigReader();	
	String baseURL=config.getBaseUrl();

	String excelpath = "src\\test\\resources\\data\\Program_Batch.xlsx";
	String writeexcelpath =".\\src/test/resources/data/IdExcel.xlsx";
	Excelreader reader = new Excelreader();	
	LinkedHashMap<String, String> testdata;
	LinkedHashMap<String, String> iddata;
	
	RequestSpecification request;
	Response response;
	JSONObject requestbody = new JSONObject();
	
	String batchDescription,batchName,batchNoOfClasses,batchStatus,programId;
	
	int Batch_ID;
	public int rowval;
	public static String BatchId1;
	public static String BatchId2;

	//Using random values for negative scenarios
	Random random = new Random();
	int random_num = random.nextInt(50);
	String random_string = UUID.randomUUID().toString();
	
/*	public PrintStream log ;
    RequestLoggingFilter requestLoggingFilter;
    ResponseLoggingFilter responseLoggingFilter;
    
   @Before
   public void init() throws FileNotFoundException {
        
        log = new PrintStream(new FileOutputStream("test_logging.txt"),true);  
        requestLoggingFilter = new RequestLoggingFilter(log);
        responseLoggingFilter = new ResponseLoggingFilter(log);
         
   }*/
	//@PostBatch_Status200
	
	@Given("User creates Batch POST Request for the LMS API endpoint")
	public void user_creates_batch_post_request_for_the_lms_api_endpoint() {
		LoggerLoad.info("********* Creating Batch *********");
		request =  given().contentType(ContentType.JSON);
	}

	@SuppressWarnings("unchecked")
	@When("User sends HTTPS Request with all fields from {string} and {int}")
	public void user_sends_https_request_with_all_fields_from_and(String string, Integer int1) throws IOException {
		
		testdata = reader.readexcelsheet(excelpath,string,int1);
		
		rowval = int1;
		
		batchDescription = testdata.get("batchDescription");
		batchName = testdata.get("batchName");
		batchNoOfClasses = testdata.get("batchNoOfClasses");
		batchStatus = testdata.get("batchStatus");
		
		requestbody.put("batchDescription",testdata.get("batchDescription"));
		requestbody.put("batchName",testdata.get("batchName"));
		requestbody.put("batchNoOfClasses",testdata.get("batchNoOfClasses"));
		requestbody.put("batchStatus",testdata.get("batchStatus"));
		
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		requestbody.put("programId",iddata.get("ProgramID1"));
		programId = iddata.get("ProgramID1");
//		requestbody.put("programId",10760);
		
		response = request.when().body(requestbody.toJSONString()).post(baseURL+config.getBatch_Post_Url());
//		System.out.println("Response = " + response.asPrettyString());
	}

	@Then("User receives {int} Created Status with response body")
	public void user_receives_created_status_with_response_body(Integer statuscode) throws IOException {
		
		response.then().log().all(); 
		Batch_ID = response.jsonPath().getInt("batchId");
		
		JsonPath js = new JsonPath(response.asString());
		Excelwriter writer = new Excelwriter();
		if (rowval==0) {
			BatchId1 = js.getString("batchId");
			writer.WriteExcel(writeexcelpath, "Sheet1", BatchId1, 4);
		}
		else if (rowval==1) {
			BatchId2= js.getString("batchId");
			writer.WriteExcel(writeexcelpath, "Sheet1", BatchId2, 5);
		}

		System.out.println("Batch_ID is:"+ Batch_ID);
			
		Assert.assertEquals(response.getStatusCode(), statuscode);
		Assert.assertNotNull(Batch_ID);
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.jsonPath().getString("batchDescription"),batchDescription );
		Assert.assertEquals(response.jsonPath().getString("batchName"),batchName );
		Assert.assertEquals(response.jsonPath().getString("batchNoOfClasses"),batchNoOfClasses );
		Assert.assertEquals(response.jsonPath().getString("batchStatus"),batchStatus );
		Assert.assertEquals(response.jsonPath().getString("programId"),programId );
		
		//Response Time Validation
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(5000L));		
	}
	
	//@PostBatch_Status400
	@Then("User receives {int} Bad Request Status with message and boolean success details")
	public void user_receives_bad_request_status_with_message_and_boolean_success_details(Integer statuscode) {
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), statuscode);	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
//		Assert.assertEquals(response.jsonPath().getString("message"),((Object)response.jsonPath().getString("message")).getClass().getSimpleName());
		Assert.assertEquals(response.jsonPath().getString("success"),"false");
	}
	
//	 @PostBatch_Status400_Missingdata
	
	@SuppressWarnings("unchecked")
	@When("User sends HTTPS Request with missing fields from {string} and {int}")
	public void user_sends_https_request_with_missing_fields_from_and(String SheetName, Integer RowNum) throws IOException {
		LoggerLoad.info("********* Creating Batch with missing mandatory fields*********");
		testdata = reader.readexcelsheet(excelpath,SheetName,RowNum);
		
		batchDescription = testdata.get("batchDescription");
	
		batchNoOfClasses = testdata.get("batchNoOfClasses");
		
		requestbody.put("batchDescription",testdata.get("batchDescription"));
		requestbody.put("batchNoOfClasses",testdata.get("batchNoOfClasses"));
		
		response = request.when().body(requestbody.toJSONString()).post(baseURL+config.getBatch_Post_Url());
	}
	
	
	//Get All Request
	
	@Given("User creates Batch GET Request for the LMS API endpoint")
	public void user_creates_batch_get_request_for_the_lms_api_endpoint() {
		LoggerLoad.info("********* Get All Batches *********");
		request =  given();
//		request.filters(requestLoggingFilter,responseLoggingFilter);
	}

	@When("User sends HTTPS Request for Get All Batches")
	public void user_sends_https_request_for_Get_All_Batches() {
		response = request.when().get(baseURL+config.getBatch_Getall_Url());
	}

	@Then("User receives {int} OK Status with response body for Get All Batches")
	public void user_receives_ok_status_with_response_body_for_Get_All_Batches(Integer statuscode) {
	//	System.out.println("Response for Get all Request"+response.asPrettyString());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), statuscode);	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		
		//Response Time Validation
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(1000L));
	}
	
	//GetBatchByID_Status200 Request
	
	@Given("User creates Batch GET Request for the LMS API endpoint with valid Batch ID")
	public void user_creates_batch_get_request_for_the_lms_api_endpoint_with_valid_batch_id() {
		LoggerLoad.info("********* Getting Batch by ID *********");
		request =  given();
	//	request.filters(requestLoggingFilter,responseLoggingFilter);
	}

	@When("User sends HTTPS Request for Get Batch By ID")
	public void user_sends_https_request_for_get_batch_by_id() throws IOException {
		Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0); 
	
		System.out.println("By Id URL:"+baseURL+config.getBatch_ById_Url()+iddata.get("BatchId1"));
		response = request.when().get(baseURL+config.getBatch_ById_Url()+iddata.get("BatchId1"));
	}

	@Then("User receives {int} OK Status with response body for Get Batch By ID")
	public void user_receives_ok_status_with_response_body_for_Get_Batch_By_ID(Integer statuscode) {
	//	System.out.println("Response for Get all Request"+response.asPrettyString());
		response.then().log().all(); 
		Assert.assertEquals(response.getStatusCode(), statuscode);	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		//Response Time Validation
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(1000L));
		
		//Schema Validation
		response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/Batch_GetByID.json")).extract().response();
	}
	
	//@GetBatchByID_Status404
	
	@Given("User creates Batch GET Request for the LMS API endpoint with invalid Batch ID")
	public void user_creates_batch_get_request_for_the_lms_api_endpoint_with_invalid_batch_id() {
		LoggerLoad.info("********* Get Batch by Invalid Id *********");
		request =  given();
	}

	@When("User sends HTTPS Request for Get Batch By InvalidID")
	public void user_sends_https_request_for_get_batch_by_invalid_id() {
		
		System.out.println("Invalid URL:"+baseURL+config.getBatch_ById_Url()+random_num);
		response = request.when().get(baseURL+config.getBatch_ById_Url()+random_num);
	}

	@Then("User receives {int} Not Found Status with message and boolean success details")
	public void user_receives_not_found_status_with_message_and_boolean_success_details(Integer statuscode) {
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), statuscode);	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		Assert.assertEquals(response.jsonPath().getString("success"),"false");
		//Response Time Validation
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(500L));		
	}


	//GetBatchByName_Status200 Request
	
	@Given("User creates Batch GET Request for the LMS API endpoint with valid Batch name")
	public void user_creates_batch_get_request_for_the_lms_api_endpoint_with_valid_batch_name() {
		LoggerLoad.info("********* Getting Batch By Name*********");
		request =  given();
	}

	@When("User sends HTTPS Request for Get Batch By Name")
	public void user_sends_https_request_for_get_batch_by_name() throws IOException {
		
		testdata = reader.readexcelsheet(excelpath,"Batch",0);
		
		System.out.println("By Batch Name URL:"+baseURL+config.getBatch_ByName_Url()+testdata.get("batchName"));
		response = request.when().get(baseURL+config.getBatch_ByName_Url()+testdata.get("batchName"));
	}

	@Then("User receives {int} OK Status with response body for Get Batch By Name")
	public void user_receives_ok_status_with_response_body_for_get_batch_by_name(Integer statuscode) {
	//	System.out.println("Response for Get all Request"+response.asPrettyString());
		response.then().log().all(); 
		Assert.assertEquals(response.getStatusCode(), statuscode);	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		//Response Time Validation
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(250L));
		
		//Schema Validation
		response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/Batch_GetByName.json")).extract().response();
	}
	
	//GetBatchByName_Status404 Request
	
	@Given("User creates Batch GET Request for the LMS API endpoint with Invalid Batch name")
	public void user_creates_batch_get_request_for_the_lms_api_endpoint_with_invalid_batch_name() {
		LoggerLoad.info("********* Getting Batch by Invalid Batch*********");
		request =  given();
	}

	@When("User sends HTTPS Request for Get Batch By InvalidName")
	public void user_sends_https_request_for_get_batch_by_invalid_name() {
		System.out.println("Invalid URL:"+baseURL+config.getBatch_ByName_Url()+random_string);
		response = request.when().get(baseURL+config.getBatch_ByName_Url()+random_string);
	}
	
//	@GetBatchByProgramID_Status200 Request
	
	@Given("User creates Batch GET Request for the LMS API endpoint with valid Program Id")
	public void user_creates_batch_get_request_for_the_lms_api_endpoint_with_valid_program_id() {
		LoggerLoad.info("********* Getting Batch by program ID *********");
		request =  given();
	}

	@When("User sends HTTPS Request for Get Batch By Program ID")
	public void user_sends_https_request_for_get_batch_by_program_id() throws IOException {
	//	Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0); 
		
		System.out.println("By Program Id URL:"+baseURL+config.getBatch_ByProgramId_Url()+iddata.get("ProgramID1"));
		response = request.when().get(baseURL+config.getBatch_ByProgramId_Url()+iddata.get("ProgramID1"));
	}

	@Then("User receives {int} OK Status with response body for Get Batch By Program ID")
	public void user_receives_ok_status_with_response_body_for_get_batch_by_program_id(Integer statuscode) {
		
		response.then().log().all(); 
		Assert.assertEquals(response.getStatusCode(), statuscode);	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		//Response Time Validation
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(250L));
		
	}

	//@GetBatchByInvalidProgramID_Status404
	
	@Given("User creates Batch GET Request for the LMS API endpoint with invalid Program Id")
	public void user_creates_batch_get_request_for_the_lms_api_endpoint_with_invalid_program_id() {
		LoggerLoad.info("********* Getting Batch by Invalid program ID*********");
		request =  given();
	}

	@When("User sends HTTPS Request for Get Batch By Invalid Program ID")
	public void user_sends_https_request_for_get_batch_by_invalid_program_id() {
		System.out.println("Invalid URL:"+baseURL+config.getBatch_ByProgramId_Url()+random_num);
		response = request.when().get(baseURL+config.getBatch_ByProgramId_Url()+random_num);
	}
	
	//PutBatch_Status200 Request
	
	@Given("User creates Batch PUT Request for the LMS API endpoint and Valid batch Id")
	public void user_creates_batch_put_request_for_the_lms_api_endpoint_and_valid_batch_id() {
		LoggerLoad.info("********* Update Batch *********");
		request =  given().contentType(ContentType.JSON);
	}

	@SuppressWarnings("unchecked")
	@When("User sends HTTPS Request for update batch with all fields from {string} and {int}")
	public void user_sends_https_request_for_update_batch_with_all_fields_from_and(String sheetName, Integer RowNum) throws IOException {
		testdata = reader.readexcelsheet(excelpath,sheetName,RowNum);
		
		batchDescription = testdata.get("batchDescription");
		batchName = testdata.get("batchName");
		batchNoOfClasses = testdata.get("batchNoOfClasses");
		batchStatus = testdata.get("batchStatus");
		
		requestbody.put("batchDescription",testdata.get("batchDescription"));
		requestbody.put("batchName",testdata.get("batchName"));
		requestbody.put("batchNoOfClasses",testdata.get("batchNoOfClasses"));
		requestbody.put("batchStatus",testdata.get("batchStatus"));
		
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		requestbody.put("programId",iddata.get("ProgramID1"));
	
		response = request.when().body(requestbody.toJSONString()).put(baseURL+config.getBatch_Put_Url()+iddata.get("BatchId1"));
	}

	@Then("User receives {int} OK Status with updated value in response body for update Batch")
	public void user_receives_ok_status_with_updated_value_in_response_body_for_update_batch(Integer statuscode) {
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), statuscode);
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.jsonPath().getString("batchDescription"),batchDescription );
		Assert.assertEquals(response.jsonPath().getString("batchName"),batchName );
		Assert.assertEquals(response.jsonPath().getString("batchNoOfClasses"),batchNoOfClasses );
		Assert.assertEquals(response.jsonPath().getString("batchStatus"),batchStatus );
		//Response Time Validation
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(500L));	
	}
	
	// @PutBatch_Status404
	
	@Given("User creates Batch PUT Request for the LMS API endpoint and invalid batch Id")
	public void user_creates_batch_put_request_for_the_lms_api_endpoint_and_invalid_batch_id() {
		LoggerLoad.info("********* Updata Batch by Bad Request *********");
		request =  given().contentType(ContentType.JSON);
	}

	@SuppressWarnings("unchecked")
	@When("User sends HTTPS Request for update batch with invalid batch Id & all fields from {string} and {int}")
	public void user_sends_https_request_for_update_batch_with_invalid_batch_id_all_fields_from_and(String sheetName, Integer RowNum) throws IOException {
		
		testdata = reader.readexcelsheet(excelpath,sheetName,RowNum);
		
		requestbody.put("batchDescription",testdata.get("batchDescription"));
		requestbody.put("batchName",testdata.get("batchName"));
		requestbody.put("batchNoOfClasses",testdata.get("batchNoOfClasses"));
		requestbody.put("batchStatus",testdata.get("batchStatus"));
		
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		requestbody.put("batchId",iddata.get("BatchId1"));
		requestbody.put("programId",iddata.get("ProgramID1"));
		requestbody.put("programName",iddata.get("ProgramName1"));
		
		System.out.println("Put Invalid URL:"+baseURL+config.getBatch_Put_Url()+random_num);
		response = request.when().body(requestbody.toJSONString()).put(baseURL+config.getBatch_Put_Url()+random_num);
	}
	
	//@PutBatch_Status400 
	
	@SuppressWarnings("unchecked")
	@When("User sends HTTPS Request for update batch with missing fields from {string} and {int}")
	public void user_sends_https_request_for_update_batch_with_missing_fields_from_and(String sheetName, Integer RowNum) throws IOException {
		LoggerLoad.info("********* Updata Batch with missing fields *********");
		testdata = reader.readexcelsheet(excelpath,sheetName,RowNum);
		
		batchDescription = testdata.get("batchDescription");
		batchName = testdata.get("batchName");
		batchNoOfClasses = testdata.get("batchNoOfClasses");
		batchStatus = testdata.get("batchStatus");
		
		requestbody.put("batchDescription",testdata.get("batchDescription"));
		requestbody.put("batchNoOfClasses",testdata.get("batchNoOfClasses"));
		
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0); 
		response = request.when().body(requestbody.toJSONString()).put(baseURL+config.getBatch_Put_Url()+iddata.get("BatchId1"));
	}
	
	//Delete Request
	
	@Given("User creates Batch DELETE Request for the LMS API endpoint and valid programName")
	public void user_creates_batch_delete_request_for_the_lms_api_endpoint_and_valid_program_name() {
		LoggerLoad.info("********* Delete Batch by Batch ID *********");
		request =  given();
	}
	@When("User sends HTTPS Request for Batch DELETE with valid programName")
	public void user_sends_https_request_for_batch_delete_with_valid_program_name() throws IOException {
	//	Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0); 
	//	System.out.println("iddata:"+iddata.get("BatchId1"));
		
		System.out.println("Delete URL:"+baseURL+config.getBatch_Deletebyid_Url()+iddata.get("BatchId1"));
		response = request.when().delete(baseURL+config.getBatch_Deletebyid_Url()+iddata.get("BatchId1"));
		response = request.when().delete(baseURL+config.getBatch_Deletebyid_Url()+iddata.get("BatchId2"));
	//	System.out.println("Response = " + response.asPrettyString());
	}
	@Then("User receives {int} Ok status with message")
	public void user_receives_ok_status_with_message(Integer statuscode) {
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), statuscode);
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
	//	Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		//Response Time Validation
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(500L));
		
	}
	
	// @DeleteBatch_Status404
	@Given("User creates Batch DELETE Request for the LMS API endpoint and invalid programName")
	public void user_creates_batch_delete_request_for_the_lms_api_endpoint_and_invalid_program_name() {
		LoggerLoad.info("********* Delete Batch by invalid Batch ID *********");
		request =  given();
	}

	@When("User sends HTTPS Request for Batch DELETE with invalid programName")
	public void user_sends_https_request_for_batch_delete_with_invalid_program_name() {
		System.out.println("Delete URL:"+baseURL+config.getBatch_Deletebyid_Url()+random_num);
		response = request.when().delete(baseURL+config.getBatch_Deletebyid_Url()+random_num);
	}
}

