package stepdefinitions;

import io.cucumber.java.en.Given;



import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;
import utilities.Excelreader;
import utilities.Excelwriter;
import utilities.LoggerLoad;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.io.PrintStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import org.json.simple.JSONObject;
import org.testng.Assert;
import io.restassured.path.json.JsonPath;
import java.util.Random;

import org.hamcrest.Matchers;
import org.json.simple.JSONArray;


public class Assignment {
	ConfigReader config = new ConfigReader();		
	String assignmentpostendpoint=config.postAssignmet_post_url();
	String excelpath =".\\src/test/resources/data/Assignment.xlsx";
	 String writeexcelpath=".\\src/test/resources/data/IDExcel.xlsx";;
    String baseURL=config.getBaseUrl();
    public String batchId = "";
    public String programmeName="";
    public String graderId = "AdminUserId";
    public String AdminUserId = "";
    public String StaffUserId = "";
    public static RequestSpecification request;
	
	public int rowval;
	public static int assignmentId;
	public static String AssignmentId1;
	public static String AssignmentId2;
	Response response;
	LocalDateTime currentDate = LocalDateTime.now();
	LocalDateTime currentDateTime = LocalDateTime.now().withNano(0);
	String assignmentDescription,assignmentName,comments,pathAttachment1,pathAttachment2,pathAttachment3,pathAttachment4,pathAttachment5;
	JSONObject requestquery = new JSONObject();
	LinkedHashMap<String, String> testdata;
	LinkedHashMap<String, String> iddata;
	
	JSONArray jsonArray = new JSONArray();
	
	
    Excelreader reader = new Excelreader();
    String msgvalidation;
    String getallAssinmentUrl = config.getAllAssinmentUrl();
	String getAssignmentforBatchurl =config.getAssignmentforBatch_url();
	public String uri;
	public String givenassignmentid;
    public String deleteAssignment_delete_url;
    public String givenassignmentname;
    public int statuscode;
	
	

	@SuppressWarnings("unchecked")
	
	//POST ASSIGNMENT_200
	
	@Given("User creates assignment Post request from {string} and {int}")
	public void user_creates_assignment_post_request_from_and(String string, Integer int1) throws Exception {
		LoggerLoad.info("********* Creating Assignment *********");
				
		testdata = reader.readexcelsheet(excelpath,string,int1);
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		batchId = iddata.get("BatchId1");
		programmeName=iddata.get("ProgramName1");
		AdminUserId=iddata.get("AdminUserId");
		StaffUserId=iddata.get("StaffUserId");
		rowval=int1;
		String assignname;
				request=given().
						header("Content-Type","application/Json").
						contentType(ContentType.JSON).
						accept(ContentType.JSON);
				
				
				//System.out.println("testdata:"  +testdata);
				
				Random random = new Random();
				int serialno=random.nextInt(1000);
				
				if (int1==0 ) assignname="-Assignment1-";
				else assignname="-Assignment2-";
				assignmentDescription = testdata.get("assignmentDescription");
				assignmentName = testdata.get("assignmentName")+programmeName+assignname+serialno;
				comments = testdata.get("comments");
				pathAttachment1 = testdata.get("pathAttachment1");
				pathAttachment2 = testdata.get("pathAttachment2");
				pathAttachment3 = testdata.get("pathAttachment3");
				pathAttachment4 = testdata.get("pathAttachment4");
				pathAttachment5 = testdata.get("pathAttachment5");
				
				requestquery.put("assignmentDescription",testdata.get("assignmentDescription"));
		        //requestquery.put("assignmentName",testdata.get("assignmentName")+"-"+serialno);
				requestquery.put("assignmentName",assignmentName);
				requestquery.put("comments",testdata.get("comments"));

					
				currentDateTime = currentDateTime.plusDays(30);
				Instant instant = currentDateTime.toInstant(ZoneOffset.UTC);
				String formattedduedate = DateTimeFormatter.ISO_INSTANT.format(instant);		
				
				
				requestquery.put("dueDate",formattedduedate);        
				requestquery.put("pathAttachment1",testdata.get("pathAttachment1"));
				requestquery.put("pathAttachment2",testdata.get("pathAttachment2"));
				requestquery.put("pathAttachment3",testdata.get("pathAttachment3"));
				requestquery.put("pathAttachment4",testdata.get("pathAttachment4"));
				requestquery.put("pathAttachment5",testdata.get("pathAttachment5"));
				requestquery.put("batchId",batchId);
				
				requestquery.put("createdBy",iddata.get("AdminUserId"));
				requestquery.put("graderId",iddata.get("AdminUserId"));
			
				
				System.out.println(requestquery.toJSONString());

	}

	@When("User sends HTTPS request with valid request body and valid endpoint")
	public void user_sends_https_request_with_valid_request_body_and_valid_endpoint() {
	
		
		System.out.println(requestquery.toJSONString());
		response = request.when().body(requestquery.toJSONString()).
				post(baseURL+config.postAssignmet_post_url()).
				then().
					statusCode(201).log().all().extract().response();
	}

	@Then("User will receive {int} created with response body")
	public void user_will_receive_created_with_response_body(Integer int1) throws IOException {
		response.then().log().all(); 
		//assignmentId = response.jsonPath().getInt("assignmentId");
		
		JsonPath js = new JsonPath(response.asString());
		Excelwriter writer = new Excelwriter();
		if (rowval==0) {
			AssignmentId1 = js.getString("assignmentId");
			writer.WriteExcel(writeexcelpath, "Sheet1", AssignmentId1, 9);
		}
		else if (rowval==1) {
			AssignmentId2= js.getString("assignmentId");
			writer.WriteExcel(writeexcelpath, "Sheet1", AssignmentId2, 10);
		}
		System.out.println(response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), int1);
		//Response Time Validation
				System.out.println("Response Time:" +response.getTime());
				ValidatableResponse restime = response.then();
				restime.time(Matchers.lessThan(5000L));		
			
		  
	}  
		 
	//post negetive400	
	@SuppressWarnings("unchecked")
	@Given("user creates assignment post request for mandatory fields from  {string} and {int}")
	public void user_creates_assignment_post_request_for_mandatory_fields_from_and(String string, Integer int1) throws IOException {
	LoggerLoad.info("********* Creating Assignment with existing values and missing fields *********");
		
		request=given();
		testdata = reader.readexcelsheet(excelpath,string,int1);
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		batchId = iddata.get("BatchId1");
		programmeName=iddata.get("ProgramName1");
		AdminUserId=iddata.get("AdminUserId");
		StaffUserId=iddata.get("StaffUserId");
		rowval=int1;
		
				
		response = request.when().get(baseURL+config.getAssignment_getbyid_url()+iddata.get("AssignmentId1"))
				.then().statusCode(200).log().all().extract().response();
			

		JsonPath js = new JsonPath(response.getBody().asString());			
				
				
			
		Random random = new Random();
		int serialno=random.nextInt(1000);
		assignmentName = js.getString("assignmentName");
		String assignname;
		if (int1==1 ) {
			assignname="-Assignment2-";
			assignmentName = testdata.get("assignmentName")+programmeName+assignname+serialno;
		}
		else if (int1==2) {
			assignname="-Assignment1-";
			assignmentName = testdata.get("assignmentName")+programmeName+assignname+serialno;
		}
		
						
				
				assignmentDescription = testdata.get("assignmentDescription");
				
				comments = testdata.get("comments");
				pathAttachment1 = testdata.get("pathAttachment1");
				pathAttachment2 = testdata.get("pathAttachment2");
				pathAttachment3 = testdata.get("pathAttachment3");
				pathAttachment4 = testdata.get("pathAttachment4");
				pathAttachment5 = testdata.get("pathAttachment5");
				
				requestquery.put("assignmentDescription",testdata.get("assignmentDescription"));
				
				requestquery.put("comments",testdata.get("comments"));

				requestquery.put("dueDate",js.getString("dueDate"));        
				requestquery.put("pathAttachment1",testdata.get("pathAttachment1"));
				requestquery.put("pathAttachment2",testdata.get("pathAttachment2"));
				requestquery.put("pathAttachment3",testdata.get("pathAttachment3"));
				requestquery.put("pathAttachment4",testdata.get("pathAttachment4"));
				requestquery.put("pathAttachment5",testdata.get("pathAttachment5"));
				requestquery.put("batchId",batchId);
				requestquery.put("assignmentId", iddata.get("AssignmentId1"));
				
				if (rowval==1)
				{
					requestquery.put("createdBy","");
					requestquery.put("graderId",iddata.get("AdminUserId"));
					requestquery.put("assignmentName",assignmentName);
				}
				else if(rowval==2) {
					requestquery.put("createdBy",iddata.get("AdminUserId"));
					requestquery.put("graderId","");
					requestquery.put("assignmentName",assignmentName);
				}
				else {
					requestquery.put("createdBy",iddata.get("AdminUserId"));
					requestquery.put("graderId",iddata.get("AdminUserId"));
					requestquery.put("assignmentName",assignmentName);
				}
				request=given().
						header("Content-Type","application/Json").
						contentType(ContentType.JSON).
						accept(ContentType.JSON);
		//System.out.println(requestquery.toJSONString());
	

		
	}
	

	@When("user send post request with existing assignmentName requestbody and valid endpoint")
	public void user_send_post_request_with_existing_assignment_name_requestbody_and_valid_endpoint() {
		response = request.when().body(requestquery.toJSONString()).
				post(baseURL+config.postAssignmet_post_url()).
				then().
				statusCode(400).extract().response();
	    
	}

	@Then("user will receive {int} with response body {int}")
	public void user_will_receive_with_response_body(Integer int1, Integer int2) {
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), int1);	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		
		Assert.assertEquals(response.jsonPath().getString("success"),"false");
		
		//Response Time Validation
				System.out.println("Response Time:" +response.getTime());
				ValidatableResponse restime = response.then();
				restime.time(Matchers.lessThan(5000L));		
		
	}
		
	
   //ASSINMENT GET REQUEST
	@Given("User is provided with the Base Url and endpoint")
	public void user_is_provided_with_the_base_url_and_endpoint() {
		LoggerLoad.info("********* Get all Assignment *********");
		
		this.uri = baseURL + getallAssinmentUrl;
	    request = RestAssured.given().header("Content-Type", "application/json"); 
	}

	@When("User send the get request")
	public void user_send_the_get_request() {
		 response = request.when().get(this.uri).then().log().all().extract().response();
	    
	}

	@Then("User receives {string} OK Status and response body")
	public void user_receives_ok_status_and_response_body(String string) {
		int getstatuscode = response.getStatusCode();
		int statuscode = 200;
        System.out.println(getstatuscode);
        Assert.assertEquals(response.getStatusCode(), statuscode);	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		
		//Response Time Validation
				System.out.println("Response Time:" +response.getTime());
				ValidatableResponse restime = response.then();
				restime.time(Matchers.lessThan(1000L));
			}
	   
	
   //GET  REQUEST BY ID_200
	@Given("User is provided with the Base Url,endpoint and assignment Id")
	public void user_is_provided_with_the_base_url_endpoint_and_assignment_id() throws IOException {
		LoggerLoad.info("********* Get Assignment by ID *********");
		
		request =  given();
		Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		 givenassignmentid = iddata.get("AssignmentId2");
	}
		

	@When("User send the get request for get assignment by id")
	public void user_send_the_get_request_for_get_assignment_by_id() {
		response = request.when().get(baseURL+config.getAssignment_getbyid_url()+givenassignmentid)
				.then().statusCode(200).log().all().extract().response();
	}
	
	@SuppressWarnings("unused")
	@Then("User receives {string} OK Status with given response body")
	public void user_receives_ok_status_with_given_response_body(String string) {
		JsonPath js = new JsonPath(response.getBody().asString());
		Assert.assertEquals(response.getStatusCode(), 200);
		//Response Time Validation
				System.out.println("Response Time:" +response.getTime());
				ValidatableResponse restime = response.then();
				restime.time(Matchers.lessThan(5000L));		
		
	}
	    
     //GET REQUEST BYID-404
	
	@Given("User creates GET request for the LMS API endpoint with invalid assignment ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_assignment_id() throws IOException {
		LoggerLoad.info("********* Get Assignment with invalid assignmentid*********");
		
		request =  given();
		Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		 givenassignmentid = iddata.get("ProgramID2");
	}

	@When("User sends HTTPS request")
	public void user_sends_https_request() {
		response = request.when().get(baseURL+config.getAssignment_getbyid_url()+givenassignmentid);
	}
	

	@Then("User receives {int} NOT FOUND status with message and boolean success details")
	public void user_receives_not_found_status_with_message_and_boolean_success_details(Integer int1) {
		int getstatuscode = response.getStatusCode();
		int statuscode = 404;
        System.out.println(getstatuscode);
        	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(5000L));
		
	}
 //GET REQUEST for BATCHID_200
	
	@Given("User is provided with the Base Url,endpoint and batch Id")
	public void user_is_provided_with_the_base_url_endpoint_and_batch_id() throws IOException {
		LoggerLoad.info("********* Get Assignment for batch *********");
		
		request =  given();
		Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		 givenassignmentid = iddata.get("BatchId1");
	}

	@When("User send the get request for get assignment for batchId")
	public void user_send_the_get_request_for_get_assignment_for_batch_id() {
		response = request.when().get(baseURL+config.getAssignmentforBatch_url()+givenassignmentid)
	
				.then().log().all().extract().response();
	}
    
	//GET REQUEST for BATCHID_404
	
	@Given("User creates GET request for the LMS API endpoint with invalid batch ID")
	public void user_creates_get_request_for_the_lms_api_endpoint_with_invalid_batch_id() throws IOException {
		LoggerLoad.info("********* Get Assignment with invalid batchid *********");
		
		request =  given();
		Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		 givenassignmentid = iddata.get("ProgramID2");
	}
		
		
		
	
	

	//PUT ASSIGNMENTREQUEST WITH VALID ID_200
	@SuppressWarnings("unchecked")
	@Given("user update assignment for given assignmentId with mandatory fields from  {string} and {int}")
	public void user_update_assignment_for_given_assignment_id_with_mandatory_fields_from_and(String string, Integer int1) throws IOException {
		LoggerLoad.info("********* Update Assignment with valid assignmentid *********");
		
		request=given();
		testdata = reader.readexcelsheet(excelpath,string,int1);
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		batchId = iddata.get("BatchId1");
		programmeName=iddata.get("ProgramName1");
		AdminUserId=iddata.get("AdminUserId");
		StaffUserId=iddata.get("StaffUserId");
		rowval=int1;
		givenassignmentid= iddata.get("AssignmentId1");
		response = request.when().get(baseURL+config.getAssignment_getbyid_url()+givenassignmentid)
				.then().statusCode(200).log().all().extract().response();
			

		JsonPath js = new JsonPath(response.getBody().asString());
		
		
		assignmentDescription = testdata.get("assignmentDescription");
		assignmentName = js.getString("assignmentName");
		comments = js.getString("comments");
		pathAttachment1 = testdata.get("pathAttachment1");
		pathAttachment2 =js.getString("pathAttachment2");
		pathAttachment3 = js.getString("pathAttachment3");
		pathAttachment4 = js.getString("pathAttachment4");
		pathAttachment5 = js.getString("pathAttachment5");
		
		
        
		requestquery.put("assignmentName",assignmentName);
		requestquery.put("comments",comments);
		requestquery.put("assignmentId", givenassignmentid);
		requestquery.put("dueDate", js.getString("dueDate"));        
		requestquery.put("pathAttachment2",js.getString("pathAttachment2"));
		requestquery.put("pathAttachment3",js.getString("pathAttachment3"));
		requestquery.put("pathAttachment4",js.getString("pathAttachment4"));
		requestquery.put("pathAttachment5",js.getString("pathAttachment5"));
		requestquery.put("batchId",js.getString("batchId"));
		requestquery.put("createdBy",js.getString("createdBy"));
		requestquery.put("graderId",js.getString("graderId"));
		
		requestquery.put("assignmentDescription",assignmentDescription); //updating assignmentdescription
		requestquery.put("pathAttachment1",testdata.get("pathAttachment1")); //updating pathAttachment1
		
		request=given().
				header("Content-Type","application/Json").
				contentType(ContentType.JSON).
				accept(ContentType.JSON);
		
		System.out.println(requestquery.toJSONString());
	

	}
	   
	

	@When("user send put request to update given assignmentId")
	public void user_send_put_request_to_update_given_assignment_id() {
		response = request.when().body(requestquery.toJSONString()).
				put(baseURL+config.putAssignment_put_url()+givenassignmentid).
				then().
				statusCode(200).log().all().extract().response();
	}


	@Then("user will receive {int} status with valid response body from {int}")
	public void user_will_receive_status_with_valid_response_body_from(Integer int1, Integer int2) {
		if (response.getStatusCode()==int1) {
			System.out.println("User Put updation Success");
			Assert.assertEquals(response.getStatusCode(), int1);
			
			//Response Time Validation
					System.out.println("Response Time:" +response.getTime());
					ValidatableResponse restime = response.then();
					restime.time(Matchers.lessThan(5000L));		
		}
	}
	
	//PUT ASSIGNMENTREQUEST WITH INVALIDID_404
	
	@SuppressWarnings("unchecked")
	@Given("User creates  Put Request with invalid assignmentId with mandatory request body")
	public void user_creates_put_request_with_invalid_assignment_id_with_mandatory_request_body() throws IOException {
		LoggerLoad.info("********* update Assignment with invalid assignmentid *********");
		
		request=given();
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		batchId = iddata.get("BatchId1");
		programmeName=iddata.get("ProgramName1");
		AdminUserId=iddata.get("AdminUserId");
		StaffUserId=iddata.get("StaffUserId");
	
		givenassignmentid= iddata.get("ProgramID2");
		response = request.when().get(baseURL+config.getAssignment_getbyid_url()+iddata.get("AssignmentId1"))
				.then().statusCode(200).log().all().extract().response();
			

		JsonPath js = new JsonPath(response.getBody().asString());
		
		
		assignmentDescription = js.getString("assignmentDescription");
		assignmentName = js.getString("assignmentName");
		comments = js.getString("comments");
		pathAttachment1 = js.getString("pathAttachment1");
		pathAttachment2 =js.getString("pathAttachment2");
		pathAttachment3 = js.getString("pathAttachment3");
		pathAttachment4 = js.getString("pathAttachment4");
		pathAttachment5 = js.getString("pathAttachment5");
		
	
		requestquery.put("assignmentName",assignmentName);
		requestquery.put("comments",comments);
		requestquery.put("assignmentId",js.getString("assignmentId"));
		requestquery.put("dueDate", js.getString("dueDate"));        
		requestquery.put("pathAttachment2",js.getString("pathAttachment2"));
		requestquery.put("pathAttachment3",js.getString("pathAttachment3"));
		requestquery.put("pathAttachment4",js.getString("pathAttachment4"));
		requestquery.put("pathAttachment5",js.getString("pathAttachment5"));
		requestquery.put("batchId",js.getString("batchId"));
		requestquery.put("createdBy",js.getString("createdBy"));
		requestquery.put("graderId",js.getString("graderId"));
		
		requestquery.put("assignmentDescription",assignmentDescription); 
		requestquery.put("pathAttachment1",js.getString("pathAttachment1")); 
		
		request=given().
				header("Content-Type","application/Json").
				contentType(ContentType.JSON).
				accept(ContentType.JSON);
		
				System.out.println(requestquery.toJSONString());

	}
	    

	@When("user send Put request with invalid assignmentId")
	public void user_send_put_request_with_invalid_assignment_id() {
		response = request.when().body(requestquery.toJSONString()).
				put(baseURL+config. putAssignment_put_url()+givenassignmentid);
	}
	

	@Then("user will receive {int} status")
	public void user_will_receive_status(Integer int1) {
		int getstatuscode = response.getStatusCode();
		int statuscode =int1;
        System.out.println(getstatuscode);
        Assert.assertEquals(response.getStatusCode(), statuscode);	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		
		//Response Time Validation
				System.out.println("Response Time:" +response.getTime());
				ValidatableResponse restime = response.then();
				restime.time(Matchers.lessThan(5000L));		
		
	}
	
	//PUT ASSIGNMENTREQUEST WITH MISSINGFIELD_400
	@SuppressWarnings("unchecked")
	@Given("User updates assignment for given assignmentId with missing mandatory fields from  {string} and {int}")
	public void user_updates_assignment_for_given_assignment_id_with_missing_mandatory_fields_from_and(String string, Integer int1) throws IOException {
		LoggerLoad.info("********* Update Assignment with missing mandatory fields *********");
		
		request=given();
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		batchId = iddata.get("BatchId1");
		programmeName=iddata.get("ProgramName1");
		AdminUserId=iddata.get("AdminUserId");
		StaffUserId=iddata.get("StaffUserId");
	
		givenassignmentid= iddata.get("AssignmentId1");
		response = request.when().get(baseURL+config.getAssignment_getbyid_url()+iddata.get("AssignmentId1"))
				.then().statusCode(200).log().all().extract().response();
			

		JsonPath js = new JsonPath(response.getBody().asString());
		
		
		assignmentDescription = js.getString("assignmentDescription");
		assignmentName = js.getString("assignmentName");
		comments = js.getString("comments");
		pathAttachment1 = js.getString("pathAttachment1");
		pathAttachment2 =js.getString("pathAttachment2");
		pathAttachment3 = js.getString("pathAttachment3");
		pathAttachment4 = js.getString("pathAttachment4");
		pathAttachment5 = js.getString("pathAttachment5");
		
	
		requestquery.put("assignmentName",assignmentName);
		requestquery.put("comments",comments);
		requestquery.put("assignmentId",js.getString("assignmentId"));
		requestquery.put("dueDate", js.getString("dueDate"));        
		requestquery.put("pathAttachment2",js.getString("pathAttachment2"));
		requestquery.put("pathAttachment3",js.getString("pathAttachment3"));
		requestquery.put("pathAttachment4",js.getString("pathAttachment4"));
		requestquery.put("pathAttachment5",js.getString("pathAttachment5"));
		requestquery.put("batchId",js.getString("batchId"));
		requestquery.put("createdBy",js.getString("createdBy"));
		requestquery.put("graderId",js.getString("graderId"));
		
		requestquery.put("assignmentDescription",assignmentDescription); 
		requestquery.put("pathAttachment1",js.getString("pathAttachment1")); 
		
		request=given().
				header("Content-Type","application/Json").
				contentType(ContentType.JSON).
				accept(ContentType.JSON);
		
				System.out.println(requestquery.toJSONString());
	}
	

	@When("user send put request to validate mandatory fields for given assignmentId")
	public void user_send_put_request_to_validate_mandatory_fields_for_given_assignment_id() {
		response = request.when().
				body(requestquery.toJSONString()).
				put(baseURL+config. putAssignment_put_url()+givenassignmentid).
			then()
				.log().all().extract().response();
	}

	@Then("user will receive {int} status from {int}")
	public void user_will_receive_status_from(Integer int1, Integer int2) {
		if (response.getStatusCode()==int1) {
			//System.out.println("Mandatory Field Validation");
			//System.out.println("actual "+response.jsonPath().get("message"));
			//Assert.assertEquals(response.jsonPath().get("message"), msgvalidation);
		}
		if (response.getStatusCode()==int1) {
			if (int2==0) {
				LoggerLoad.info("With existing assignmentDescription");
			}
			else if (int2==1) {
				LoggerLoad.info(("Empty assignmentDescription"));
			}
			else if (int2==2) {
				LoggerLoad.info("Empty assignmentName");
			}
			else if (int2==3) {
				LoggerLoad.info("Empty comments");
			}
			else if (int2==4) {
				LoggerLoad.info("Empty pathAttachment1");
			}
			else if (int2==5) {
				LoggerLoad.info("Empty pathAttachment2");
			}
			else if (int2==7) {
				LoggerLoad.info("Empty pathAttachment3");
			}
			else if (int2==8) {
				LoggerLoad.info("Empty pathAttachment4");
			}
			else if (int2==9) {
				LoggerLoad.info("Empty pathAttachment5");
			}
		}
		
		{
		//System.out.println("Mandatory Field Validation");
		//Assert.assertEquals(response.jsonPath().get("message"), msgvalidation);
		//Assert.assertEquals(response.getStatusCode(), int1);
		//Response Time Validation
				System.out.println("Response Time:" +response.getTime());
				ValidatableResponse restime = response.then();
				restime.time(Matchers.lessThan(5000L));		
	}
}

//DELETE ASSIGNMENTWITH VALID ID_200
	@Given("User is provided with the Base Url and endpoint and valid assignmentId")
	public void user_is_provided_with_the_base_url_and_endpoint_and_valid_assignment_id() throws IOException {
		LoggerLoad.info("********* Deleting Assignment with valid assignmentid *********");
		
		request =  given();
	Excelreader reader = new Excelreader();	
	iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
	 givenassignmentid = iddata.get("AssignmentId2");
}

	@When("User send the delete request for deleting assignment")
	public void user_send_the_delete_request_for_deleting_assignment() {
		response = request.when().get(baseURL +config.deleteAssignment_delete_url()+givenassignmentid)
				.then().log().all().extract().response();
	}
	

	//DELETE ASSIGNMENT BY INVALIDID_404
	@Given("User creates delete request for the valid LMS API endpoint and invalid assignment id")
	public void user_creates_delete_request_for_the_valid_lms_api_endpoint_and_invalid_assignment_id() throws IOException {
		LoggerLoad.info("********* Deleting Assignment with invalid assignmentid *********");
		
		request =  given();
		Excelreader reader = new Excelreader();	
		iddata = reader.readexcelsheet(writeexcelpath,"Sheet1",0);
		 givenassignmentid = iddata.get("ProgramID2");
	}
	
	
	@When("User sends delete request for deleting assignment with invalid assignmentId")
	public void user_sends_delete_request_for_deleting_assignment_with_invalid_assignment_id() {
		response = request.when().get(baseURL +config.deleteAssignment_delete_url()+givenassignmentid)
				.then().log().all().extract().response();
	}


	@Then("User receives {int} not found status with message and boolean suceess datails")
	public void user_receives_not_found_status_with_message_and_boolean_suceess_datails(Integer int1) {
		int getstatuscode = response.getStatusCode();
		int statuscode = 404;
        System.out.println(getstatuscode);
        Assert.assertEquals(response.getStatusCode(), statuscode);	
		Assert.assertEquals(response.header("Content-Type"),"application/json");
		Assert.assertEquals(response.header("Connection"),"keep-alive");
		Assert.assertEquals(response.header("Transfer-Encoding"),"chunked");
		
		//Response Time Validation
				System.out.println("Response Time:" +response.getTime());
				ValidatableResponse restime = response.then();
				restime.time(Matchers.lessThan(5000L));		
		
	    
	}













	
	
	

}
