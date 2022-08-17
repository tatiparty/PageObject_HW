package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthPage {

    public AuthPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver;

    private By email = By.xpath("//form[@action = '/login/']//input[@name = 'email']");
    private By pass = By.xpath("//form[@action = '/login/']//input[@name = 'password']");
    private By authSubmitButton = By.xpath("//form[@action = '/login/']//button[@type = 'submit']");

    public AccountPage auth(String login, String password){
        driver.findElement(email).sendKeys(login);
        driver.findElement(pass).sendKeys(password);
        driver.findElement(authSubmitButton).submit();
        return new AccountPage(driver);
    }

}
