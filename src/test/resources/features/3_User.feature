@TC05_User
Feature: create different user and validate all users
Background: User sets Authorization to No Auth

  @1
  Scenario Outline: create users with valid endpoint and request body from excelsheet "<Sheetname>" and <RowNumber>
    Given user creates post request from  "<Sheetname>" and <RowNumber>
    When user send post request with valid requestbody and valid endpoint
  Then user will receive 201 created with response for user module

    Examples: 
      | Sheetname | RowNumber |
      | Sheet1    |         0 |
      | Sheet1    |         1 |
      | Sheet1    |         2 |
	@2
	Scenario Outline: create users with valid endpoint with existing phone no and missing mandatory fields request body from excelsheet "<Sheetname>" and <RowNumber>
		Given user creates post request for mandatory fields from  "<Sheetname>" and <RowNumber>
    When user send post request with existing phoneno requestbody and valid endpoint
 Then user will receive 400 with response body <RowNumber> for user module
 
    Examples: 
      | Sheetname | RowNumber |
      | Sheet1    |         3 |
      | Sheet1    |         4 |
      | Sheet1    |         5 |
      | Sheet1    |         6 |
      | Sheet1    |         7 |
      | Sheet1    |         8 |
      | Sheet1    |         9 |
      | Sheet1    |         10|

	
  @3get_all_users
  Scenario: retrieve all users with valid endpoint
  	Given user creates GET Request for the LMS API All User endpoint 
  	When user send get request with valid endpoint
  	Then user will receive 200 status with reponse body
 	
  @4get_user_by_id
  Scenario: user able to retrieve a user with valid User ID
  	Given User creates GET Request with valid User ID
  	When user send get request with valid user id
  	Then user will receive 200 status with given reponse body
 
   @5get_user_by_invalid_id
  Scenario: user able to retrieve a user with invalid User ID
  	Given User creates GET Request with invalid User ID
  	When user send get request with invalid user id
  	Then user will receive 404 status for user module
 
  @6get_all_staff
  Scenario: retrieve all staff users with valid endpoint
  	Given user creates GET Request for all staff
  	When user send get allstaff request with valid endpoint
  	Then user will receive 200 status with reponse body
	
	@7get_users_with_roles
 	Scenario: retrieve all users with roles with valid endpoint
  	Given user creates GET Request for all users with roles
  	When user send get request with roles valid endpoint
  	Then user will receive 200 status with reponse body for users with roles
  
  @8Put_update_user_with_valid_userid
  Scenario Outline: user able to update a user with valid User ID and request body
  Given update user for given userid with mandatory fields from  "<Sheetname>" and <RowNumber>
  When user send put request to update given user
  Then user will receive 200 status with valid response body from <RowNumber> for user module
     Examples: 
      | Sheetname | RowNumber |
      | Sheet2    |         0 | 
      
  @9Put_update_user_with_invalid_id
  Scenario: user able to update a user with invalid User ID
  	Given User creates  Put Request with invalid User ID
  	When user send Put request with invalid user id
  Then user will receive 404 status for user module
  	
  @10Put_mandatory_fields_validation	
  Scenario Outline: user able to update a user with missing mandatory fields in request body
  Given update user for given userid with missing mandatory fields from  "<Sheetname>" and <RowNumber>
  When user send put request to validate mandatory fields for given user
  Then user will receive 400 status from <RowNumber> for user module
     Examples: 
      | Sheetname | RowNumber |
      | Sheet2    |         1 | 
      | Sheet2    |         2 | 
      | Sheet2    |         3 | 
      | Sheet2    |         4 |
 
 @11Put_update_user_role_status_for_valid_id
  Scenario Outline: user able to update a user role status with valid User ID and request body
  Given update user role status given userid with mandatory fields from  "<Sheetname>" and <RowNumber>
  When user send put request to update given user role status
  Then user will receive 200 status for role status with valid response body from <RowNumber>
     Examples: 
      | Sheetname | RowNumber |
      | Sheet3    |         0 |  
      
   @12Put_update_user_role_status_invalid_id
  Scenario: user able to update a user role status with invalid User ID
  	Given creates Put Request with invalid User ID
  	When user send Put request with invalid user id for user role status
  Then user will receive 404 status for user module
  
  @13Put_user_role_status_mandatory_fields_validation	
  Scenario Outline: user able to update a user rolestatus with missing mandatory fields in request body
  Given update user role staus for given userid with missing mandatory fields from  "<Sheetname>" and <RowNumber>
  When user send put request to validate mandatory fields for update user role status
  Then user will receive 400 status bad request
     Examples: 
      | Sheetname | RowNumber |
      | Sheet3    |         1 | 
      | Sheet3    |         2 | 
      
   @14Put_update_assign_user_to_program_batch_valid_id
  Scenario Outline: user able to assign user to program / batch with valid User Id and request body
  Given update assign user to program/batch with mandatory fields from "<Sheetname>" and <RowNumber>
  When user send put request to update given user assign user to program/batch
  Then user will receive 200 status with valid response body for user module
     Examples: 
      | Sheetname | RowNumber |
      | Sheet4    |         0 |     
      
  @15Put_update_assign_user_to_program_batch_invalid_id
  Scenario: user able to assign user to program/batch with invalid User Id and request body
  Given update assign user to program/batch with mandatory fields using invalid id
  When user send put request to update given invalid userid assign user to program/batch
  Then user will receive 404 status for user module
   
  @16put_assign_user_to_program_batch_mandatory_field_validation
  Scenario Outline: user able to assign user to program/batch with missing mandatory fields in request body
  Given assign user to program/batch for given userid with missing mandatory fields from  "<Sheetname>" and <RowNumber>
  When user send put request to validate mandatory fields for assign user to program/batch
  Then user will receive 400 status for assign user to program/batch with <RowNumber>
     Examples: 
      | Sheetname | RowNumber |
      | Sheet4    |         1 | 
			| Sheet4    |         2 |
			
#	# Delete user valid id
#	@17_UserDelete
#	Scenario: user able to delete a user with valid User Id
#	Given User creates DELETE Request for valid endpoint and valid user ID
#	When user sends the delete request with valid user id
#		Then user will receive 200 success message for delete user
#		
#	# Delete user invalid id
#	@18_UserDelete
#	Scenario: user able to delete a user with invalid User Id
#	Given User creates DELETE Request for valid endpoint and invalid user ID
#	When user sends the delete request with invalid user id
#	Then user will receive 404 message for delete user
  
