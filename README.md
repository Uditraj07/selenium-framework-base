# Selenium Framework Base

This starter framework uses Java, Selenium, and TestNG with a structure that supports:

- Data-driven testing from CSV
- Local browser execution
- Selenium Grid execution
- Sequential runs
- Parallel runs
- Thread-safe WebDriver handling

## Tech Stack

- Java 17+
- Maven
- Selenium 4
- TestNG
- WebDriverManager

## Project Structure

```text
selenium-framework-base
|-- pom.xml
|-- testng-parallel.xml
|-- testng-sequential.xml
`-- src
    `-- test
        |-- java
        |   |-- base
        |   |-- config
        |   |-- data
        |   |-- driver
        |   |-- listeners
        |   |-- model
        |   |-- pages
        |   |-- tests
        |   `-- utils
        `-- resources
            |-- config
            `-- testdata
```

## Key Design Choices

- `DriverManager` uses `ThreadLocal<WebDriver>` so parallel execution is safe.
- `DriverFactory` can start local drivers or remote Grid sessions.
- `TestDataProviders` loads login data from CSV and feeds TestNG.
- `BaseTest` centralizes browser setup and teardown.
- `testng-sequential.xml` and `testng-parallel.xml` let you switch run style easily.

## Run Commands

Run sequentially on local Chrome:

```bash
mvn clean test
```

Run parallel suite:

```bash
mvn clean test -Pparallel
```

Run on local Firefox:

```bash
mvn clean test -Dbrowser=firefox
```

Run on Grid:

```bash
mvn clean test -Dexecution.type=grid -Dgrid.url=http://localhost:4444/wd/hub -Dbrowser=chrome
```

Run headless:

```bash
mvn clean test -Dheadless=true
```

Use a different environment file:

```bash
mvn clean test -Denv=dev
```

## Sample Scenario

The framework includes multiple UI flows against `https://the-internet.herokuapp.com`:

- valid login
- invalid login attempts from CSV
- login and logout flow
- add/remove element flow
- checkbox toggle flow
- dropdown selection flow

## Good Next Steps

1. Add Page Object classes for your actual application.
2. Replace CSV data with Excel or JSON if your team prefers.
3. Add retry logic, reporting, and CI integration.
4. Group smoke, regression, and sanity suites in separate TestNG XML files.
