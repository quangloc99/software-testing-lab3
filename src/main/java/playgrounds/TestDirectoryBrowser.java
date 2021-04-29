package playgrounds;

import org.openqa.selenium.firefox.FirefoxDriver;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.RootDirectoryBrowsingPage;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.SubdirectoryBrowsingPage;

public class TestDirectoryBrowser {
    public static void main(String[] args) throws Exception {
        String url = "https://github.com/quangloc99/software-testing-lab1/";
//        String url = "https://github.com/quangloc99/software-testing-lab1/tree/master/.idea";
//        String url = "https://github.com/quangloc99/software-testing-lab1/tree/master/src/main/java/ru/ifmo/se/s267880/softwareTesting/lab1";
        System.setProperty("webdriver.gecko.driver", "/home/darkkcyan/Downloads/selenium-webdriver/geckodriver");
        var driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(url);
//        var page = new SubdirectoryBrowsingPage(driver);
        var page = new RootDirectoryBrowsingPage(driver);
        System.out.println("Path: " + page.getPath());
        page.getDirectoryLinks().forEach((name, elm) -> {
            System.out.println(name);
        });
        driver.close();
    }
}
