
@TC02_Batch
Feature: As a tester,I want to test and validate Program Batch Module
Background: User sets Authoization to No Auth

 	@tag1 @PostBatch_Status201
  Scenario Outline: Create a Batch with valid endpoint and request body
    Given User creates Batch POST Request for the LMS API endpoint
    When User sends HTTPS Request with all fields from "<Sheetname>" and <RowNumber>
    Then User receives 201 Created Status with response body
    
    Examples: 
      | Sheetname | RowNumber |
      | Batch    |         0 |
     	| Batch    |         1 |
     
   	@tag2 @PostBatch_Status400
  Scenario Outline: Check if user able to create a Batch with valid endpoint and request body (existing value in Batch Name)
    Given User creates Batch POST Request for the LMS API endpoint
    When User sends HTTPS Request with all fields from "<Sheetname>" and <RowNumber>
    Then User receives 400 Bad Request Status with message and boolean success details
    
    Examples: 
      | Sheetname | RowNumber |
      | Batch    |         0 |
      
  @tag3 @PostBatch_Status400_Missingdata
  Scenario Outline: Check if user able to create a Batch with valid endpoint and request body (existing value in Batch Name)
    Given User creates Batch POST Request for the LMS API endpoint
    When User sends HTTPS Request with missing fields from "<Sheetname>" and <RowNumber>
    Then User receives 400 Bad Request Status with message and boolean success details
    
    Examples: 
      | Sheetname | RowNumber |
      | Batch    |         2 |
      
  @tag4 @GetAllBatch
  Scenario: Check if user able to retrieve all batches with valid LMS API
    Given User creates Batch GET Request for the LMS API endpoint
    When User sends HTTPS Request for Get All Batches
    Then User receives 200 OK Status with response body for Get All Batches

	@tag5 @GetBatchByID_Status200
  Scenario: Check if user able to retrieve a batch with valid BATCH ID
    Given User creates Batch GET Request for the LMS API endpoint with valid Batch ID
    When User sends HTTPS Request for Get Batch By ID
    Then User receives 200 OK Status with response body for Get Batch By ID

	@tag6 @GetBatchByID_Status404
  Scenario: Check if user able to retrieve a batch with invalid BATCH ID
    Given User creates Batch GET Request for the LMS API endpoint with invalid Batch ID
    When User sends HTTPS Request for Get Batch By InvalidID
    Then User receives 404 Not Found Status with message and boolean success details
    
	@tag7 @GetBatchByName_Status200
  Scenario: Check if user able to retrieve a batch with valid BATCH NAME
    Given User creates Batch GET Request for the LMS API endpoint with valid Batch name
    When User sends HTTPS Request for Get Batch By Name
    Then User receives 200 OK Status with response body for Get Batch By Name

	@tag8 @GetBatchByInvalidName_Status404
  Scenario: Check if user able to retrieve a batch with invalid BATCH NAME
    Given User creates Batch GET Request for the LMS API endpoint with Invalid Batch name
    When User sends HTTPS Request for Get Batch By InvalidName
    Then User receives 404 Not Found Status with message and boolean success details
    
	@tag9 @GetBatchByProgramID_Status200
  Scenario: Check if user able to retrieve a batch with valid Program ID
    Given User creates Batch GET Request for the LMS API endpoint with valid Program Id
    When User sends HTTPS Request for Get Batch By Program ID
    Then User receives 200 OK Status with response body for Get Batch By Program ID
    
  @tag10 @GetBatchByInvalidProgramID_Status404
  Scenario: Check if user able to retrieve a batch with invalid Program Id
    Given User creates Batch GET Request for the LMS API endpoint with invalid Program Id
    When User sends HTTPS Request for Get Batch By Invalid Program ID
    Then User receives 404 Not Found Status with message and boolean success details
    
  @tag11  @PutBatch_Status200
  Scenario Outline: Update a Batch with valid batchID and mandatory request body
    Given User creates Batch PUT Request for the LMS API endpoint and Valid batch Id
    When User sends HTTPS Request for update batch with all fields from "<Sheetname>" and <RowNumber>
    Then User receives 200 OK Status with updated value in response body for update Batch 
    
    Examples: 
      | Sheetname | RowNumber |
      | Batch    |         2 |
    
  @tag12  @PutBatch_Status404 
  Scenario Outline: Update a Batch with invalid batchID and mandatory request body
    Given User creates Batch PUT Request for the LMS API endpoint and invalid batch Id
    When User sends HTTPS Request for update batch with invalid batch Id & all fields from "<Sheetname>" and <RowNumber>
    Then User receives 404 Not Found Status with message and boolean success details
    
    Examples: 
      | Sheetname | RowNumber |
      | Batch    |         2 |
      
  @tag13 @PutBatch_Status400 
  Scenario Outline: Check if user able to update a Batch with valid batchID and missing mandatory fields request body
    Given User creates Batch PUT Request for the LMS API endpoint and Valid batch Id
    When User sends HTTPS Request for update batch with missing fields from "<Sheetname>" and <RowNumber>
    Then User receives 400 Bad Request Status with message and boolean success details
    
    Examples: 
      | Sheetname | RowNumber |
      | Batch    |         2 |
      
#	@Ignore @tag14 @DeleteBatch_Status200
  #Scenario:  Delete a Batch with valid programName
    #Given User creates Batch DELETE Request for the LMS API endpoint and valid programName
    #When User sends HTTPS Request for Batch DELETE with valid programName
    #Then User receives 200 Ok status with message
    #
  #@Ignore @tag15 @DeleteBatch_Status404
  #Scenario:  Delete a Batch with invalid programName
    #Given User creates Batch DELETE Request for the LMS API endpoint and invalid programName
    #When User sends HTTPS Request for Batch DELETE with invalid programName
    #Then User receives 404 Not Found Status with message and boolean success details
    