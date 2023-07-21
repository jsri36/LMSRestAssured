#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: As a tester,I want to test and validate Program Batch Module
Background: User sets Authoization to No Auth

  @tag1
  Scenario: Check if user able to retrieve all batches with valid LMS API
    Given User creates Batch GET Request for the LMS API endpoint
    When User sends HTTPS Request 
    Then User receives "200" OK Status with response body
