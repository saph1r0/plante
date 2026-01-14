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
    private JavascriptExecutor js;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // CI / Jenkins safe
        if (System.getenv("CI") != null) {
            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--window-size=1920,1080",
                    "--disable-extensions",
                    "--remote-allow-origins=*"
            );
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;
    }

    @Test
    @DisplayName("Registro exitoso debe crear usuario")
    void registerExitosoDebeCrearUsuario() {

        driver.get("http://localhost:8080/web/login");

        // Toggle registro
        WebElement toggle = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("registerToggle"))
        );
        jsClick(toggle);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("registerForm")));

        driver.findElement(By.cssSelector("#registerForm input[type='text']"))
                .sendKeys("Maria Test");

        String email = "maria" + System.currentTimeMillis() + "@test.com";
        driver.findElement(By.id("register-correo")).sendKeys(email);
        driver.findElement(By.id("register-contrasena")).sendKeys("123456");

        // Checkbox términos
        WebElement checkbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("#registerForm input[type='checkbox']")
                )
        );
        jsClick(checkbox);

        // Botón registrar
        WebElement registerBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("button.register-btn"))
        );
        jsClick(registerBtn);

        // Assert seguro
        WebElement successMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("successMessage"))
        );

        assertTrue(successMessage.isDisplayed(),
                "Debe mostrarse mensaje de registro exitoso");
    }

    @Test
    @DisplayName("Registro sin aceptar términos NO debe mostrar mensaje de éxito")
    void registerSinAceptarTerminosNoDebeMostrarSuccess() {

        driver.get("http://localhost:8080/web/login");

        WebElement toggle = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("registerToggle"))
        );
        jsClick(toggle);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("registerForm")));

        driver.findElement(By.cssSelector("#registerForm input[type='text']"))
                .sendKeys("Maria Sin Terminos");

        String email = "maria" + System.currentTimeMillis() + "@test.com";
        driver.findElement(By.id("register-correo")).sendKeys(email);
        driver.findElement(By.id("register-contrasena")).sendKeys("123456");

        WebElement registerBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("button.register-btn"))
        );
        jsClick(registerBtn);

        boolean successVisible = false;
        try {
            successVisible = new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.visibilityOfElementLocated(
                            By.id("successMessage")
                    ))
                    .isDisplayed();
        } catch (TimeoutException ignored) {}

        assertFalse(successVisible,
                "NO debe mostrarse mensaje de éxito si no se aceptan los términos");
    }

    // 🔥 Utilidad CI/CD SAFE
    private void jsClick(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        js.executeScript("arguments[0].click();", element);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
