package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AccountPage {
    public AccountPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver;

    private By menu = By.xpath("//div[@class = 'header2-menu__item-wrapper header2-menu__item-wrapper__username']");
    private By menuItem = By.xpath("//div[@class = 'header2-menu__dropdown header2__right__menu__item__dropdown header2-menu__dropdown_right']/a");

    public ProfilePage enterToProfile(){
        Actions action = new Actions(driver);
        action.moveToElement(getElement(menu));
        action.perform();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        List<WebElement> webElements = driver.findElements(menuItem);
        webElements.get(0).click();
        return new ProfilePage(driver);
    }

    private WebElement getElement(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
