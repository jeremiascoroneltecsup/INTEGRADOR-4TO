package com.redsocial.controller;

import com.redsocial.model.Comentario;
import com.redsocial.model.Publicacion;
import com.redsocial.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/publicaciones")
@CrossOrigin(origins = "http://localhost:3000")  // Permite el acceso desde el frontend en React
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public ResponseEntity<List<Publicacion>> obtenerTodasPublicaciones() {
        List<Publicacion> publicaciones = publicacionService.obtenerTodasPublicaciones();
        return new ResponseEntity<>(publicaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publicacion> obtenerPublicacionPorId(@PathVariable Long id) {
        Publicacion publicacion = publicacionService.obtenerPublicacionPorId(id);
        if (publicacion != null) {
            return new ResponseEntity<>(publicacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Publicacion> crearPublicacion(
            @RequestParam("titulo") String titulo,
            @RequestParam("contenido") String contenido,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        Publicacion publicacion = publicacionService.crearPublicacion(titulo, contenido, imagen);
        return new ResponseEntity<>(publicacion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> actualizarPublicacion(
            @PathVariable Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("contenido") String contenido,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {
        Publicacion publicacion = publicacionService.actualizarPublicacion(id, titulo, contenido, imagen);
        if (publicacion != null) {
            return new ResponseEntity<>(publicacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long id) {
        publicacionService.eliminarPublicacion(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{publicacionId}/comentarios")
    public ResponseEntity<Publicacion> agregarComentario(
            @PathVariable Long publicacionId,
            @RequestParam("contenido") String contenido) {
        Publicacion publicacion = publicacionService.agregarComentario(publicacionId, contenido);
        if (publicacion != null) {
            return new ResponseEntity<>(publicacion, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<Publicacion> eliminarComentario(
            @PathVariable Long publicacionId,
            @PathVariable Long comentarioId) {
        Publicacion publicacion = publicacionService.eliminarComentario(publicacionId, comentarioId);
        if (publicacion != null) {
            return new ResponseEntity<>(publicacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<Publicacion> actualizarComentario(
            @PathVariable Long publicacionId,
            @PathVariable Long comentarioId,
            @RequestParam("contenido") String contenido) {
        Publicacion publicacion = publicacionService.actualizarComentario(publicacionId, comentarioId, contenido);
        if (publicacion != null) {
            return new ResponseEntity<>(publicacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{publicacionId}/comentarios")
    public ResponseEntity<List<Comentario>> obtenerComentariosPorPublicacionId(@PathVariable Long publicacionId) {
        List<Comentario> comentarios = publicacionService.obtenerComentariosPorPublicacionId(publicacionId);
        return new ResponseEntity<>(comentarios, HttpStatus.OK);
    }
}