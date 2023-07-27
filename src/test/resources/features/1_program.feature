
@TC01_Program
Feature: Program module
  
    @tag1
    Scenario Outline: create a program id
    Given The POST endpoint and the reqeust payload from "<Sheetname>" and <RowNumber> for Assignment
    When I send a POST reqeust for creating an program
    Then The program is successfully created 201
    
    Examples: 
      | Sheetname | RowNumber |
      | Sheet1    |         0 |
      | Sheet1    |         1 |
      
    @tag2
    Scenario: Check if user able to create a program with valid endpoint and request body (existing values in Program Name)
    Given User creates POST Request for the LMS API endpoint
    When send request body from excel
    Then  response body should be 400 program already exists
    
    @tag3
    Scenario: Missing mandotiry field request 
    Given User creates request for missing field
    When send the request body with null data
    Then response is 400 for missing data with boolean
    
  @tag4 @alldata
  Scenario: List all program
    Given There are program
    When I fetch data with program id
    Then The program are listed

   @tag5 @getByID
   Scenario: Get program details with the programid
   Given send a get request by using programid
   When store the program id and assign it as a endpoint
   Then we need to get the program with 200 status code
   
   @tag6 @updateByName
   Scenario: Get response with programName
   Given User creates PUT Request for the LMS API endpoint  and Valid program Name
   When fetch the date with stored ProgramName
   Then response need to status code 200 
   
    
   @tage7  
   Scenario: Get negative sceanrio  with programName
   Given User creates PUT Request for the LMS API endpoint  and Valid program Name
   When User sends HTTPS Request  and  request Body with mandatory fields.
   Then User receives 404 Not Found Status with message and boolean success details for program
   
   
   @tag8
   Scenario: Check if user able to update a program using valid program name
   Given User creates PUT Request for the LMS API endpoint 
   When User sends HTTPS Request  and request Body  
   Then User receives 400 Bad Request Status with message and boolean success details for program
   
   @tag9 @putByID
   Scenario: Check if user able to update a program with invalid programID
   Given User creates PUT Request with invalid programID
   When User sends reqeuset body with mandatory fields
   Then user need to get 404 status code not found
   
   @tag10 @putByID
   Scenario: user able to update a program  missing mandatory fields 
   Given User creates Request with valid programID
   When user sends request with null value
   Then user need to get 400 mising data
   
   @tag11 @putByID
   Scenario: Check if user able to update a program with valid programID
   Given User creates PUT Request with Valid programID
   When user send request fields
   Then user need to get 200 status code
   
   @tag12 @getByID
   Scenario: user is able to retrive with invalid prgramID
   Given user creates get request with invalid programID
   When user send request fields with invalid id
   Then response need to be 404 not found status 
   
   #@Ignore @tag13 @deletebyName
   #Scenario: Check if user able to delete a program with valid programName
   #Given User creates DELETE Request for the LMS API endpoint  and  valid programName
   #When User will send the request body along with programName
   #Then response body 200 successfully deleted 
   #
   #@Ignore @tag14 @deletebyName
   #Scenario: Check if user able to delete a program with valid LMS API,invalid programName
   #Given User creates DELETE Request for the LMS API endpoint  and  invalid programName
   #When user send with invalid ProgramName
   #Then response body 404 program not found
   #
   #@Ignore @tag15 @deletebyID
   #Scenario: Check if user able to delete a program with valid program ID
   #Given User creates DELETE Request valid program ID
   #When user will send the request with valid program iD
   #Then Response body should be 200 successfully deleted
   #
   #@Ignore @tag16 @deletebyId
   #Scenario: Check if user able to delete a program with valid LMS API,invalid program ID
   #Given User create delete request with invalid programID 
   #When user will send the request with invalid program iD
   #Then Response body should be 404 program not found
   
   
   
   