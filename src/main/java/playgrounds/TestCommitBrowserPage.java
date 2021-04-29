package playgrounds;

import org.openqa.selenium.firefox.FirefoxDriver;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.RootDirectoryBrowsingPage;
import ru.ifmo.se.s267880.softwareTesting.lab3.utils.LoadPropertiesToSystem;

public class TestCommitBrowserPage {
    public static void main(String[] args) throws Exception {
        String url = "https://github.com/SeleniumHQ/selenium";
        LoadPropertiesToSystem.doLoad();
        var driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(url);
        var page = new RootDirectoryBrowsingPage(driver);
        var commitBrowserPage = page.openCommitBrowsingPage();
        commitBrowserPage.getCommitElementList().forEach(System.out::println);

        commitBrowserPage.getCommitElementList().get(1).browCommit();

        driver.close();
    }
}
