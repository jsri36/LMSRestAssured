@TC04_Assignment
Feature: Assignment Module
Background: User sets Authorization to No Auth

 @PostAssignment_201_positive
  Scenario Outline: Create assignment with valid endpoint and request body from excelsheet "<Sheetname>" and <Rownumber>
      Given User creates assignment Post request from "<Sheetname>" and <Rownumber>
      When User sends HTTPS request with valid request body and valid endpoint
      Then User will receive 201 created with response body
      Examples:
      |Sheetname | Rownumber |
      |Sheet1    | 0         |   
      |Sheet1    | 1         |
      
      
  @PostAssignment_400_negetive
	Scenario Outline: Create assignment with valid endpoint and with existing assignmentName in request body from excelsheet "<Sheetname>" and <RowNumber>
		Given user creates assignment post request for mandatory fields from  "<Sheetname>" and <RowNumber>
    When user send post request with existing assignmentName requestbody and valid endpoint
    Then user will receive 400 with response body <RowNumber>
    Examples: 
      | Sheetname | RowNumber |
      | Sheet1    |         0 |
      | Sheet1    |         1 |
      | Sheet1    |         2 |
      
      
     @Get_Allassignment_200_positive
  Scenario: To verify that user is able to retrieve a record with valid LMS API 
    Given User is provided with the Base Url and endpoint
    When User send the get request
    Then User receives "200" OK Status and response body
    
    @Get_assignmentById_200_positive
    Scenario: To verify that user is able to retrieve a record with valid assignment ID
    Given User is provided with the Base Url,endpoint and assignment Id
    When User send the get request for get assignment by id
    Then User receives "200" OK Status with given response body
   
    
    @Get_assignmentById_404_negetive
    Scenario: To verify that user is able to retrieve a record with invalid assignment ID
     Given User creates GET request for the LMS API endpoint with invalid assignment ID
    When User sends HTTPS request
    Then User receives 404 NOT FOUND status with message and boolean success details
    
    @Get_assignmentForBatch_200_positive
    Scenario: To verify that user is able to retrieve a record with valid batch ID
     Given User is provided with the Base Url,endpoint and batch Id
    When User send the get request for get assignment for batchId
    Then User receives "200" OK Status and response body
    
    @Get_assignmentForBatch_404_negetive
    Scenario: To verify that user is able to retrieve a record with invalid batch ID
    Given User creates GET request for the LMS API endpoint with invalid batch ID
    When User sends HTTPS request
    Then User receives 404 NOT FOUND status with message and boolean success details
    
    @Put_updateAssignmentbyid_200_positive
    Scenario Outline: To verify that user is able to update a record with valid assignment id and mandatory request body
     Given user update assignment for given assignmentId with mandatory fields from  "<Sheetname>" and <RowNumber>
  When user send put request to update given assignmentId
  Then user will receive 200 status with valid response body from <RowNumber>
     Examples: 
      | Sheetname | RowNumber |
      | Sheet2    |         0 | 
    
     @Put_updateAssignmentbyid_404_negetive
     Scenario: To verify that user is able to update a record with invalid assignment id and mandatory request body
      Given User creates  Put Request with invalid assignmentId with mandatory request body
  	When user send Put request with invalid assignmentId
  	Then user will receive 404 status
  	
   @Put_updateAssignmentbyid_400_negetive
     Scenario Outline: To verify that user is able to update a record with valid assignment id and missing mandatory request body
     
  Given User updates assignment for given assignmentId with missing mandatory fields from  "<Sheetname>" and <RowNumber>
  When user send put request to validate mandatory fields for given assignmentId
  Then user will receive 400 status from <RowNumber>
     Examples: 
      | Sheetname | RowNumber |
      | Sheet3   |         0 | 
      | Sheet3    |         1 | 
      | Sheet3    |         2 | 
      | Sheet3    |         3 | 
      | Sheet3    |         4 | 
      | Sheet3    |         5 | 
      | Sheet3    |         6 | 
      | Sheet3    |         7 |
      | Sheet3    |         8 |  
      | Sheet3    |         9 |  
  
  #@Delete_Assignmentbyid_200_positive
  #Scenario: To verify that user is able to delete a record with valid assignment id 
     #Given User is provided with the Base Url and endpoint and valid assignmentId
    #When User send the delete request for deleting assignment
    #Then User receives "200" OK Status and response body
 #
  #@Delete_Assignmentbyid_404_negetive
  #Scenario: To verify that user is able to delete a record with valid LMS API endpoint and invalid assignment id 
    #Given User creates delete request for the valid LMS API endpoint and invalid assignment id
    #When User sends delete request for deleting assignment with invalid assignmentId
    #Then User receives 404 not found status with message and boolean suceess datails
