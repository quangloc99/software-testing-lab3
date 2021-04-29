package ru.ifmo.se.s267880.softwareTesting.lab3.pages.github;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.ifmo.se.s267880.softwareTesting.lab3.utils.WebDriverUtil;

import java.util.stream.Collectors;

public class FileBrowsingPage extends FileNavigationBrowsingPage {
    public FileBrowsingPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public String getPath() {
        var elms = new WebDriverWait(driver, 10).until(d -> d.findElements(By.xpath(
                // position() > 1 ---- exclude the link to the root
                // position() < last() - 2 ----  exclude the function signature picker
                "//*[@id=\"blob-path\"]/*[position() > 1]"
        )));
        var sb = new StringBuilder();
        for (var elm: elms) {
            sb.append(elm.getText());
            if (elm.getTagName().equals("strong")) break;
        }
        return sb.toString();
    }

    public String getContent() {
        return driver.findElements(By.xpath(
                "/html/body/div[4]/div/main/div[2]/div/div/div[3]/div[2]/table/tbody/tr/td[2]"
        )).stream().map(WebElement::getText).collect(Collectors.joining("\n"));
    }

    @Override
    public RootDirectoryBrowsingPage backToRoot() {
        WebDriverUtil.clickThenWaitTillDisappear(driver,
                By.xpath("/html/body/div[4]/div/main/div[2]/div/div/div[1]/h2/span[1]/span/a"));
        return new RootDirectoryBrowsingPage(driver);
    }
}
