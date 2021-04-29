package ru.ifmo.se.s267880.softwareTesting.lab3.pages.github;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.Page;

public abstract class FileNavigationBrowsingPage extends Page {
    @FindAll({
            @FindBy(id = "repo-content-pjax-container"),                    // I'm pretty sure this id is static
            @FindBy(xpath = "/html/body/div[4]/div/main/div[2]/div[1]")     // Fallback
    })
    protected WebElement container;

    public FileNavigationBrowsingPage(WebDriver driver) {
        super(driver);
    }

    public abstract String getPath();

    public abstract RootDirectoryBrowsingPage backToRoot();
}
