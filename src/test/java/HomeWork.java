import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
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

public class HomeWork {

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

        String URL = "https://otus.ru";

        MainPage firstAuthPage = new MainPage(driver);

        firstAuthPage.openUrl(URL);

        //авторизация
        firstAuthPage.auth(login, password);

        //переход в раздел "О себе"-> "Персональные данные"
        firstAuthPage.enterToProfile();

        //заполнение данных
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.fillData();

        //сохранение данных
        driver.findElement(By.xpath("//button[@name = 'continue']")).click();

        //Открытие https://otus.ru в "чистом браузере"
        driver.quit();
        driver = new ChromeDriver();
        driver.get(URL);

        //повторная авторизация и переход в раздел "О себе"-> "Персональные данные"
        MainPage secondAuthPage = new MainPage(driver);
        secondAuthPage.auth(login, password);
        secondAuthPage.enterToProfile();

        //проверка наличия сохраненных данных
        ProfilePage profilePageForCheckData = new ProfilePage(driver);
        profilePageForCheckData.checkData();

        logger.info(driver.manage().getCookies());
    }
}
