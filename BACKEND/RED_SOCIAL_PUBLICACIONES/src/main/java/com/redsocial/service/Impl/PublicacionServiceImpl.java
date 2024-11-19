package com.redsocial.service.Impl;

import com.redsocial.model.Comentario;
import com.redsocial.model.Publicacion;
import com.redsocial.repository.ComentarioRepository;
import com.redsocial.repository.PublicacionRepository;
import com.redsocial.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public List<Publicacion> obtenerTodasPublicaciones() {
        return publicacionRepository.findAll();
    }

    @Override
    public Publicacion obtenerPublicacionPorId(Long id) {
        return publicacionRepository.findById(id).orElse(null);
    }

    @Override
    public Publicacion crearPublicacion(String titulo, String contenido, MultipartFile imagen) {
        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(titulo);
        publicacion.setContenido(contenido);
        if (imagen != null) {
            // Aquí puedes agregar la lógica para guardar la imagen en un servicio de almacenamiento (como Cloudinary)
            // y luego guardar la URL de la imagen en la publicación
            publicacion.setImagenUrl("URL_DE_LA_IMAGEN");
        }
        return publicacionRepository.save(publicacion);
    }

    @Override
    public Publicacion actualizarPublicacion(Long id, String titulo, String contenido, MultipartFile imagen) {
        Publicacion publicacion = publicacionRepository.findById(id).orElse(null);
        if (publicacion != null) {
            publicacion.setTitulo(titulo);
            publicacion.setContenido(contenido);
            if (imagen != null) {
                // Aquí puedes agregar la lógica para guardar la imagen en un servicio de almacenamiento (como Cloudinary)
                // y luego actualizar la URL de la imagen en la publicación
                publicacion.setImagenUrl("URL_DE_LA_IMAGEN");
            }
            return publicacionRepository.save(publicacion);
        }
        return null;
    }

    @Override
    public void eliminarPublicacion(Long id) {
        publicacionRepository.deleteById(id);
    }

    @Override
    public Publicacion agregarComentario(Long publicacionId, String contenido) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId).orElse(null);
        if (publicacion != null) {
            Comentario comentario = new Comentario();
            comentario.setContenido(contenido);
            comentario.setPublicacion(publicacion);
            comentarioRepository.save(comentario);
            publicacion.getComentarios().add(comentario);
            return publicacionRepository.save(publicacion);
        }
        return null;
    }

    @Override
    public Publicacion eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId).orElse(null);
        if (publicacion != null) {
            comentarioRepository.deleteById(comentarioId);
            publicacion.getComentarios().removeIf(comentario -> comentario.getId().equals(comentarioId));
            return publicacionRepository.save(publicacion);
        }
        return null;
    }

    @Override
    public Publicacion actualizarComentario(Long publicacionId, Long comentarioId, String contenido) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId).orElse(null);
        if (publicacion != null) {
            Comentario comentario = comentarioRepository.findById(comentarioId).orElse(null);
            if (comentario != null) {
                comentario.setContenido(contenido);
                comentarioRepository.save(comentario);
                return publicacionRepository.save(publicacion);
            }
        }
        return null;
    }
}