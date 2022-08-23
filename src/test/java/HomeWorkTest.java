import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;
import pages.ProfilePage;
import webDriverFactory.WebDriverFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HomeWorkTest {

    private String url = System.getProperty("baseURL", "https://otus.ru");

    private String login;
    private String password;
    public String browser;

    private org.apache.logging.log4j.Logger logger = LogManager.getLogger(Logger.class);

    private WebDriver driver;

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
        //Войти в Личный кабинет, заполнить и сохранить данные
        String firstTitle = new MainPage(driver).
                openUrl(url).
                goToAuth().
                auth(login, password).
                enterToProfile().
                fillAndSaveData();

        Assert.assertEquals(firstTitle, "Данные успешно сохранены");

        //Открыть https://otus.ru в "чистом браузере"
        driver.quit();
        driver = new ChromeDriver();
        driver.get(url);

        //Повторно войти в Личный кабинет и проверить данные
        ProfilePage secondTitle = new MainPage(driver).
                openUrl(url).
                goToAuth().
                auth(login, password).
                enterToProfile().
                checkData();

        logger.info(driver.manage().getCookies());
    }

}
