package com.tecnologicadeloriente.taller_RAA1_U2;

import com.tecnologicadeloriente.taller_RAA1_U2.controller.UsuarioController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioControllerUnitTest {

    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        // Inicializamos el controlador manualmente, de forma aislada
        usuarioController = new UsuarioController();
    }

    @Test
    void testCrearUsuario_Exitoso() {
        Map<String, String> datosIn = new HashMap<>();
        datosIn.put("username", "luis_garcia");
        datosIn.put("password", "clave123");

        ResponseEntity<Map<String, Object>> respuesta = usuarioController.crearUsuario(datosIn);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("Usuario registrado con éxito", respuesta.getBody().get("mensaje"));
        assertEquals("ACTIVO", respuesta.getBody().get("status"));
        assertEquals("luis_garcia", respuesta.getBody().get("usuario"));
    }

    @Test
    void testCrearUsuario_Error_UsernameVacio() {
        Map<String, String> datosIn = new HashMap<>();
        datosIn.put("username", "   ");
        datosIn.put("password", "clave123");

        ResponseEntity<Map<String, Object>> respuesta = usuarioController.crearUsuario(datosIn);

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertNotNull(respuesta.getBody());
        assertEquals("El nombre de usuario es obligatorio", respuesta.getBody().get("error"));
    }

    // ==========================================
    //   NUEVAS PRUEBAS UNITARIAS PARA EL GET
    // ==========================================

    @Test
    void testConsultarUsuario_Exitoso() {
        // 1. Precondición: Primero debemos registrar al usuario para que exista en la lista en memoria
        Map<String, String> registroIn = new HashMap<>();
        registroIn.put("username", "ana_valencia");
        registroIn.put("password", "secure123");
        usuarioController.crearUsuario(registroIn); // Lo guardamos

        // 2. Ejecutar la consulta del usuario que acabamos de guardar
        ResponseEntity<Map<String, Object>> respuesta = usuarioController.consultarUsuario("ana_valencia");

        // 3. Aserciones
        assertEquals(HttpStatus.OK, respuesta.getStatusCode()); // Espera 200 OK
        assertNotNull(respuesta.getBody());
        assertEquals("ana_valencia", respuesta.getBody().get("usuarioEncontrado"));
        assertEquals("Existe en el sistema", respuesta.getBody().get("resultado"));
    }

    @Test
    void testConsultarUsuario_Error_NoExiste() {
        // Ejecutar la consulta directamente con un usuario inventado que no está en la lista
        ResponseEntity<Map<String, Object>> respuesta = usuarioController.consultarUsuario("usuario_fantasma");

        // Aserciones
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode()); // Espera 404 Not Found
        assertNotNull(respuesta.getBody());
        assertEquals("Usuario no encontrado", respuesta.getBody().get("error"));
    }
}