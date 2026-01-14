package com.planta.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class RegisterFunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Si estamos en Jenkins o CI/CD
        if (System.getenv("CI") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-extensions");
            options.addArguments("--remote-allow-origins=*");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("Registro exitoso debe crear usuario")
    void registerExitosoDebeCrearUsuario() {

        driver.get("http://localhost:8080/web/login");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("registerToggle")))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("registerForm")));

        driver.findElement(By.cssSelector("#registerForm input[type='text']"))
                .sendKeys("Maria Test");

        String email = "maria" + System.currentTimeMillis() + "@test.com";
        driver.findElement(By.id("register-correo"))
                .sendKeys(email);

        driver.findElement(By.id("register-contrasena"))
                .sendKeys("123456");

        // CHECKBOX
        WebElement checkbox = driver.findElement(
                By.cssSelector("#registerForm input[type='checkbox']")
        );

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", checkbox);
        js.executeScript("arguments[0].click();", checkbox);

        // Submit
        driver.findElement(By.cssSelector("button.register-btn"))
                .click();

        // Assert
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage")));

        assertTrue(
                driver.findElement(By.id("successMessage")).isDisplayed(),
                "Debería mostrarse mensaje de registro exitoso"
        );
    }

    @Test
    @DisplayName("Registro sin aceptar términos NO debe mostrar mensaje de éxito")
    void registerSinAceptarTerminosNoDebeMostrarSuccess() {

        driver.get("http://localhost:8080/web/login");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("registerToggle")))
                .click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("registerForm")));

        driver.findElement(By.cssSelector("#registerForm input[type='text']"))
                .sendKeys("Maria Sin Terminos");

        String email = "maria" + System.currentTimeMillis() + "@test.com";
        driver.findElement(By.id("register-correo"))
                .sendKeys(email);

        driver.findElement(By.id("register-contrasena"))
                .sendKeys("123456");

        //NO marcar checkbox de términos
        driver.findElement(By.cssSelector("button.register-btn"))
                .click();

        wait.withTimeout(Duration.ofSeconds(3));

        WebElement successMessage = driver.findElement(By.id("successMessage"));

        assertFalse(
                successMessage.isDisplayed(),
                "NO debería mostrarse el mensaje de éxito si no se aceptan los términos"
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
