package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {

    // Method to enter text into a WebElement and press ENTER
    public static boolean enterText(WebElement element, String text) {
        try {
            element.click();
            element.clear();
            element.sendKeys(text);
            element.sendKeys(Keys.ENTER);
            WebDriverWait wait = new WebDriverWait((WebDriver) element, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.stalenessOf(element));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to click on a WebElement
    public static boolean clickOnElement(WebElement element, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to count the number of products with a rating of 4 or less
    public static int washingMachineItemCount(WebDriver driver) {
        List<WebElement> products = driver.findElements(By.xpath("//div[@class='_5OesEi']"));
        int count = 0;
        for (WebElement product : products) {
            try {
                WebElement ratingElement = product.findElement(By.xpath("./span/div"));
                double rating = Double.parseDouble(ratingElement.getText());
                if (rating <= 4) {
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    // Method to print the title and discount of iPhone products with a discount greater than 17%
    public static void titleAndDiscountOfIphone(List<WebElement> parentElements, WebDriver driver) {
        int count = 0;
        for (WebElement parent : parentElements) {
            try {
                WebElement discount = parent.findElement(By.xpath("./div[2]/div[1]/div[1]/div[3]/span"));
                int discountPercentage = Integer.parseInt(discount.getText().replaceAll("[^0-9]", ""));
                WebElement title = parent.findElement(By.xpath("./div[1]/div[1]"));
                String titleText = title.getText();
                if (discountPercentage > 17) {
                    System.out.println("Title: " + titleText + " and discount percentage: " + discountPercentage);
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total number of items found with more than 17% discount: " + count);
    }

    // Method to close the login popup on Flipkart
    public static void closePopup(WebDriver driver) {
        try {
            WebElement popup = driver.findElement(By.xpath("//span[@class='_30XB9F']"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(popup));
            popup.click();
        } catch (Exception e) {
            System.out.println("Login popup didn't appear.");
        }
    }

    public static void titleAndImageUrl(WebDriver driver) {
        try {
            List<WebElement> productTitles = new ArrayList<>();
            List<WebElement> productImages = new ArrayList<>();
            List<Integer> productReviews = new ArrayList<>();

            List<WebElement> products = driver.findElements(By.xpath("//div[@class='slAVV4']"));
            for (WebElement product : products) {
                WebElement titleElement = product.findElement(By.xpath(".//a[@class='wjcEIp']"));
                WebElement imageElement = product.findElement(By.xpath(".//img"));
                WebElement reviewsElement = product.findElement(By.xpath(".//span[@class='Wphh3N']"));

                productTitles.add(titleElement);
                productImages.add(imageElement);
                productReviews.add(extractNumber(getText(reviewsElement)));
            }

            List<Integer> sortedIndices = new ArrayList<>();
            for (int i = 0; i < productReviews.size(); i++) {
                sortedIndices.add(i);
            }
            sortedIndices.sort(Comparator.comparingInt(productReviews::get).reversed());

            for (int i = 0; i < 5; i++) {
                int index = sortedIndices.get(i);
                System.out.println("Title: " + getText(productTitles.get(index)));
                System.out.println("Image URL: " + productImages.get(index).getAttribute("src"));
            }

            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getText(WebElement element) {
        return element.getText();
    }

    public static int extractNumber(String str) {
        StringBuilder number = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                number.append(c);
            }
        }
        if (number.length() > 0) {
            return Integer.parseInt(number.toString());
        } else {
            throw new IllegalArgumentException("No number found in the string");
        }
    }
}
