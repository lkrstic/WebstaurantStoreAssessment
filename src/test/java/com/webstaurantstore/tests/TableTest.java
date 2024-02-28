package com.webstaurantstore.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class TableTest {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
    	options.addArguments("--remote-allow-origins=*");
    	driver = new ChromeDriver(options);
    }

    @Test
    public void testWebstaurantStore() {
        // 1. Go to https://www.webstaurantstore.com/
        driver.get("https://www.webstaurantstore.com/");

        // 2. Search for 'stainless work table'
        WebElement searchBox = driver.findElement(By.id("searchval"));
        searchBox.sendKeys("stainless work table");
        searchBox.submit();

        // 3. Check the search result ensuring every product has the word 'Table' in its title.
        List<WebElement> products = driver.findElements(By.className("description"));
        for (WebElement product : products) {
            String title = product.getText();
            Assert.assertTrue(title.toLowerCase().contains("table"), "Product title does not contain 'Table': " + title);
        }

        // 4. Add the last of found items to Cart.
        List<WebElement> addToCartButtons = driver.findElements(By.xpath("//input[@value='Add to Cart']"));
        WebElement lastAddToCartButton = addToCartButtons.get(addToCartButtons.size() - 1);
        lastAddToCartButton.click();

        // 5. Empty Cart.
        WebElement cartButton = driver.findElement(By.xpath("//a[contains(@href, '/viewcart.cfm')]"));
        cartButton.click();
        WebElement emptyCartButton = driver.findElement(By.xpath("//button[contains(text(), 'Empty Cart')]"));
        emptyCartButton.click();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}