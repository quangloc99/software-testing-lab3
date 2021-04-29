package playgrounds;

import org.openqa.selenium.firefox.FirefoxDriver;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.HomePage;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.NewRepositoryPage;
import ru.ifmo.se.s267880.softwareTesting.lab3.utils.CookiesStorage;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class CreateNewRepoOnGithub {
    public static void main(String[] args) throws Exception {
        var out = System.out;
        System.setProperty("webdriver.gecko.driver", "/home/darkkcyan/Downloads/selenium-webdriver/geckodriver");
        var driver = new FirefoxDriver();
        driver.manage().window().maximize();
        new CookiesStorage(new File("./save-cookies")).loadTo(driver, HomePage.URL);
        driver.manage().getCookies().forEach(out::println);
        var newRepoPage = NewRepositoryPage.go(driver);
        newRepoPage.create("test-repo", "a very cool repo to test :))");
    }
}
