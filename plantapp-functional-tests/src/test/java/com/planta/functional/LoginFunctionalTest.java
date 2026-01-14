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

class LoginFunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        if (System.getenv("CI") != null) {
            options.addArguments(
                    "--headless=new",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--disable-gpu",
                    "--window-size=1920,1080",
                    "--disable-extensions",
                    "--disable-notifications",
                    "--disable-popup-blocking",
                    "--remote-allow-origins=*"
            );
        }

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    void irALogin() {
        driver.get(BASE_URL + "/web/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("login-correo")
        ));
    }

    void hacerLogin(String correo, String password) {

        WebElement email = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("login-correo"))
        );
        email.clear();
        email.sendKeys(correo);

        WebElement pass = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("login-contrasena"))
        );
        pass.clear();
        pass.sendKeys(password);

        WebElement btnLogin = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.login-btn"))
        );

        btnLogin.click();
    }

    @Test
    @DisplayName("Login exitoso redirige al dashboard")
    void loginExitosoDebeRedirigir() {

        irALogin();
        hacerLogin("maria@gmail.com", "mariaaa");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("dashboard"),
                ExpectedConditions.urlContains("plantas")
        ));

        assertFalse(
                driver.getCurrentUrl().contains("login"),
                "No debería permanecer en login tras login exitoso"
        );
    }

    @Test
    @DisplayName("Login inválido permanece en login")
    void loginInvalidoDebePermanecerEnLogin() {

        irALogin();
        hacerLogin("fake@test.com", "wrong");

        wait.until(ExpectedConditions.urlContains("login"));

        assertTrue(
                driver.getCurrentUrl().contains("login"),
                "Debe permanecer en login con credenciales inválidas"
        );
    }

    @Test
    @DisplayName("Campos vacíos no permiten login")
    void camposVaciosNoPermitenLogin() {

        irALogin();

        WebElement btnLogin = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.login-btn"))
        );

        btnLogin.click();

        wait.until(ExpectedConditions.urlContains("login"));

        assertTrue(
                driver.getCurrentUrl().contains("login"),
                "Debe seguir en login si no se llenan los campos"
        );
    }

    @Test
    @DisplayName("Toggle Login ↔ Register alterna formularios")
    void toggleLoginRegisterDebeFuncionar() {

        irALogin();

        WebElement loginForm = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("loginForm"))
        );
        WebElement registerForm = driver.findElement(By.id("registerForm"));

        assertTrue(loginForm.getAttribute("class").contains("active"));
        assertFalse(registerForm.getAttribute("class").contains("active"));

        driver.findElement(By.id("registerToggle")).click();

        wait.until(ExpectedConditions.attributeContains(
                registerForm, "class", "active"));

        driver.findElement(By.id("loginToggle")).click();

        wait.until(ExpectedConditions.attributeContains(
                loginForm, "class", "active"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
