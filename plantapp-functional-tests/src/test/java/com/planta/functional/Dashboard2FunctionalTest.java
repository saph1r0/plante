package com.planta.functional;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Dashboard2FunctionalTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        // Opciones de Chrome para CI/CD
        ChromeOptions options = new ChromeOptions();

        // Si está en Jenkins (CI=true), ejecuta en modo headless
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

        //INICIA SESIÓN ANTES DE CADA TEST
        login();
    }

    // Método helper para hacer login
    private void login() {
        driver.get("http://localhost:8080/web/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-correo")))
                .sendKeys("maria@gmail.com"); // Usa un usuario válido

        driver.findElement(By.id("login-contrasena"))
                .sendKeys("mariaaa", Keys.ENTER); // Usa una contraseña válida

        // Espera a que termine el login y redirija
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("dashboard"),
                ExpectedConditions.urlContains("plantas")
        ));
    }

    @Test
    @DisplayName("El dashboard carga correctamente")
    void dashboardCargaCorrectamente() {
        driver.get("http://localhost:8080/web/plantas/dashboard2");

        WebElement titulo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//h1[contains(text(),'Dashboard Personal')]")
                )
        );

        assertTrue(titulo.isDisplayed());
    }

    @Test
    @DisplayName("Se muestran los contadores del dashboard")
    void seMuestranContadores() {
        driver.get("http://localhost:8080/web/plantas/dashboard2");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("total-plantas")));

        assertTrue(driver.findElement(By.id("total-plantas")).isDisplayed());
        assertTrue(driver.findElement(By.id("plantas-saludables")).isDisplayed());
        assertTrue(driver.findElement(By.id("plantas-cuidado")).isDisplayed());
        assertTrue(driver.findElement(By.id("cuidados-hoy")).isDisplayed());
    }

    @Test
    @DisplayName("Se muestran las secciones principales del dashboard")
    void seMuestranSecciones() {
        driver.get("http://localhost:8080/web/plantas/dashboard2");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("plantas-atencion")));

        assertTrue(driver.findElement(By.id("plantas-atencion")).isDisplayed());
        assertTrue(driver.findElement(By.id("ultimas-plantas")).isDisplayed());
        assertTrue(driver.findElement(By.id("proximos-cuidados")).isDisplayed());
    }

    @Test
    @DisplayName("Se muestran plantas o estado vacío")
    void seMuestranPlantasOEstadoVacio() {
        driver.get("http://localhost:8080/web/plantas/dashboard2");

        // Espera a que cargue la página
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        // Espera a que aparezca al menos una planta o un estado vacío
        wait.until(d ->
                !d.findElements(By.className("plant-card")).isEmpty()
                        || !d.findElements(By.className("empty-state")).isEmpty()
        );

        List<WebElement> plantas = driver.findElements(By.className("plant-card"));
        List<WebElement> emptyStates = driver.findElements(By.className("empty-state"));

        assertTrue(
                !plantas.isEmpty() || !emptyStates.isEmpty(),
                "Debe haber plantas o un estado vacío visible"
        );
    }

    @Test
    @DisplayName("Cerrar sesión redirige al login")
    void cerrarSesionRedirigeLogin() {
        driver.get("http://localhost:8080/web/plantas/dashboard2");

        WebElement btnCerrar = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("btn-cerrar-sesion"))
        );

        btnCerrar.click();

        // Espera al alert y acéptalo
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        // Espera redirección al login
        wait.until(ExpectedConditions.urlContains("/web/login"));

        assertTrue(driver.getCurrentUrl().contains("/web/login"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}