Feature: Validate conversion rates for past and future dates using Foreign Exchange Rates API
  Description: Validate that Foreign Exchange Rates API is accessible.

  Background: 
    Given Foreign Exchange Rates API is accessible

  @Functional
  Scenario Outline: Verify that API response is with correct status code when different query parameters are used
    When API is hit with end point as "<EndPoint>" "<QueryParam>" "<BaseCurrency>"
    Then API response is with status code as "<StatusCode>"
    And Response should contain not null values for "<BaseCurrency>"

    Examples: 
      | EndPoint   | QueryParam | BaseCurrency | StatusCode |
      | latest     | symbols    | INR          |        200 |
      | latest     | symbols    | USD,INR      |        200 |
      | latest     | base       | JPY          |        200 |
      | 2020-12-29 | base       | RUB          |        200 |
      | 2020-12-26 | symbols    | USD,CAD      |        200 |

  @Regression
  Scenario Outline: Verify that API response with correct base value for currency based on input
    When API is hit with end point as "<EndPoint>" "<QueryParam>" "<BaseCurrency>"
    Then API response is with status code as "200"
    And Response should contain base currency as "<BaseCurrency>"
    And Response should contain value "1.0" for "<BaseCurrency>"
    And Response should contain not null values for "<CheckCurrencies>"

    Examples: 
      | EndPoint | QueryParam | BaseCurrency | CheckCurrencies |
      | latest   | base       | GBP          | INR,AUD         |
      | latest   | base       | INR          | PHP,AUD         |
      | latest   | base       | AUD          | INR,USD         |
      | latest   | base       | HKD          | INR,AUD         |
      | latest   | base       | NZD          | INR,AUD         |

  @InvalidEndpoint
  Scenario Outline: Verify the API results on providing an invalid endpoint
    When API is hit with end point as ?base="<EndPoint>"
    Then API response is with status code as "<StatusCode>"
    And Error message should be displayed as "<ErrorMessage>"

    Examples: 
      | EndPoint | StatusCode | ErrorMessage                 |
      | BKG      |        400 | Base 'BKG' is not supported. |
      | 123      |        400 | Base '123' is not supported. |
      | @#%      |        400 | Base '@#%' is not supported. |

  @PastConversionRates
  Scenario Outline: Verify that API response is correct  for specific past date
    When API is hit with end point as "<EndPoint>" "<QueryParam>" "<BaseCurrency>"
    Then API response is with status code as "200"
    And Response should contain date as "<ResponseDate>"
    And Response should contain not null values for "<CheckCurrencies>"
    And Response should contain base currency as "<BaseCurrency>"

    Examples: 
      | EndPoint   | QueryParam | BaseCurrency | ResponseDate | CheckCurrencies |
      | 2020-12-15 | base       | INR          | 2020-12-15   | AUD,GBP         |
      | 2020-12-16 | base       | USD          | 2020-12-16   | NZD,INR         |
      | 2020-12-17 | base       | EUR          | 2020-12-17   | EUR,JPY         |

  @FutureConversionRates
  Scenario Outline: Verify that API response is corrected with current date on entering future date as query parameter
    When API is hit with end point as "<EndPoint>" "<QueryParam>" "<BaseCurrency>"
    Then API response is with status code as "200"
    And Response should contain date as "<ResponseDate>"
    And Response for future date should match with response for today
    And Response should contain not null values for "<CheckCurrencies>"

    Examples: 
      | EndPoint   | QueryParam | BaseCurrency | ResponseDate | CheckCurrencies |
      | 2021-01-20 | base       | EUR          | Today        | NZD,INR         |
      | 2021-01-31 | base       | JPY          | Today        | USD,CHF         |
