	 @TC06_Delete
Feature: Delete Module

	 
	 @tag1 @Submission_Module
  Scenario Outline: Check if user able to delete a submission with valid submission Id
		Given User creates Request with no parameters  
    When User sends HTTPS Delete Request and request Body for the api "assignment_submission" with sub id from "<sheetname>" and <rownum>
    Then user receives 200 status with message
    Examples:
    	| sheetname   | rownum |
    	| Sheet1      | 0      |
    
  @tag2 @Submission_Module
  Scenario Outline: Check if user able to delete a submission with valid submission Id
		Given User creates Request with no parameters  
    When User sends HTTPS Delete Request and request Body for the api "assignment_submission" with invalid sub id from "<sheetname>" and <rownum>
    Then user receives 404 status with response body success flag false
    Examples:
    	| sheetname   | rownum |
    	| Sheet1      | 0      |   
	 
	
	 @tag3 @Delete_Assignmentbyid_200_positive
  Scenario: To verify that user is able to delete a record with valid assignment id 
     Given User is provided with the Base Url and endpoint and valid assignmentId
    When User send the delete request for deleting assignment
    Then User receives "200" OK Status and response body
 
  @tag4 @Delete_Assignmentbyid_404_negetive
  Scenario: To verify that user is able to delete a record with valid LMS API endpoint and invalid assignment id 
    Given User creates delete request for the valid LMS API endpoint and invalid assignment id
    When User sends delete request for deleting assignment with invalid assignmentId
    Then User receives 404 not found status with message and boolean suceess datails
	 
	 

	
	@tag5_UserDelete
	Scenario: user able to delete a user with valid User Id
	Given User creates DELETE Request for valid endpoint and valid user ID
	When user sends the delete request with valid user id
		Then user will receive 200 success message for delete user
	
	@tag6_UserDelete
	Scenario: user able to delete a user with invalid User Id
	Given User creates DELETE Request for valid endpoint and invalid user ID
	When user sends the delete request with invalid user id
	Then user will receive 404 message for delete user
	
	
	@tag7 @DeleteBatch_Status200
  Scenario:  Delete a Batch with valid programName
    Given User creates Batch DELETE Request for the LMS API endpoint and valid programName
    When User sends HTTPS Request for Batch DELETE with valid programName
    Then User receives 200 Ok status with message
    
  @tag8 @DeleteBatch_Status404
  Scenario:  Delete a Batch with invalid programName
    Given User creates Batch DELETE Request for the LMS API endpoint and invalid programName
    When User sends HTTPS Request for Batch DELETE with invalid programName
    Then User receives 404 Not Found Status with message and boolean success details
    
	
	
	 @tag9 @Program_Module_deletebyName
   Scenario: Check if user able to delete a program with valid programName
   Given User creates DELETE Request for the LMS API endpoint  and  valid programName
   When User will send the request body along with programName
   Then response body 200 successfully deleted 
   
   @tag10 @deletebyName
   Scenario: Check if user able to delete a program with valid LMS API,invalid programName
   Given User creates DELETE Request for the LMS API endpoint  and  invalid programName
   When user send with invalid ProgramName
   Then response body 404 program not found
   
   @tag11 @deletebyID
   Scenario: Check if user able to delete a program with valid program ID
   Given User creates DELETE Request valid program ID
   When user will send the request with valid program iD
   Then Response body should be 200 successfully deleted
   
   @tag12 @deletebyId
   Scenario: Check if user able to delete a program with valid LMS API,invalid program ID
   Given User create delete request with invalid programID 
   When user will send the request with invalid program iD
   Then Response body should be 404 program not found
