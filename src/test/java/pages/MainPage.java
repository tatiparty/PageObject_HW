package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By authButton = By.cssSelector("button.header2__auth");
    private By email = By.xpath("//form[@action = '/login/']//input[@name = 'email']");
    private By pass = By.xpath("//form[@action = '/login/']//input[@name = 'password']");
    private By authSubmitButton = By.xpath("//form[@action = '/login/']//button[@type = 'submit']");
    private By menu = By.xpath("//div[@class = 'header2-menu__item-wrapper header2-menu__item-wrapper__username']");
    private By menuItem = By.xpath("//div[@class = 'header2-menu__dropdown header2__right__menu__item__dropdown header2-menu__dropdown_right']/a");

    private WebDriver driver;

    public void openUrl(String URL){
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(URL);
    }

    public void auth(String login, String password){
        driver.findElement(authButton).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.findElement(email).sendKeys(login);
        driver.findElement(pass).sendKeys(password);
        driver.findElement(authSubmitButton).submit();
    }

    public void enterToProfile(){
        Actions action = new Actions(driver);
        action.moveToElement(getElement(menu));
        action.perform();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        List<WebElement> webElements = driver.findElements(menuItem);
        webElements.get(0).click();
    }

    private WebElement getElement(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
