package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.Assert;

import utilities.ConfigReader;
import utilities.Excelreader;
import utilities.Excelwriter;

public class Submission_SD {

	ConfigReader config = new ConfigReader();	
    String excelpath = "src\\test\\resources\\data\\submission.xlsx";
    String idexcelpath = "src\\test\\resources\\data\\IdExcel.xlsx";
    String excelSheetname;
	RequestSpecification request;
	Response response;
	int submissionId;
	int assignmentId;
	int userId;
	Excelreader reader = new Excelreader();	
	
	@SuppressWarnings("unchecked")
	@Given("User creates Request with all mandatory fields and additional fields from {string} and {int}")
	public void user_creates_request_with_all_mandatory_fields_and_additional_fields_from_and(
			String sheetname, Integer rownum) throws IOException {
		request =  given().contentType(ContentType.JSON);
		Excelreader reader = new Excelreader();		
		LinkedHashMap<String, String> testdata = reader.readexcelsheet(excelpath, sheetname, rownum);
	
		Excelreader idreader = new Excelreader();		
		LinkedHashMap<String, String> idtestdata = reader.readexcelsheet(idexcelpath, sheetname, rownum);
		
		String curTimeStamp = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new java.util.Date());
	
		JSONObject requestbody = new JSONObject();
		requestbody.put("assignmentId",idtestdata.get("AssignmentId"));
	    requestbody.put("userId",idtestdata.get("StudentUserId"));
		requestbody.put("subDesc",testdata.get("subDesc"));
		requestbody.put("subDateTime",curTimeStamp);
		requestbody.put("subComments",testdata.get("subComments"));
		requestbody.put("subPathAttach1",testdata.get("subPathAttach1"));
		requestbody.put("subPathAttach2",testdata.get("subPathAttach2"));
		requestbody.put("subPathAttach3",testdata.get("subPathAttach3"));
		requestbody.put("subPathAttach4",testdata.get("subPathAttach4"));
		requestbody.put("subPathAttach5",testdata.get("subPathAttach5"));
		requestbody.put("gradedBy",testdata.get("gradedBy"));
		requestbody.put("gradedDateTime",curTimeStamp);
		requestbody.put("grade",testdata.get("grade"));
		excelSheetname = sheetname;
		request.when().body(requestbody.toJSONString());
		
	}
	
	@When("User sends HTTPS Post Request and request Body for the api {string}")
	public void user_sends_https_post_request_and_request_body_for_the_api(String apiProperty) {
		response = request.post(config.getApiUrl(apiProperty));
		response.then().log().all();
	}
	
	
	@Given("User creates Request  with missing manadatory field")
	public void user_creates_request_with_missing_manadatory_field()  throws IOException{
		request =  given().contentType(ContentType.JSON);
		Excelreader reader = new Excelreader();		
		LinkedHashMap<String, String> testdata = reader.readexcelsheet(excelpath, "Sheet1",0);
	
		String curTimeStamp = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new java.util.Date());
	
		JSONObject requestbody = new JSONObject();
		//missing assignmentId
	    requestbody.put("userId",testdata.get("userId"));
		requestbody.put("subDesc",testdata.get("subDesc"));
		requestbody.put("subDateTime",curTimeStamp);
		requestbody.put("subComments",testdata.get("subComments"));
		requestbody.put("subPathAttach1",testdata.get("subPathAttach1"));
		requestbody.put("subPathAttach2",testdata.get("subPathAttach2"));
		requestbody.put("subPathAttach3",testdata.get("subPathAttach3"));
		requestbody.put("subPathAttach4",testdata.get("subPathAttach4"));
		requestbody.put("subPathAttach5",testdata.get("subPathAttach5"));
		requestbody.put("gradedBy",testdata.get("gradedBy"));
		requestbody.put("gradedDateTime",curTimeStamp);
		requestbody.put("grade",testdata.get("grade"));
	
		
		request.when().body(requestbody.toJSONString());
	}
	
	
	
	
	
	@Then("user receives {int} status with response body with submission response")
	public void user_receives_status_with_response_body_with_submission_response(Integer expectedStatus) throws IOException {
	
		Assert.assertEquals(response.statusCode(), expectedStatus );
		
		JsonPath jsonResp = response.jsonPath();
	
	/*
		Map<String, Object> submission =jsonResp.getMap("", String.class, Object.class);
		Assert.assertTrue(submission.containsKey("submissionId"));
		Assert.assertTrue(submission.containsKey("assignmentId"));
		Assert.assertTrue(submission.containsKey("userId"));
		Assert.assertTrue(submission.containsKey("subDesc"));
		Assert.assertTrue(submission.containsKey("subComments"));
		Assert.assertTrue(submission.containsKey("subPathAttach1"));
		Assert.assertTrue(submission.containsKey("subPathAttach2"));
		Assert.assertTrue(submission.containsKey("subPathAttach3"));
		Assert.assertTrue(submission.containsKey("subPathAttach4"));
		Assert.assertTrue(submission.containsKey("subPathAttach5"));
		Assert.assertTrue(submission.containsKey("subDateTime"));
		Assert.assertTrue(submission.containsKey("gradedBy"));
		Assert.assertTrue(submission.containsKey("gradedDateTime"));
		Assert.assertTrue(submission.containsKey("grade"));*/
		
		submissionId = response.jsonPath().getInt("submissionId");
		
		Excelwriter writer = new Excelwriter();	
		writer.WriteExcel(idexcelpath, excelSheetname, "" + submissionId, 10);
			
	}
	
	@Then("user receives {int} status with response body success flag false")
	public void user_receives_status_with_response_body_success_flag_false(Integer expectedStatus) {
	    Assert.assertEquals(response.statusCode(), expectedStatus );
		Assert.assertEquals(response.jsonPath().getBoolean("success"),false );
	}
	
	@Given("User creates Request with no parameters")
	public void user_creates_request_with_no_parameters() {
		request =  given();
	}
	
	@When("User sends HTTPS Get Request and request Body for the api {string}")
	public void user_sends_https_get_request_and_request_body_for_the_api(String apiProperty) {
		response = request.get(config.getApiUrl(apiProperty));
	}
	
	@Then("User receives {int} status with valid all submissions response")
	public void user_receives_status_with_valid_all_submissions_response(Integer expectedStatus) {
		Assert.assertEquals(response.statusCode(),expectedStatus );
		//System.out.println(response.prettyPrint());
		
		JsonPath jsonResp = response.jsonPath();
		List<LinkedHashMap<String, Object>> submissions = jsonResp.getList("");
		for(LinkedHashMap<String, Object> submission: submissions) {
			Assert.assertTrue(submission.containsKey("submissionId"), "submissionId");
			Assert.assertTrue(submission.containsKey("assignmentId"), "assignmentId");
			Assert.assertTrue(submission.containsKey("userId"), "userId");
			Assert.assertTrue(submission.containsKey("subDesc"), "subDesc");
			Assert.assertTrue(submission.containsKey("subComments"), "subComments");
			//Assert.assertTrue(submission.containsKey("subPathAttach1"),"subPathAttach1");
			//Assert.assertTrue(submission.containsKey("subPathAttach2"),"subPathAttach2");
			//Assert.assertTrue(submission.containsKey("subPathAttach3"),"subPathAttach3");
			//Assert.assertTrue(submission.containsKey("subPathAttach4"),"subPathAttach4");
			//Assert.assertTrue(submission.containsKey("subPathAttach5"),"subPathAttach5");
			Assert.assertTrue(submission.containsKey("subDateTime"),"subDateTime");
			Assert.assertTrue(submission.containsKey("gradedBy"),"gradedBy");
			Assert.assertTrue(submission.containsKey("gradedDateTime"),"gradedDateTime");
			Assert.assertTrue(submission.containsKey("grade"),"grade");
		}
	}
	
	@When("User sends HTTPS Get Request and request Body for the api {string} with assignmentId from {string} and {int}")
	public void user_sends_https_get_request_and_request_body_for_the_api_with_assignment_id_from_and(String apiProperty, 
			String sheetname, Integer rownum) throws IOException {
		Excelreader reader = new Excelreader();		
		LinkedHashMap<String, String> testdata = reader.readexcelsheet(excelpath, sheetname, rownum);
		
		String getUri = config.getApiUrl(apiProperty) + testdata.get("assignmentId");
		response = request.get(getUri);
		response.then().log().all();
	}
	
	@Then("User receives {int} status with valid grades response")
	public void user_receives_status_with_valid_grades_response(Integer expectedStatus) {
	
		Assert.assertEquals(response.statusCode(),expectedStatus );
		
		JsonPath jsonResp = response.jsonPath();
		List<LinkedHashMap<String, Object>> gradesObj = jsonResp.getList("");
		
		for(LinkedHashMap<String, Object> gradeObj: gradesObj) {
			Assert.assertTrue(gradeObj.containsKey("submissionId"));
			Assert.assertTrue(gradeObj.containsKey("assignmentId"));
			Assert.assertTrue(gradeObj.containsKey("userId"));
			Assert.assertTrue(gradeObj.containsKey("subDesc"));
			Assert.assertTrue(gradeObj.containsKey("subComments"));
			Assert.assertTrue(gradeObj.containsKey("subPathAttach1"));
			Assert.assertTrue(gradeObj.containsKey("subPathAttach2"));
			Assert.assertTrue(gradeObj.containsKey("subPathAttach3"));
			Assert.assertTrue(gradeObj.containsKey("subPathAttach4"));
			Assert.assertTrue(gradeObj.containsKey("subPathAttach5"));
			Assert.assertTrue(gradeObj.containsKey("subDateTime"));
			Assert.assertTrue(gradeObj.containsKey("gradedBy"));
			Assert.assertTrue(gradeObj.containsKey("gradedDateTime"));
			Assert.assertTrue(gradeObj.containsKey("grade"));
	
		}   
	}
	
	@When("User sends HTTPS Get Request and request Body for the api {string} with Student from {string} and {int}")
	public void user_sends_https_get_request_and_request_body_for_the_api_with_student_from_and(String apiProperty, 
			String sheetname, Integer rownum) throws IOException {
		Excelreader reader = new Excelreader();		
		LinkedHashMap<String, String> testdata = reader.readexcelsheet(excelpath, sheetname, rownum);
		
		String getUri = config.getApiUrl(apiProperty) + testdata.get("userId");
		response = request.get(getUri);
	    
	}
	
	
	@Then("user receives {int} status with message")
	public void user_receives_status_with_message(Integer expectedStatus) {
		Assert.assertEquals(response.statusCode(),expectedStatus );
	    Assert.assertEquals(response.statusCode(), expectedStatus );
		Assert.assertEquals(response.jsonPath().getBoolean("success"),true );
		Assert.assertNotNull(response.jsonPath().getBoolean("message"));
	}
	
	
	
	@When("User sends HTTPS Delete Request and request Body for the api {string} with sub id from {string} and {int}")
	public void user_sends_https_delete_request_and_request_body_for_the_api_with_sub_id_from_and(String apiProperty, 
			String sheetname, Integer rownum) throws IOException {
		Excelreader reader = new Excelreader();		
		LinkedHashMap<String, String> testdata = reader.readexcelsheet(idexcelpath, sheetname, rownum); 
		
		String deleteUri = config.getApiUrl(apiProperty) + "/" + testdata.get("SubmissionId");
		response = request.delete(deleteUri);
	}
	
	@When("User sends HTTPS Delete Request and request Body for the api {string} with invalid sub id from {string} and {int}")
	public void user_sends_https_delete_request_and_request_body_for_the_api_with_invalid_sub_id_from_and(String apiProperty, 
			String sheetname, Integer rownum) throws IOException {
//		Excelreader reader = new Excelreader();		
//		LinkedHashMap<String, String> testdata = reader.readexcelsheet(idexcelpath, sheetname, rownum); 
		
		String deleteUri = config.getApiUrl(apiProperty) + "/" + -1;
		response = request.delete(deleteUri);
	}
	
	//tage9
	@Given("User is provided with the Base Url and endpoint for GET Grades by BatchID")
	public void user_is_provided_with_the_base_url_and_endpoint_for_get_grades_by_batch_id() {
		request =  given();
	}
	
	@When("User send the get request for GET Grades by BatchID")
	public void user_send_the_get_request_for_get_grades_by_batch_id() throws IOException {
		Excelreader reader = new Excelreader();	
		LinkedHashMap<String, String> iddata = reader.readexcelsheet(idexcelpath,"Sheet1",0); 
	
		System.out.println("By Id URL:"+config.getGrades_byBatchID()+iddata.get("BatchId1"));
		response = request.when().get(config.getGrades_byBatchID()+iddata.get("BatchId1"));
		
	}
	
	@Then("User receives {int} status with valid grades response for submission")
	public void User_receives_status_with_valid_grades_response_for_submission(Integer statuscode) {
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), statuscode);
		
		//Response Time Validation
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(500L));	
	}
	
	@When("User send the get request for GET Grades by invalid BatchID")
	public void user_send_the_get_request_for_get_grades_by_invalid_batch_id() throws IOException {
		Excelreader reader = new Excelreader();	
		LinkedHashMap<String, String> iddata = reader.readexcelsheet(idexcelpath,"Sheet1",0); 
	
		System.out.println("By Id URL:"+config.getGrades_byBatchID()+-7);
		response = request.when().get(config.getGrades_byBatchID()+-7);
		
	}
	@Then("User receives {int} status with valid grades response for invalid submission")
	public void User_receives_status_with_valid_grades_response_for_invalid_submission(Integer statuscode) {
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), statuscode);
		
		//Response Time Validation
		System.out.println("Response Time:" +response.getTime());
		ValidatableResponse restime = response.then();
		restime.time(Matchers.lessThan(500L));	
	}
	//tag11
	@Given("User is provided with the Base Url and endpoint for GET Submission by batch ID")
	public void user_is_provided_with_the_base_url_and_endpoint_for_get_submission_by_batch_id() {
		request =  given();
	}
	
	//tag15
	@SuppressWarnings("unchecked")
	@Given("User creates Request for put with all mandatory fields and additional fields from {string} and {int}")
	public void user_creates_request_for_put_with_all_mandatory_fields_and_additional_fields_from_and(String sheetname, Integer rownum) throws IOException {
		request =  given().contentType(ContentType.JSON);
		Excelreader reader = new Excelreader();		
		LinkedHashMap<String, String> testdata = reader.readexcelsheet(excelpath, sheetname, rownum);
	
		String curTimeStamp = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new java.util.Date());
	
		JSONObject requestbody = new JSONObject();
		LinkedHashMap<String, String> iddata = reader.readexcelsheet(idexcelpath,"Sheet1",0);
		
		requestbody.put("assignmentId",iddata.get("AssignmentId"));
	    requestbody.put("userId",iddata.get("StudentUserId"));
	    requestbody.put("submissionId",iddata.get("SubmissionId"));
		requestbody.put("subDesc",testdata.get("subDesc"));
		requestbody.put("subDateTime",curTimeStamp);
		requestbody.put("subComments",testdata.get("subComments"));
		requestbody.put("subPathAttach1",testdata.get("subPathAttach1"));
		requestbody.put("subPathAttach2",testdata.get("subPathAttach2"));
		requestbody.put("subPathAttach3",testdata.get("subPathAttach3"));
		requestbody.put("subPathAttach4",testdata.get("subPathAttach4"));
		requestbody.put("subPathAttach5",testdata.get("subPathAttach5"));
		requestbody.put("gradedBy",testdata.get("gradedBy"));
		requestbody.put("gradedDateTime",curTimeStamp);
		requestbody.put("grade",testdata.get("grade"));
	
		System.out.println("REquest body"+request.when().body(requestbody.toJSONString()));
		request.when().body(requestbody.toJSONString());
	}

	@When("User sends HTTPS Put Request and request Body for the api {string} with valid submission ID from {string} and {int}")
	public void user_sends_https_put_request_and_request_body_for_the_api_with_valid_submission_id_from_and(String apiProperty, String sheetname, Integer rownum) throws IOException {
		LinkedHashMap<String, String> iddata = reader.readexcelsheet(idexcelpath,"Sheet1",0);
		System.out.println("URL"+config.getApiUrl(apiProperty) + iddata.get("SubmissionId"));
		response = request.put(config.getApiUrl(apiProperty) + iddata.get("SubmissionId"));
		response.then().log().all();
	}

	//tag16
	@When("User sends HTTPS Put Request and request Body for the api {string} with invalid submission ID from {string} and {int}")
	public void user_sends_https_put_request_and_request_body_for_the_api_with_invalid_submission_id_from_and(String apiProperty, String sheetname, Integer rownum) throws IOException {
//		LinkedHashMap<String, String> testdata = reader.readexcelsheet(excelpath, sheetname, rownum);
//		response = request.put(config.getApiUrl(apiProperty) + testdata.get("SubmissionId"));
//		LinkedHashMap<String, String> iddata = reader.readexcelsheet(idexcelpath,"Sheet1",0);
		response = request.put(config.getApiUrl(apiProperty) + -1);
		response.then().log().all();
	}
	
	//tag17
	@SuppressWarnings("unchecked")
	@Given("User update Request with missing manadatory field")
	public void user_update_request_with_missing_manadatory_field() throws IOException {
		request =  given().contentType(ContentType.JSON);
		Excelreader reader = new Excelreader();		
		LinkedHashMap<String, String> testdata = reader.readexcelsheet(excelpath, "Sheet1",0);
	
		String curTimeStamp = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new java.util.Date());
	
		JSONObject requestbody = new JSONObject();
		//missing assignmentId
	    requestbody.put("userId",testdata.get("userId"));
		requestbody.put("subDesc",testdata.get("subDesc"));
		requestbody.put("subDateTime",curTimeStamp);
		requestbody.put("subComments",testdata.get("subComments"));
		requestbody.put("subPathAttach1",testdata.get("subPathAttach1"));
		requestbody.put("subPathAttach2",testdata.get("subPathAttach2"));
		requestbody.put("subPathAttach3",testdata.get("subPathAttach3"));
		requestbody.put("subPathAttach4",testdata.get("subPathAttach4"));
		requestbody.put("subPathAttach5",testdata.get("subPathAttach5"));
//		requestbody.put("gradedBy",testdata.get("gradedBy"));
		requestbody.put("gradedDateTime",curTimeStamp);
		requestbody.put("grade",testdata.get("grade"));
	
		
		request.when().body(requestbody.toJSONString());
	}
	
	@When("User sends HTTPS Put Request and request Body for the api {string}")
	public void user_sends_https_put_request_and_request_body_for_the_api(String apiProperty) throws IOException {
		LinkedHashMap<String, String> iddata = reader.readexcelsheet(idexcelpath,"Sheet1",0);
		System.out.println("URL"+config.getApiUrl(apiProperty) + "/" + iddata.get("SubmissionId"));
		response = request.put(config.getApiUrl(apiProperty) + "/" + iddata.get("SubmissionId"));
		response.then().log().all();
	}

	//@tag18
	@When("User sends HTTPS Put Request and request Body for the api {string} with grade & valid submission ID from {string} and {int}")
	public void user_sends_https_put_request_and_request_body_for_the_api_with_grade_valid_submission_id_from_and(String apiProperty, String string2, Integer int1) throws IOException {
		LinkedHashMap<String, String> iddata = reader.readexcelsheet(idexcelpath,"Sheet1",0);
		System.out.println("URL"+config.get_grade_assignment_url() + iddata.get("SubmissionId"));
		response = request.put(config.get_grade_assignment_url() + iddata.get("SubmissionId"));
//		LinkedHashMap<String, String> testdata = reader.readexcelsheet(excelpath,"Sheet1",0);
//		System.out.println("URL"+config.get_grade_assignment_url() + testdata.get("submissionId"));
//		response = request.put(config.get_grade_assignment_url() + testdata.get("submissionId"));
		response.then().log().all();
		
	}
	@Then("user receives {int} status with response body with Grade submission response")
	public void user_receives_status_with_response_body_with_grade_submission_response(Integer expectedStatus) throws IOException {
	
		Assert.assertEquals(response.statusCode(), expectedStatus );
		
	}
	
	//@tag19
	@When("User sends HTTPS Put Request and request Body for the api {string} with grade & invalid submission ID from {string} and {int}")
	public void user_sends_https_put_request_and_request_body_for_the_api_with_grade_invalid_submission_id_from_and(String apiProperty, String string2, Integer int1) throws IOException {
	//	LinkedHashMap<String, String> iddata = reader.readexcelsheet(idexcelpath,"Sheet1",0);
		System.out.println("URL"+config.get_grade_assignment_url() + -1);
		response = request.put(config.get_grade_assignment_url() + -1);
		response.then().log().all();
		
	}
	
	//@tag20
	@When("User sends HTTPS update grade Request and request Body for the api {string}")
	public void user_sends_https_update_grade_request_and_request_body_for_the_api(String apiProperty) throws IOException {
		LinkedHashMap<String, String> iddata = reader.readexcelsheet(idexcelpath,"Sheet1",0);
		System.out.println("URL"+config.get_grade_assignment_url() + iddata.get("SubmissionId"));
		response = request.put(config.get_grade_assignment_url() + iddata.get("SubmissionId"));
		response.then().log().all();
	}
}
