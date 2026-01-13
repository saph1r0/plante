package com.planta.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class LoginFunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("Login exitoso debe redirigir al dashboard")
    void loginExitosoDebeMostrarDashboard() {
        // Arrange
        driver.get("http://localhost:8080/web/login");

        // Act
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-correo")))
                .sendKeys("maria@gmail.com");

        driver.findElement(By.id("login-contrasena"))
                .sendKeys("mariaaa", Keys.ENTER);

        // Assert - Espera explícita en lugar de Thread.sleep()
        wait.until(ExpectedConditions.urlContains("dashboard"));

        assertTrue(driver.getCurrentUrl().contains("dashboard"),
                "La URL debería contener 'dashboard' después del login exitoso");
    }

    @Test
    @DisplayName("Login inválido no debe redirigir")
    void loginInvalidoDebePermanecerEnLogin() {

        driver.get("http://localhost:8080/web/login");

        driver.findElement(By.id("login-correo"))
                .sendKeys("fake@test.com");

        driver.findElement(By.id("login-contrasena"))
                .sendKeys("wrong");

        driver.findElement(By.cssSelector("button.login-btn")).click();

        wait.until(ExpectedConditions.urlContains("login"));

        assertTrue(driver.getCurrentUrl().contains("login"),
                "Debe permanecer en login con credenciales inválidas");
    }

    @Test
    @DisplayName("Campos vacíos no permiten login")
    void camposVaciosNoPermitenLogin() {

        driver.get("http://localhost:8080/web/login");

        driver.findElement(By.cssSelector("button.login-btn")).click();

        assertTrue(driver.getCurrentUrl().contains("login"),
                "Debe seguir en login si no se llenan los campos");
    }

    @Test
    @DisplayName("Login con credenciales inválidas no debe mostrar success ni redirigir")
    void loginConCredencialesInvalidasNoDebeEntrar() {

        driver.get("http://localhost:8080/web/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-correo")))
                .sendKeys("invalido@test.com");

        driver.findElement(By.id("login-contrasena"))
                .sendKeys("passwordIncorrecta", Keys.ENTER);

        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlContains("dashboard")
        ));

        // Seguimos en login
        assertTrue(
                driver.getCurrentUrl().contains("/login"),
                "Con credenciales inválidas no debería salir del login"
        );

        // No aparece success
        assertTrue(
                driver.findElements(By.id("successMessage")).isEmpty(),
                "No debería mostrarse mensaje de éxito"
        );

        // Sí aparece error
        assertFalse(
                driver.findElements(By.id("errorMessage")).isEmpty(),
                "Debería mostrarse un mensaje de error"
        );
    }

    @Test
    @DisplayName("Toggle Login ↔ Register debe alternar formularios")
    void toggleLoginRegisterDebeFuncionar() {

        driver.get("http://localhost:8080/web/login");

        WebElement loginForm = driver.findElement(By.id("loginForm"));
        WebElement registerForm = driver.findElement(By.id("registerForm"));

        assertTrue(loginForm.getAttribute("class").contains("active"));
        assertFalse(registerForm.getAttribute("class").contains("active"));

        driver.findElement(By.id("registerToggle")).click();

        wait.until(ExpectedConditions.attributeContains(registerForm, "class", "active"));

        assertTrue(registerForm.getAttribute("class").contains("active"));
        assertFalse(loginForm.getAttribute("class").contains("active"));

        // Volver a Login
        driver.findElement(By.id("loginToggle")).click();

        wait.until(ExpectedConditions.attributeContains(loginForm, "class", "active"));

        assertTrue(loginForm.getAttribute("class").contains("active"));
        assertFalse(registerForm.getAttribute("class").contains("active"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}