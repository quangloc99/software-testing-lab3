package playgrounds;

import org.openqa.selenium.firefox.FirefoxDriver;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.FileBrowsingPage;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.RootDirectoryBrowsingPage;

public class TestPageNavigation {
    public static void main(String[] args) throws Exception {
        String url = "https://github.com/SeleniumHQ/selenium/tree/64447d4b03f6986337d1ca8d8b6476653570bcc1";
        System.setProperty("webdriver.gecko.driver", "/home/darkkcyan/Downloads/selenium-webdriver/geckodriver");
        var driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(url);
        var page = new RootDirectoryBrowsingPage(driver);
        page.navigateTo("/java/client/src/org/openqa/selenium/support/ui/ExpectedConditions.java");
        System.out.println(new FileBrowsingPage(driver).getContent());
        driver.close();
    }
}
