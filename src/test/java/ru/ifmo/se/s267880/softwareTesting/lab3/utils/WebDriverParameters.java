package ru.ifmo.se.s267880.softwareTesting.lab3.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class WebDriverParameters {
    public static final String MAIN_GITHUB_PAGE = "https://github.com";
    private static final ArrayList<WebDriver> createdDrivers = new ArrayList<>();
    private static boolean hookLoaded = false;

    public static ArrayList<Supplier<WebDriver>> getAvailableWebDriversFromProperties() {
        if (!hookLoaded) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                for (var i: createdDrivers) {
                    i.close();
                }
            }));
            hookLoaded = true;
        }
        try {
            LoadPropertiesToSystem.doLoad();
            var res = new ArrayList<Supplier<WebDriver>>();
            if (System.getProperty("webdriver.gecko.driver") != null) {
                res.add(() -> {
                    try {
                        var driver = new FirefoxDriver();
                        new CookiesStorage(new File("./firefox-cookies")).loadTo(driver, MAIN_GITHUB_PAGE);
                        createdDrivers.add(driver);
                        return driver;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            if (System.getProperty("webdriver.chrome.driver") != null) {
                res.add(() -> {
                    try {
                        var options = new ChromeOptions();
                        options.addArguments("user-data-dir=selenium");
                        var driver = new ChromeDriver(options);
//                        new CookiesStorage(new File("./chrome-cookies")).loadTo(driver, MAIN_GITHUB_PAGE);
                        createdDrivers.add(driver);
                        return driver;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return res;
        } catch (IOException e) {
            // better to report an error because we are need a web driver to load
            throw new RuntimeException(e);
        }
    }

    public static Collection<Object[]> getAvailableWebDriverFromPropertiesAsJunitParam() {
        return getAvailableWebDriversFromProperties().stream()
                .map(drv -> new Object[]{drv}).collect(Collectors.toList());
    }
}
