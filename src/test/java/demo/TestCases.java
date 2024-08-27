package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    @BeforeTest
    public void startBrowser() {
        // Set up logging configuration
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // Set up Chrome options and logging preferences
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        // Set the path for ChromeDriver log
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        // Initialize the ChromeDriver
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        // Close the browser after test execution
        driver.quit();
    }

    @Test
    public void testCase01() {
        System.out.println("Start test case 01");
        try {
            // Navigate to Flipkart homepage
            driver.get("https://www.flipkart.com/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
            wait.until(ExpectedConditions.urlContains("flipkart.com"));
            
            // Search for "Washing Machine"
            WebElement searchbar = driver
                    .findElement(By.xpath("//input[@title='Search for Products, Brands and More']"));
            Wrappers.enterText(searchbar, "Washing Machine");
            
            // Click on the "Popularity" tab
            WebElement popularityTab = driver.findElement(By.xpath("//div[@class='sHCOk2']/div[2]"));
            Wrappers.clickOnElement(popularityTab, driver);
        
            // Count products with rating <= 4
            int count = Wrappers.washingMachineItemCount(driver);
            System.out.println("Number of products with rating <= 4: " + count);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Rating less than or equal to 4 not found for a product.");
        }
        System.out.println("End test case 01");
    }

    @Test
    public void testCase02() {
        System.out.println("Start test case 02");
        try {
            // Navigate to Flipkart homepage
            driver.get("https://www.flipkart.com/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.urlContains("flipkart.com"));
            
            // Close login popup if it appears
            Wrappers.closePopup(driver);
            
            // Search for "iPhone"
            WebElement searchbar = driver.findElement(By.xpath("//input[@title='Search for Products, Brands and More']"));
            Wrappers.enterText(searchbar, "iPhone");
            
            // Get the title and discount of iPhone products with discount > 17%
            List<WebElement> parentElements = driver.findElements(By.xpath("//div[@class='yKfJKb row']"));
            Wrappers.titleAndDiscountOfIphone(parentElements, driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("End test case 02");
    }

    @Test
    public void testCase03() {
        System.out.println("Start test case 03");
        try {
            // Navigate to Flipkart homepage
            driver.get("https://www.flipkart.com/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.urlContains("flipkart.com"));
            
            // Close login popup if it appears
            Wrappers.closePopup(driver);
            
            // Search for "Coffee Mug"
            WebElement searchbar = driver.findElement(By.xpath("//input[@title='Search for Products, Brands and More']"));
            Wrappers.enterText(searchbar, "Coffee Mug");
            
            // Filter by 4-star rating
            WebElement fourStarIcon = driver.findElement(By.xpath("//div[contains(text(),'4')]/parent::label"));
            Wrappers.clickOnElement(fourStarIcon, driver);
            
            // Retrieve titles and image URLs of the top 5 products based on reviews
            Wrappers.titleAndImageUrl(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("End test case 03");
    }
}
