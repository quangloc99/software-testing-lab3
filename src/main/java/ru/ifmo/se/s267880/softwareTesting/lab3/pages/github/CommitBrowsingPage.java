package ru.ifmo.se.s267880.softwareTesting.lab3.pages.github;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommitBrowsingPage extends Page {
    public CommitBrowsingPage(WebDriver driver) {
        super(driver);
    }

    public class CommitElement {
        private WebElement container;

        public CommitElement(WebElement elm) {
            container = elm;
        }

        public String getCommitShortMessage() {
            return container.findElement(By.xpath("div[1]/p")).getText();
        }

        public String getCommitHashHead() {
            return container.findElement(By.xpath("div[2]/*[last() - 1]/a")).getText();
        }

        public RootDirectoryBrowsingPage browCommit() {
            var elm = container.findElement(By.xpath("div[2]/*[last()]/a"));
            elm.click();
            new WebDriverWait(driver, 3).until(ExpectedConditions.invisibilityOf(elm));
            return new RootDirectoryBrowsingPage(driver);
        }

        @Override
        public String toString() {
            return String.format("%s: %s", getCommitHashHead(), getCommitShortMessage());
        }
    }

    public List<CommitElement> getCommitElementList() {
        var locator = By.xpath(
                "//div[contains(@class, 'TimelineItem')]//ol/li"
        );
        return driver.findElements(locator).stream().map(CommitElement::new).collect(Collectors.toList());
    }
}
