package ru.ifmo.se.s267880.softwareTesting.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Page {
    public final WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
