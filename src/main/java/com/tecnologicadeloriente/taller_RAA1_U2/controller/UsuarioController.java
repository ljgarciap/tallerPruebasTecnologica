package com.tecnologicadeloriente.taller_RAA1_U2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UsuarioController {

    // Simulación de base de datos en memoria para el taller
    private static final List<Map<String, String>> baseDeDatosUsuarios = new ArrayList<>();

    @GetMapping("/registro")
    public String mostrarFormulario() {
        return "formulario";
    }

    // 1. POST: Crear Usuario (Modificado para guardar en la lista)
    @PostMapping("/api/usuarios")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> crearUsuario(@RequestBody Map<String, String> datos) {
        Map<String, Object> respuesta = new HashMap<>();

        if (datos.get("username") == null || datos.get("username").trim().isEmpty()) {
            respuesta.put("error", "El nombre de usuario es obligatorio");
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        // Guardamos el usuario en nuestra lista en memoria
        baseDeDatosUsuarios.add(datos);

        respuesta.put("mensaje", "Usuario registrado con éxito");
        respuesta.put("usuario", datos.get("username"));
        respuesta.put("status", "ACTIVO");

        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }

    // 2. NUEVO ENDPOINT GET: Consultar si un usuario existe
    @GetMapping("/api/usuarios/{username}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> consultarUsuario(@PathVariable String username) {
        Map<String, Object> respuesta = new HashMap<>();

        // Buscamos el usuario en la lista
        for (Map<String, String> usuario : baseDeDatosUsuarios) {
            if (usuario.get("username").equalsIgnoreCase(username)) {
                respuesta.put("usuarioEncontrado", usuario.get("username"));
                respuesta.put("resultado", "Existe en el sistema");
                return new ResponseEntity<>(respuesta, HttpStatus.OK); // Retorna 200 OK
            }
        }

        // Si no lo encuentra
        respuesta.put("error", "Usuario no encontrado");
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND); // Retorna 404 Not Found
    }
}