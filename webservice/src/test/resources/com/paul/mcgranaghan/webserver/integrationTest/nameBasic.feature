# Created by Paul at 22/12/2022
Feature: Find by name

  Background:
    Given the following titles exist:
      | t1 | Ocean's Eleven |
      | t2 | Titanic        |

  Scenario Outline: Find person by their name
    Given There is a person <name> with <nameid> who was a <titleId> of <titleRole>
    When I search for that user by the <name>
    Then I should find person with id <nameid> result

    Examples:
      | name           | nameid | titleId | titleRole | something |
      | "Brad Pitt"    | "n1"   | "t1"    | Actor     | blah      |
      | "NotBrad Pitt" | "n2"   | "t1"    | Director  | blah2     |
