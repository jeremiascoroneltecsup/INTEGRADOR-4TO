package com.redsocial.controller;

import com.redsocial.model.Comentario;
import com.redsocial.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comentarios")
@CrossOrigin(origins = "http://localhost:3000")  // Permite el acceso desde el frontend en React
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    // Obtener todos los comentarios
    @GetMapping
    public ResponseEntity<List<Comentario>> getAllComentarios() {
        List<Comentario> comentarios = comentarioService.getAllComentarios();
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }

    // Agregar un comentario a una publicación
    @PostMapping("/{publicacionId}/comentarios")
    public ResponseEntity<Comentario> agregarComentario(
            @PathVariable Long publicacionId,
            @RequestBody Comentario comentario) {
        if (comentario.getContenido() == null || comentario.getContenido().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Comentario nuevoComentario = comentarioService.agregarComentario(publicacionId, comentario.getContenido());
        return new ResponseEntity<>(nuevoComentario, HttpStatus.CREATED);
    }

    // Obtener comentarios por ID de publicación
    @GetMapping("/{publicacionId}/comentarios")
    public ResponseEntity<List<Comentario>> getComentariosByPublicacionId(@PathVariable Long publicacionId) {
        List<Comentario> comentarios = comentarioService.getComentariosByPublicacionId(publicacionId);
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }
}