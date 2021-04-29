package ru.ifmo.se.s267880.softwareTesting.lab3.pages.github;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.Page;

import java.util.Arrays;

public class HomePage extends Page {
    public static final String URL = "https://github.com";
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public static HomePage go(WebDriver driver) {
        driver.get(URL);
        return new HomePage(driver);
    }

    public boolean isLoggedIn() {
        var classList = Arrays.asList(driver.findElement(By.tagName("body")).getAttribute("class").split("\\s"));
        System.out.println(classList + " " + classList.contains("logged-in"));
        return classList.contains("logged-in");
    }

    public String getUserName() {
        try {
            var profileDropdown = driver.findElement(
                    By.xpath("/html/body/div[1]/header/div[7]/details/summary")
            );
            profileDropdown.click();
            var userNameContainer = new WebDriverWait(driver, 10).until(
                    d -> d.findElement(By.xpath("/html/body/div[1]/header/div[7]/details/details-menu/div[1]/a/strong"))
            );
            profileDropdown.click();
            return userNameContainer.getText().strip();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void logout() {
        if (!isLoggedIn()) {
            return ;
        }
        var logoutButton = driver.findElement(
                By.xpath("/html/body/div[1]/header/div[7]/details/details-menu/form/button")
        );
        logoutButton.submit();
    }

    public boolean login(String username, String password) {
        System.out.println(getUserName() + " " +  username + " " + username.equals(getUserName()));
        if (username.equals(getUserName())) return true;
        if (isLoggedIn()) {
            logout();
        }
        // There is no need for creating a new class for login
        // I just do it here
        driver.get("https://github.com/login");
        var loginField = new WebDriverWait(driver, 10).until(
                d -> d.findElement(By.id("login_field")));
        // I am confident that the login page is static, so getting the elements by IDs is ok
        loginField.sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("input[type=submit]")).submit();

        new WebDriverWait(driver, 3).until(d -> !d.getCurrentUrl().contains("login"));

//        if (!isLoggedIn()) {
//            driver.get(URL);
//            return false;
//        }
        return true;
    }
}
