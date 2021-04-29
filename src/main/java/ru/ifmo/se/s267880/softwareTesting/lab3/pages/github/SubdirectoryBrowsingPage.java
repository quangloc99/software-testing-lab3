package ru.ifmo.se.s267880.softwareTesting.lab3.pages.github;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.se.s267880.softwareTesting.lab3.utils.WebDriverUtil;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

public class SubdirectoryBrowsingPage extends FileNavigationBrowsingPage {
    public SubdirectoryBrowsingPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPath() {
        return driver.findElements(By.xpath(
                "/html/body/div[4]/div/main/div[2]/div/div[1]/div[1]/div[2]/div/*[position() > 1]"
        )).stream().map(WebElement::getText).collect(Collectors.joining());
    }

    protected By getDirectoryLinksLocator() {
        return By.xpath("/html/body/div[4]/div/main/div[2]/div/div/div[3]/div[3]/div/div[contains(@class, 'Box-row')]");
    }

    public Map<String, WebElement> getDirectoryLinks() {
        // here I am assuming that the directory is never empty
        // but I have that case.
        var elms = new WebDriverWait(driver,3).until(d -> {
            var res = d.findElements(getDirectoryLinksLocator());
            if (res.size() == 0) return null;
            return res;
        });
        return elms.stream().map(rowElm -> {
            var secondLink = rowElm.findElements(By.xpath("div[2]/span/a"));  // "normal element"
            if (secondLink.size() > 0) {
                return secondLink.get(0);
            }
            return rowElm.findElement(By.xpath("div/a"));  // "go back to parent" element
        }).collect(Collectors.toMap(elm -> {
            return elm.getText();
        }, elm -> elm));
    }

    public void navigateTo(Path destination) throws FileNotFoundException {
        // Note that I choose not to return anything (e.g. the navigated page)
        // because I also need to determine the page type (either root, subdirectory, or file browsing)
        // which is not comfortable to do.
        // So the method user should determine themself (which is not hard in the scope of the lab).
        var path = getPath();
        for (var entry: getDirectoryLinks().entrySet()) {
            var currentPath = Paths.get(path, entry.getKey());
//            System.out.println(currentPath);
            if (!destination.startsWith(currentPath)) {
                continue;
            }
            var elm = entry.getValue();
            elm.click();
            new WebDriverWait(driver, 2).until(ExpectedConditions.invisibilityOf(elm));

            // tail-call optimization (hopefully :)))
            if (!destination.equals(currentPath)) {
                // Should use a fresh page tho, because this method can be called from root page.
                new SubdirectoryBrowsingPage(driver).navigateTo(destination);
            }
            return ;
        }
        throw new FileNotFoundException(
                String.format("Cannot go any further from %s when finding %s.", path, destination.toString())
        );
    }

    public void navigateTo(String absolutePathName) throws FileNotFoundException {
        navigateTo(Paths.get(absolutePathName));
    }

    @Override
    public RootDirectoryBrowsingPage backToRoot() {
        WebDriverUtil.clickThenWaitTillDisappear(
                driver,
                By.xpath("/html/body/div[4]/div/main/div[2]/div/div/div[1]/div[2]/div/span[1]/span/a")
        );
        return new RootDirectoryBrowsingPage(driver);
    }
}
