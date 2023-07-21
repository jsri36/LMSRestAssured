package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utilities.ConfigReader;

public class ProgramBatch_SD {

	
	/**
	 * 
	 * Step Definitions
	 */
	
	ConfigReader config = new ConfigReader();	
	String baseURL=config.getBaseUrl();
	String getallurl=config.getBatch_Getall_Url();
	
	@Given("User creates Batch GET Request for the LMS API endpoint")
	public void user_creates_batch_get_request_for_the_lms_api_endpoint() {
	    // Write code here that turns the phrase above into concrete actions
	    
	}

	@When("User sends HTTPS Request")
	public void user_sends_https_request() {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Base URL is --------> "+baseURL );
	}

	@Then("User receives {string} OK Status with response body")
	public void user_receives_ok_status_with_response_body(String string) {
	    // Write code here that turns the phrase above into concrete actions
	  
	}
			
}
