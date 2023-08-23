Feature: Test LMS api program module with BaseUrl and Endpoints

  Scenario: validating User able to retrieve all programs with valid endpoint
    Given User is provided with the BaseUrl and the Endpoints
    When User send the HTTPsGET request
    Then User validates the response with Status code 200 OK with response body

  Scenario: validating User able to create a program with valid endpoint and Payload
    Given User is provided with the BaseUrl and the Endpoints
    When User send the HTTPsPOST request to server with the payload with mandatory and additional fields
    Then User validates the response with Status code 201 Created Status with response body

  Scenario: validating User able to retrieve program with valid programID
    Given User is provided with the BaseUrl and the Endpoints
    When User send the HTTPsGET request with valid programID
    Then User validates the response with Status code 200 OK with response body

  Scenario: validating user able to update a program with valid programID and Payload
    Given User is provided with the BaseUrl and the Endpoints
    When User send the HTTPsPUT request with valid programID and the payload
    Then User validates the response with Status code 200 OK status with updated value in response body

  Scenario: validating user able to update a program with valid programName and Payload
    Given User is provided with the BaseUrl and the Endpoints
    When User send the HTTPsPUT request with valid programName and payload
    Then User validates the response with Status code 200 OK status with updated value in response body

  Scenario: validating user able to delete a program with valid programName
    Given User is provided with the BaseUrl and the Endpoints
    When User send the HTTPsDELETE request with valid programName
    Then User validates the response with Status code 200 OK status with message

  #Scenario: validating user able to delete a program with valid programId
    #Given User is provided with the BaseUrl and the Endpoints
    #When User send the HTTPsDELETE request with valid programId
    #Then User validates the response with Status code 200 OK status with message