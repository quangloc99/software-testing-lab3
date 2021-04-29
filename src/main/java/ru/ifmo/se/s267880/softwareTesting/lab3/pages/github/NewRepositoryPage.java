package ru.ifmo.se.s267880.softwareTesting.lab3.pages.github;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.Page;

import java.time.LocalDateTime;
import java.time.LocalTime;

// I am confident that this page is static
public class NewRepositoryPage extends Page {
    @FindBy(id = "repository_name")
    private WebElement repositoryNameText;

    @FindBy(id = "repository_description")
    private WebElement repositoryDescriptionText;

    private WebElement getNonDisableSubmitButton() {
        try {
            return new WebDriverWait(driver, 3).until(d -> d.findElement(By.xpath(
                    "/html/body/div[4]/main/div/form/div[4]/button[not(@disabled)]"
            )));
        } catch (TimeoutException e) {
            return null;
        }
    }

    public NewRepositoryPage(WebDriver driver) {
        super(driver);
    }

    static public NewRepositoryPage go(WebDriver driver) {
        driver.get("https://github.com/new");
        var res = new NewRepositoryPage(driver);
        new WebDriverWait(driver, 30).until(
                ExpectedConditions.visibilityOf(res.repositoryDescriptionText));
        return res;
    }

    public void setRepositoryName(String name) {
        repositoryNameText.sendKeys(name);
    }

    public void setRepositoryDescription(String description) {
        repositoryDescriptionText.sendKeys(description);
    }

    public String createWithGithubSuggestedName(String repositoryDescription) {
        driver.findElement(By.xpath("/html/body/div[4]/main/div/form/div[4]/p/strong")).click();
        return create("-" + System.currentTimeMillis(), repositoryDescription);
    }

    public String create(String repositoryName, String repositoryDescription) {
        setRepositoryName(repositoryName);
        setRepositoryDescription(repositoryDescription);
        var submitButton = getNonDisableSubmitButton();
        if (submitButton == null) return null;
        submitButton.click();
        var repoLinkInput = new WebDriverWait(driver, 10)
                .until(d -> d.findElement(By.xpath("//*[@id=\"empty-setup-clone-url\"]")));
        return repoLinkInput.getAttribute("value");
    }
}
