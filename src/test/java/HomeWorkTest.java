import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import webDriverFactory.WebDriverFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class HomeWorkTest {

    private String url = System.getProperty("baseURL", "https://otus.ru");

    private String login;
    private String password;
    public String browser;

    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class);

    private WebDriver driver;

    private By firstName = By.id("id_fname");
    private By secondName = By.id("id_lname");
    private By blogName = By.id("id_blog_name");

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
        //Войти в Личный кабинет
        String firstTitle = new MainPage(driver).
                openUrl(url).
                goToAuth().
                auth(login, password).
                enterToProfile().
                getStatus();

        Assert.assertEquals(firstTitle, "Личный кабинет");

        //Заполнить и сохранить данные
        fillAndSaveData();

        //Открыть https://otus.ru в "чистом браузере"
        driver.quit();
        driver = new ChromeDriver();
        driver.get(url);

        //Повторно войти в Личный кабинет
        String secondTitle = new MainPage(driver).
                openUrl(url).
                goToAuth().
                auth(login, password).
                enterToProfile().
                getStatus();

        Assert.assertEquals(secondTitle, "Личный кабинет");

        //Проверить данные
        checkData();

        logger.info(driver.manage().getCookies());
    }

    public void fillAndSaveData(){
        enterToTextArea(getElement(firstName), "Тест");
        enterToTextArea(getElement(secondName), "Тест");
        enterToTextArea(getElement(blogName), "Тест");

        driver.findElement(By.xpath("//button[@name = 'continue']")).click();

    }

    public void checkData(){
        checkTextInTextArea(getElement(firstName), "Тест");
        checkTextInTextArea(getElement(secondName), "Тест");
        checkTextInTextArea(getElement(blogName), "Тест");
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
