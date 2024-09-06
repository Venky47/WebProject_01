@LoginFunctionality
Feature: verify login functionalities

  @Login
  Scenario: login functionality
    Given application url "Orange HRM"
    When enter the below valid credentials
      | username | Admin    |
      | password | admin123 |
    Then I verify the "Title"
      | OrangeHRM |
