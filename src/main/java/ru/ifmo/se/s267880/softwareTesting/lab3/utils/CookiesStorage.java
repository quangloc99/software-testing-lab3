package ru.ifmo.se.s267880.softwareTesting.lab3.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * The utility methods could be more general if they work with all Serializable.
 * But for the scope of the lab, I just need it work with Cookie.
 */
public class CookiesStorage {
    HashSet<Cookie> cookies;
    public CookiesStorage() {
        cookies = new HashSet<>();
    }

    public CookiesStorage(WebDriver driver) {
        cookies = new HashSet<>(driver.manage().getCookies());
    }

    public CookiesStorage(File file) throws IOException, ClassNotFoundException {
        try (var ois = new ObjectInputStream(new FileInputStream(file))) {
            cookies = (HashSet<Cookie>) ois.readObject();
        }
    }

    public void saveTo(File file) throws IOException {
        try (var oos = new ObjectOutputStream(new FileOutputStream(file))) {
            if (!(cookies instanceof Serializable)) {
                cookies = new HashSet<>(cookies);
            }
            oos.writeObject(cookies);
        }
    }

    public void loadTo(WebDriver driver) {
        cookies.forEach(driver.manage()::addCookie);
    }

    public void loadTo(WebDriver driver, String url) {
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        loadTo(driver);
        driver.navigate().refresh();
    }
}
