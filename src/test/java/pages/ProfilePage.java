package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProfilePage {
    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver;

    private By firstName = By.id("id_fname");
    private By secondName = By.id("id_lname");
    private By blogName = By.id("id_blog_name");
    private By country = By.xpath("//input[@name = 'country']/following-sibling::div");
    private By city = By.xpath("//input[@name = 'city']/following-sibling::div");
    private By language = By.xpath("//input[@name = 'english_level']/following-sibling::div");

    public void fillData(){
        enterToTextArea(getElement(firstName), "Тест");
        enterToTextArea(getElement(secondName), "Тест");
        enterToTextArea(getElement(blogName), "Тест");
        driver.findElement(country).click();
        List<WebElement> webElementsCountry = driver.findElements(By.xpath("//div[@class = 'lk-cv-block__select-scroll lk-cv-block__select-scroll_country js-custom-select-options']/button"));
        webElementsCountry.get(2).click();

        driver.findElement(city).click();
        List<WebElement> webElementsCity = driver.findElements(By.xpath("//div[@class = 'lk-cv-block__select-scroll lk-cv-block__select-scroll_city js-custom-select-options']/button"));
        webElementsCity.get(2).click();

        driver.findElement(language).click();
        List<WebElement> webElementsLanguage = driver.findElements(By.xpath("//div[@class = 'lk-cv-block__select-scroll  js-custom-select-options']/button"));
        webElementsLanguage.get(2).click();
    }

    public void checkData(){
        checkTextInTextArea(getElement(firstName), "Тест");
        checkTextInTextArea(getElement(secondName), "Тест");
        checkTextInTextArea(getElement(blogName), "Тест");
        checkTextInCheckbox(getElement(country), "Республика Беларусь");
        checkTextInCheckbox(getElement(city), "Борисов");
        checkTextInCheckbox(getElement(language), "Элементарный уровень (Elementary)");
    }

    private void enterToTextArea(WebElement element, String text){
        element.clear();
        element.sendKeys(text);
    }

    private void checkTextInTextArea(WebElement element, String expectedText){
        Assert.assertEquals(expectedText, element.getAttribute("value"));
    }

    private void checkTextInCheckbox(WebElement element, String expectedText){
        Assert.assertEquals(expectedText, element.getText());
    }

    private WebElement getElement(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

}
