package ru.ifmo.se.s267880.softwareTesting.lab3.pages.github;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.se.s267880.softwareTesting.lab3.utils.WebDriverUtil;

public class RootDirectoryBrowsingPage extends SubdirectoryBrowsingPage {
    public RootDirectoryBrowsingPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPath() {
        return "/";
    }

    @Override
    protected By getDirectoryLinksLocator() {
        return By.xpath(
                "/html/body/div[4]/div/main/div[2]/div/div/div[2]/div[1]/div/div[3]/div[1]/div[contains(@class, 'Box-row')]"
        );
    }

    public CommitBrowsingPage openCommitBrowsingPage() {
        var commitButton =
                new WebDriverWait(driver, 5)
                .until(

                        d -> d.findElement(By.xpath(
                                "/html/body/div[4]/div/main/div[2]/div/div/div[2]/div[1]/div/div[1]/div/div[4]/ul/li/a"
                        ))
                );
        commitButton.click();
        WebDriverUtil.clickThenWaitTillDisappear(driver, commitButton);
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf(commitButton));
        return new CommitBrowsingPage(driver);
    }

    public void waitTillLoaded() {
        new WebDriverWait(driver, 10).until(
                d -> d.findElement(By.xpath("/html/body/div[4]/div/main/div[2]/div/div/div[2]/div[1]/div[3]/div[1]/div/div[4]/ul/li/a/span/span"))
        );
    }

    @Override
    public RootDirectoryBrowsingPage backToRoot() {
        return this;
    }

}
