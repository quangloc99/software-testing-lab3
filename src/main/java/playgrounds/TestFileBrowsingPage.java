package playgrounds;

import org.openqa.selenium.firefox.FirefoxDriver;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.FileBrowsingPage;

public class TestFileBrowsingPage {
    public static void main(String[] args) throws Exception {
        String url = "https://github.com/quangloc99/software-testing-lab1/blob/master/src/main/java/ru/ifmo/se/s267880/softwareTesting/lab1/Task1/MyMath.java";
        System.setProperty("webdriver.gecko.driver", "/home/darkkcyan/Downloads/selenium-webdriver/geckodriver");
        var driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(url);
        var page = new FileBrowsingPage(driver);
        System.out.println("Path: " + page.getPath());
        System.out.println("Content:");
        System.out.println(page.getContent());
        driver.close();
    }
}
