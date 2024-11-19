package com.redsocial.service.Impl;

import com.redsocial.model.Comentario;
import com.redsocial.model.Publicacion;
import com.redsocial.repository.ComentarioRepository;
import com.redsocial.repository.PublicacionRepository;
import com.redsocial.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public List<Comentario> getAllComentarios() {
        return comentarioRepository.findAll();
    }

    @Override
    public Comentario agregarComentario(Long publicacionId, String contenido) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId).orElse(null);
        if (publicacion != null) {
            Comentario comentario = new Comentario();
            comentario.setContenido(contenido);
            comentario.setPublicacion(publicacion);
            return comentarioRepository.save(comentario);
        }
        return null;
    }

    @Override
    public List<Comentario> getComentariosByPublicacionId(Long publicacionId) {
        return comentarioRepository.findByPublicacionId(publicacionId);
    }
}