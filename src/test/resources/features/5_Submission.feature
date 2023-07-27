
@TC05_Submission
Feature: Submission Module
  
  @tag1  
  Scenario Outline: Assignment submission
		Given User creates Request with all mandatory fields and additional fields from "<sheetname>" and <rownum> 
    When User sends HTTPS Post Request and request Body for the api "assignment_submission" 
    Then user receives 201 status with response body with submission response
    Examples:
    	| sheetname   | rownum |
    	| Sheet1      | 0      |
    	
  @tag2  
  Scenario Outline: Assignment submission bad request existings submission
		Given User creates Request with all mandatory fields and additional fields from "<sheetname>" and <rownum> 
    When User sends HTTPS Post Request and request Body for the api "assignment_submission"
    Then user receives 400 status with response body success flag false
    Examples:
    	| sheetname   | rownum |
    	| Sheet1      | 0      |

  @tag3 
  Scenario: Assignment submission bad request missing mandatory field
		Given User creates Request  with missing manadatory field
    When User sends HTTPS Post Request and request Body for the api "assignment_submission"
    Then user receives 400 status with response body success flag false
    
  @tag4 
  Scenario: get all Assignment submission
		Given User creates Request with no parameters  
    When User sends HTTPS Get Request and request Body for the api "assignment_submission"
    Then User receives 200 status with valid all submissions response 
 
  @tag5  
  Scenario Outline: Check if user able to retrieve a grades with valid Assignment ID
		Given User creates Request with no parameters  
    When User sends HTTPS Get Request and request Body for the api "assignment_getgrades" with assignmentId from "<sheetname>" and <rownum>
    Then User receives 200 status with valid grades response 
  	Examples:
    	| sheetname            | rownum |
    	| gradedAssignment      | 0      |
    	
  @tag6 
  Scenario Outline: Check if user able to retrieve a grades with invalid Assignment ID
		Given User creates Request with no parameters  
    When User sends HTTPS Get Request and request Body for the api "assignment_getgrades" with assignmentId from "<sheetname>" and <rownum>
    Then user receives 404 status with response body success flag false 
  	Examples:
    	| sheetname   | rownum |
    	| Sheet1      | 1      |
    
  @tag7 
  Scenario Outline: Check if user able to retrieve a grades with valid Student ID
		Given User creates Request with no parameters  
    When User sends HTTPS Get Request and request Body for the api "assignment_getgradesByStudentId" with Student from "<sheetname>" and <rownum>
    Then User receives 200 status with valid grades response 
  	Examples:
    	| sheetname            | rownum |
    	| gradedAssignment      | 0      |
    	
  @tag8 
  Scenario Outline: Check if user able to retrieve a grades with invalid Student ID
		Given User creates Request with no parameters  
    When User sends HTTPS Get Request and request Body for the api "assignment_getgradesByStudentId" with Student from "<sheetname>" and <rownum>
    Then user receives 404 status with response body success flag false 
  	Examples:
    	| sheetname   | rownum |
    	| Sheet1      | 1      |
    
   @tag9 @GETGrades_BatchID
  Scenario: Check if user able to retrieve GET Grades by BatchID with valid LMS API
    Given User is provided with the Base Url and endpoint for GET Grades by BatchID
    When User send the get request for GET Grades by BatchID
    Then User receives 200 status with valid grades response for submission
    
    @tag10 @GETGrades_BatchID
  Scenario: Check if user able to retrieve GET Grades by BatchID with valid LMS API
    Given User is provided with the Base Url and endpoint for GET Grades by BatchID
    When User send the get request for GET Grades by invalid BatchID
    Then User receives 404 status with valid grades response for invalid submission
    
    @tag15 
  Scenario Outline: Check if user able to update a submission with invalid submission ID and mandatory request body
		Given User creates Request for put with all mandatory fields and additional fields from "<sheetname>" and <rownum>
		When User sends HTTPS Put Request and request Body for the api "resubmit_assignment" with valid submission ID from "<sheetname>" and <rownum>
    Then user receives 200 status with response body with submission response
  	Examples:
    	| sheetname   | rownum |
    	| Sheet1      | 1      |
    	
  @tag16  
  Scenario Outline: Check if user able to update a submission with invalid submission ID and mandatory request body
		Given User creates Request for put with all mandatory fields and additional fields from "<sheetname>" and <rownum>
		When User sends HTTPS Put Request and request Body for the api "resubmit_assignment" with invalid submission ID from "<sheetname>" and <rownum>
    Then user receives 404 status with response body success flag false 
  	Examples:
    	| sheetname   | rownum |
    	| Sheet1      | 1      |
    	
  @tag17  
  Scenario: Assignment submission bad request missing mandatory field
		Given User update Request with missing manadatory field
		When User sends HTTPS Put Request and request Body for the api "assignment_submission"
    Then user receives 400 status with response body success flag false
 
 @tag18  
  Scenario Outline: Check if user able to  grade assignment with valid submission Id and mandatory request body
		Given User creates Request for put with all mandatory fields and additional fields from "<sheetname>" and <rownum>
		When User sends HTTPS Put Request and request Body for the api "Grade_assignment" with grade & valid submission ID from "<sheetname>" and <rownum>
    Then user receives 200 status with response body with Grade submission response
  	Examples:
    	| sheetname   | rownum |
    	| Sheet1      | 1      |
    	
  @tag19 
  Scenario Outline: Check if user able to  grade assignment with valid submission Id and mandatory request body
		Given User creates Request for put with all mandatory fields and additional fields from "<sheetname>" and <rownum>
		When User sends HTTPS Put Request and request Body for the api "Grade_assignment" with grade & invalid submission ID from "<sheetname>" and <rownum>
    Then user receives 404 status with response body success flag false
  	Examples:
    	| sheetname   | rownum |
    	| Sheet1      | 1      |
  
  @tag20  
  Scenario: Assignment submission bad request missing mandatory field
		Given User update Request with missing manadatory field
		When User sends HTTPS update grade Request and request Body for the api "Grade_assignment"
    Then user receives 400 status with response body success flag false
    
   #@Ignore @tag21 
  #Scenario Outline: Check if user able to delete a submission with valid submission Id
#		Given User creates Request with no parameters  
    #When User sends HTTPS Delete Request and request Body for the api "assignment_submission" with sub id from "<sheetname>" and <rownum>
    #Then user receives 200 status with message
    #Examples:
    #	| sheetname   | rownum |
    #	| Sheet1      | 0      |
    #
  #@@Ignore tag22 
  #Scenario Outline: Check if user able to delete a submission with valid submission Id
#		Given User creates Request with no parameters  
    #When User sends HTTPS Delete Request and request Body for the api "assignment_submission" with invalid sub id from "<sheetname>" and <rownum>
    #Then user receives 404 status with response body success flag false
    #Examples:
    #	| sheetname   | rownum |
    #	| Sheet1      | 0      |    