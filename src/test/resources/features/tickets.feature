Feature: Ticket functionalities

  Background:
    Given our spring application is running

  Scenario: Getting no tickets
    When I ask for all tickets
    Then I should get no tickets

  Scenario: Getting users
    Given mongo contains the following tickets
      | ID                       | Title                   | Description                                       |
      | 5e87311dc716d22b3cc983c0 | Create project          | Create spring boot project with Spring Initializr |
      | 5e87311dc716d22b3cc983c1 | Create TicketController | Implement Reactive TicketController               |
    When I ask for all tickets
    Then I should get following tickets
      | ID                       | Title                   | Description                                       |
      | 5e87311dc716d22b3cc983c0 | Create project          | Create spring boot project with Spring Initializr |
      | 5e87311dc716d22b3cc983c1 | Create TicketController | Implement Reactive TicketController               |
