package com.redsocial.service;

import com.redsocial.model.Comentario;
import com.redsocial.model.Publicacion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PublicacionService {
    List<Publicacion> obtenerTodasPublicaciones();
    Publicacion obtenerPublicacionPorId(Long id);
    Publicacion crearPublicacion(String titulo, String contenido, MultipartFile imagen);
    Publicacion actualizarPublicacion(Long id, String titulo, String contenido, MultipartFile imagen);
    void eliminarPublicacion(Long id);
    Publicacion agregarComentario(Long publicacionId, String contenido);
    Publicacion eliminarComentario(Long publicacionId, Long comentarioId);
    Publicacion actualizarComentario(Long publicacionId, Long comentarioId, String contenido);

    List<Comentario> obtenerComentariosPorPublicacionId(Long publicacionId);
}