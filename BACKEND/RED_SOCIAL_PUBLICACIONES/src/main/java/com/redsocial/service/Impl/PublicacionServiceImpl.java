package com.redsocial.service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.redsocial.model.Comentario;
import com.redsocial.model.Publicacion;
import com.redsocial.repository.ComentarioRepository;
import com.redsocial.repository.PublicacionRepository;
import com.redsocial.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private Cloudinary cloudinary;

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
            String imagenUrl = guardarImagenEnCloudinary(imagen);
            publicacion.setImagenUrl(imagenUrl);
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
                String imagenUrl = guardarImagenEnCloudinary(imagen);
                publicacion.setImagenUrl(imagenUrl);
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

    @Override
    public List<Comentario> obtenerComentariosPorPublicacionId(Long publicacionId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId).orElse(null);
        if (publicacion != null) {
            return publicacion.getComentarios();
        }
        return null;
    }

    private String guardarImagenEnCloudinary(MultipartFile imagen) {
        try {
            Map uploadResult = cloudinary.uploader().upload(imagen.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}