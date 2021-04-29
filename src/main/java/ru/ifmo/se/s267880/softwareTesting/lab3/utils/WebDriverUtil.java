package ru.ifmo.se.s267880.softwareTesting.lab3.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverUtil {
    public static void clickThenWaitTillDisappear(WebDriver driver, By locator) {
        var elm = driver.findElement(locator);
        elm.click();
        new WebDriverWait(driver, 4).until(ExpectedConditions.invisibilityOf(elm));
    }

    public static void clickThenWaitTillDisappear(WebDriver driver, WebElement elm) {
        elm.click();
        new WebDriverWait(driver, 4).until(ExpectedConditions.invisibilityOf(elm));
    }
}
