package com.tecnologicadeloriente.taller_RAA1_U2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FormularioSeleniumTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        // Inicializa el navegador (Asegúrate de tener Chrome instalado)
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    void testFlujoFormularioAutomatizado() {
        // 1. Navegar a la ruta del formulario servida por Spring Boot
        driver.get("http://localhost:8080/registro");

        // ==========================================
        // BLOQUE 1: REGISTRAR EL USUARIO (POST)
        // ==========================================

        // Localizar elementos del formulario de registro
        WebElement txtUsername = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
        WebElement txtPassword = driver.findElement(By.id("password"));
        WebElement btnEnviar = driver.findElement(By.id("btnEnviar"));

        // Simular la interacción del usuario real
        txtUsername.sendKeys("usuario_selenium");
        txtPassword.sendKeys("passwordSelenium123");

        // [CAPTURA 1: Formulario de Registro Diligenciado]
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btnEnviar.click();

        // Validar que aparezca el mensaje de éxito del registro en la interfaz
        WebElement alertExito = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mensajeExito")));
        assertTrue(alertExito.getText().contains("¡Usuario registrado con éxito!"));


        // ==========================================
        // BLOQUE 2: CONSULTAR EL USUARIO (GET)
        // ==========================================

        // Localizar elementos del módulo de búsqueda
        WebElement txtBuscar = driver.findElement(By.id("searchUsername"));
        WebElement btnBuscar = driver.findElement(By.id("btnBuscar"));

        // Escribimos el mismo usuario que acabamos de registrar en el sistema
        txtBuscar.sendKeys("usuario_selenium");

        // [CAPTURA 2: Campo de Búsqueda Diligenciado]
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btnBuscar.click();

        // Esperamos a que la caja de respuesta renderice los datos que vinieron de la API
        WebElement cajaResultado = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("resultadoBusqueda")));

        // [CAPTURA 3: Resultado de la API Renderizado en Pantalla]
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verificamos formalmente mediante la aserción que el texto en pantalla es el correcto
        assertTrue(cajaResultado.getText().contains("usuario_selenium (Existe en el sistema)"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit(); // Cierra el navegador de forma segura al finalizar
        }
    }
}