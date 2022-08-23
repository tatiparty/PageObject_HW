package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver;

    private By title = By.xpath("//div[contains(@class, 'container-padding-top-half')]/span/span");//переписать на "Данные успешно сохранены"
    private By firstName = By.id("id_fname");
    private By secondName = By.id("id_lname");
    private By blogName = By.id("id_blog_name");


    public String fillAndSaveData(){
        enterToTextArea(getElement(firstName), "Тест");
        enterToTextArea(getElement(secondName), "Тест");
        enterToTextArea(getElement(blogName), "Тест");

        driver.findElement(By.xpath("//button[@name = 'continue']")).click();
        return driver.findElement(title).getText();
    }
    public ProfilePage checkData(){
        checkTextInTextArea(getElement(firstName), "Тест");
        checkTextInTextArea(getElement(secondName), "Тест");
        checkTextInTextArea(getElement(blogName), "Тест");
        return new ProfilePage(driver);
    }

    private void enterToTextArea(WebElement element, String text){
        element.clear();
        element.sendKeys(text);
    }
    private void checkTextInTextArea(WebElement element, String expectedText){
        Assert.assertEquals(expectedText, element.getAttribute("value"));
    }

    private WebElement getElement(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
