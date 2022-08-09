import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webDriverFactory.WebDriverFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class HomeWork {

    private String login;
    private String password;
    public String browser;

    By firstName = By.id("id_fname");
    By secondName = By.id("id_lname");
    By blogName = By.id("id_blog_name");
    By country = By.xpath("//input[@name = 'country']/following-sibling::div");
    By city = By.xpath("//input[@name = 'city']/following-sibling::div");
    By language = By.xpath("//input[@name = 'english_level']/following-sibling::div");

    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class);

    protected WebDriver driver;

    @Before
    public void setUp(){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        login = properties.getProperty("login");
        password = properties.getProperty("password");
        browser = properties.getProperty("browser");

        driver = WebDriverFactory.create(browser.toUpperCase());
    }

    @After
    public void setDown(){
        if ( driver != null)
            driver.quit();
    }

    @Test
    public void testDataSaving(){

        String URL = "https://otus.ru";

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(URL);

        //авторизация
        auth();

        //переход в раздел "О себе"-> "Персональные данные"
        enterToLK();

        //заполнение данных
        fillData();

        //сохранение данных
        driver.findElement(By.xpath("//button[@name = 'continue']")).click();

        //Открытие https://otus.ru в "чистом браузере"
        driver.quit();
        driver = new ChromeDriver();
        driver.get(URL);

        //повторная авторизация и переход в раздел "О себе"-> "Персональные данные"
        auth();
        enterToLK();

        //проверка наличия сохраненных данных
        checkingData();

        logger.info(driver.manage().getCookies());
    }

    private void auth(){
        driver.findElement(By.cssSelector("button.header2__auth")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement form = driver.findElement(By.xpath("//form[@action = '/login/']"));
        form.findElement(By.xpath(".//input[@name = 'email']")).sendKeys(login);
        form.findElement(By.xpath(".//input[@name = 'password']")).sendKeys(password);
        form.findElement(By.xpath(".//button[@type = 'submit']")).submit();
    }

    private void enterToTextArea(WebElement element, String text){
        element.clear();
        element.sendKeys(text);
    }

    protected WebElement getElement(By locator){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void enterToLK() {
        By menu = By.xpath("//div[@class = 'header2-menu__item-wrapper header2-menu__item-wrapper__username']");

        Actions action = new Actions(driver);
        action.moveToElement(getElement(menu));
        action.perform();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        List<WebElement> webElements = driver.findElements(By.xpath("//div[@class = 'header2-menu__dropdown header2__right__menu__item__dropdown header2-menu__dropdown_right']/a"));
        webElements.get(0).click();
    }

    private void fillData(){
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

    public void checkTextInTextArea(WebElement element, String expectedText){
        Assert.assertEquals(expectedText, element.getAttribute("value"));
    }

    public void checkTextInCheckbox(WebElement element, String expectedText){
        Assert.assertEquals(expectedText, element.getText());
    }

    public void checkingData(){
        checkTextInTextArea(getElement(firstName), "Тест");
        checkTextInTextArea(getElement(secondName), "Тест");
        checkTextInTextArea(getElement(blogName), "Тест");
        checkTextInCheckbox(getElement(country), "Республика Беларусь");
        checkTextInCheckbox(getElement(city), "Борисов");
        checkTextInCheckbox(getElement(language), "Элементарный уровень (Elementary)");
    }
}
