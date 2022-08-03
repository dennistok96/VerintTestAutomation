Feature: Search

  Scenario Outline: Search articles based on user input
    Given Initialize the browser with chrome
    And Navigate to "https://www.verint.com/" site
    When User click on search button
    And Input <searchedText> in the search box
    Then Verify that it shows all articles title with <searchedText> keyword
    Examples:
      | searchedText      |
      |"customer solution"|


