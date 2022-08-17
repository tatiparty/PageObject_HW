package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage {
    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver;

    private By title = By.xpath("//h1[@class = 'title__text']");

    public String getStatus(){
        return driver.findElement(title).getText();
    }
}
