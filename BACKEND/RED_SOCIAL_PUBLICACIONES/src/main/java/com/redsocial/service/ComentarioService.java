package com.redsocial.service;

import com.redsocial.model.Comentario;

import java.util.List;

public interface ComentarioService {
    List<Comentario> getAllComentarios();
    Comentario agregarComentario(Long publicacionId, String contenido);
    List<Comentario> getComentariosByPublicacionId(Long publicacionId);
}