package playgrounds;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.ifmo.se.s267880.softwareTesting.lab3.pages.github.HomePage;
import ru.ifmo.se.s267880.softwareTesting.lab3.utils.CookiesStorage;
import ru.ifmo.se.s267880.softwareTesting.lab3.utils.LoadPropertiesToSystem;

import java.io.File;

public class StoreGithubLoginCookies {
   public static void main(String[] args) throws Exception {
      LoadPropertiesToSystem.doLoad();
      var driver = new ChromeDriver();
      driver.manage().window().maximize();
      var homePage = HomePage.go(driver);
      driver.navigate().refresh();
      homePage.login("quangloc99-testaccount", "gX4rf7OZuV2b");
      Thread.sleep(30000);
      new CookiesStorage(driver).saveTo(new File("./chrome-cookies"));
   }
}
