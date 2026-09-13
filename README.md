# Java Automation Framework (TestNG)

Browser + API tests for two public demo sites, written in plain TestNG.
No Serenity, no Cucumber — just Selenium, RestAssured and TestNG, so the
project stays small and easy to follow.

What gets tested:

- UI — [the-internet.herokuapp.com](https://the-internet.herokuapp.com/)
  (login, checkboxes, dropdown, dynamic loading, file up/download,
  frames, JS alerts, hovers, multiple windows, notifications)
- API — [Petstore](https://petstore.swagger.io/) (pets, store orders, users)

## What you need

- Java 21
- Maven 3.9+
- Chrome or Firefox (the driver binary is handled for you by WebDriverManager,
  no manual chromedriver setup)

## Running the tests

Run everything:

```bash
mvn test
```

Just the API tests (no browser needed):

```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-api.xml
```

Just the UI tests:

```bash
mvn test -DsuiteXmlFile=src/test/resources/testng-ui.xml
```

Useful options:

```bash
# run headed, or on firefox
mvn test -Dheadless=false
mvn test -Dbrowser=firefox

# run a single class
mvn test -DsuiteXmlFile=src/test/resources/testng-api.xml -Dtest=PetApiTests
```

Tests run methods in parallel (see the `testng*.xml` suites). Each UI test
gets its own browser instance, so parallel runs don't step on each other.

## Running without XML files

If you'd rather not touch the suite XMLs, there's a runner that builds the
suite at runtime. Anything new is picked up automatically as long as it sits
in the right package with the right group:

```bash
mvn test-compile exec:java -Dexec.args="--suite=api"
mvn test-compile exec:java -Dexec.args="--suite=ui --browser=firefox --headless=false --threads=2"
mvn test-compile exec:java -Dexec.args="--suite=all --groups=smoke"
```

Flags: `--suite=all|api|ui`, `--groups=a,b`, `--parallel=methods`,
`--threads=N`, `--browser=chrome|firefox`, `--headless=true|false`.
Add `--dry-run` to only print the generated suite
(`test-output/dynamic-testng.xml`) without running it. Every flag also works
as `-Dsuite=api` etc., which is handy in CI.

## Reports

After a run you'll find:

- `test-output/extent-report.html` — the readable report with screenshots
  attached to failures
- `test-output/screenshots/` — failure screenshots
- `target/surefire-reports/` — the standard TestNG/JUnit XML output

## How it's laid out

```
src/test/java/com/automation/
  base/        UiBaseTest / ApiBaseTest — setup every test inherits
  pages/       page objects, one class per page, locators kept private
  api/         thin wrappers around the Petstore endpoints
  models/      request/response POJOs (Jackson, Lombok builders)
  utils/       config reader, random test data, JSON, Excel, files
  listeners/   ExtentReports logging + one automatic retry for flakes
  tests/api/   PetApiTests, StoreApiTests, UserApiTests
  tests/ui/    one class per page under test
src/test/resources/
  config.properties   base URLs, browser, timeouts
  testng.xml          full regression suite
  testng-api.xml      API only
  testng-ui.xml       UI only
```

A couple of things worth knowing:

- Test data is generated fresh for every run (see `utils/DataGenerator`),
  so tests don't depend on whatever the demo sites happen to contain.
- Anything flaky by nature (the notification messages page picks one of
  two messages at random) is asserted accordingly instead of being
  hardcoded.
- The old Google/IMDB scraping checks from the JUnit project were left
  out on purpose — scraping Google results is too brittle to be worth
  automating.
