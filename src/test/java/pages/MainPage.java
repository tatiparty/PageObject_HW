package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

public class MainPage {

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By authButton = By.cssSelector("button.header2__auth");

    private WebDriver driver;

    public MainPage openUrl(String url){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(url);
        return this;
    }

    public AuthPage goToAuth(){
        driver.findElement(authButton).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return new AuthPage(driver);
    }

}
