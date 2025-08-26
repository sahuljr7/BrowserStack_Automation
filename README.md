# BStack Demo Automation Framework

## Project Structure

```
bstack-demo-automation/
│
├── pom.xml                                 # Maven configuration file
├── README.md                              # Project documentation
├── .gitignore                             # Git ignore file
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── bstack/
│   │               ├── pages/             # Page Object Model classes
│   │               │   ├── BasePage.java
│   │               │   ├── LoginPage.java
│   │               │   ├── HomePage.java
│   │               │   └── CheckoutPage.java
│   │               │
│   │               └── utils/             # Utility classes
│   │                   ├── ConfigReader.java
│   │                   ├── ExtentReportManager.java
│   │                   └── TestDataProvider.java
│   │
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── bstack/
│       │           └── tests/            # Test classes
│       │               ├── BaseTest.java
│       │               ├── LoginTest.java
│       │               └── ECommerceTest.java
│       │
│       └── resources/                    # Test resources
│           ├── config.properties         # Configuration properties
│           ├── testng.xml               # TestNG suite configuration
│           ├── log4j2.xml               # Logging configuration
│           └── testdata/                # Test data files (if any)
│               ├── users.json
│               └── products.csv
│
├── test-output/                         # Generated test outputs
│   ├── extent-reports/                  # ExtentReports HTML reports
│   ├── screenshots/                     # Screenshots on failure
│   ├── testng-results.xml              # TestNG XML results
│   └── emailable-report.html           # TestNG HTML report
│
├── logs/                                # Application logs
│   ├── automation.log                   # Main log file
│   ├── errors.log                       # Error logs
│   └── test-results.log                 # Test execution logs
│
└── drivers/                             # WebDriver executables (optional)
    ├── chromedriver.exe
    ├── geckodriver.exe
    └── msedgedriver.exe
```

## Framework Components

### 1. Page Object Model (POM)
- **BasePage.java**: Common methods and properties for all page classes
- **LoginPage.java**: Page objects for login functionality
- **HomePage.java**: Page objects for home page interactions
- **CheckoutPage.java**: Page objects for checkout process

### 2. Test Classes
- **BaseTest.java**: Base test class with setup/teardown and common utilities
- **LoginTest.java**: Test cases for authentication functionality
- **ECommerceTest.java**: Test cases for shopping cart and checkout

### 3. Utilities
- **ConfigReader.java**: Configuration properties reader
- **ExtentReportManager.java**: ExtentReports configuration and management
- **TestDataProvider.java**: TestNG data providers for parameterized tests

### 4. Configuration Files
- **pom.xml**: Maven dependencies and build configuration
- **testng.xml**: TestNG suite and test configuration
- **config.properties**: Application configuration properties
- **log4j2.xml**: Logging configuration

## Features

### ✅ Implemented Features
- **Page Object Model (POM)** architecture
- **TestNG** for test execution and assertions
- **Selenium WebDriver** for browser automation
- **Maven** for dependency management and build
- **ExtentReports** for beautiful test reporting
- **Log4j2** for comprehensive logging
- **WebDriverManager** for automatic driver management
- **Data-driven testing** with TestNG data providers
- **Cross-browser testing** support (Chrome, Firefox, Edge)
- **Parallel execution** capability
- **Screenshot capture** on test failure
- **Configuration management** via properties files
- **Test categorization** and grouping
- **Retry mechanism** for flaky tests

### 🛠 Test Scenarios Covered

#### Authentication Tests
- Login page navigation and UI validation
- Successful login with valid credentials
- Login with different user types
- Logout functionality
- Form validation testing
- Session persistence verification

#### E-Commerce Tests
- Add single/multiple products to cart
- Product filtering by brand
- Product sorting functionality
- Favourites management
- Complete checkout process
- Cart item removal
- Cart total calculation

### 🚀 Getting Started

#### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browser

#### Installation & Setup
1. Clone the repository
2. Navigate to project directory
3. Install dependencies: `mvn clean install`
4. Update `config.properties` as needed
5. Run tests: `mvn test`

#### Running Tests
```bash
# Run all tests
mvn test

# Run specific test suite
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/testng.xml

# Run with specific browser
mvn test -Dbrowser=firefox

# Run in headless mode
mvn test -Dheadless=true

# Run with Maven profiles
mvn test -Pchrome
mvn test -Pfirefox
```

#### Test Reports
- **ExtentReports**: `test-output/extent-reports/`
- **TestNG Reports**: `test-output/emailable-report.html`
- **Logs**: `logs/automation.log`

### 📋 Test Execution Commands

```bash
# Run smoke tests only
mvn test -Dgroups=smoke

# Run regression tests
mvn test -Dgroups=regression

# Run tests in parallel
mvn test -Dparallel=methods -DthreadCount=3

# Generate Allure reports (if Allure is configured)
mvn allure:serve
```

### 🔧 Configuration

#### Browser Configuration
Update `config.properties`:
```properties
browser=chrome
headless=false
maximize.window=true
```

#### Timeout Configuration
```properties
implicit.wait=10
explicit.wait=10
page.load.timeout=30
```

### 📊 Reporting

The framework generates multiple types of reports:
1. **ExtentReports** - Rich HTML reports with screenshots
2. **TestNG Reports** - Built-in TestNG HTML/XML reports
3. **Log Files** - Detailed execution logs
4. **Screenshots** - Captured on test failures

### 🧪 Best Practices Implemented

1. **Page Object Model** for maintainable code
2. **Explicit waits** instead of Thread.sleep()
3. **Proper exception handling** and logging
4. **Data-driven testing** for test scalability
5. **Independent test methods** for parallel execution
6. **Configuration externalization** for environment management
7. **Comprehensive reporting** for test results analysis
8. **Version control ready** structure

This framework provides a solid foundation for automating the BStack Demo application with industry-standard practices and tools.
