package com.ecommerce.Service;

import com.ecommerce.Model.CategoriaModel;
import com.ecommerce.Model.ComentarioModel;
import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Repository.IComentarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Primary

public class ComentarioServiceImp implements IComentarioService {

    @Autowired
    IComentarioRepository comentarioRepository;

    private List<ComentarioModel> comentariosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        comentariosExistentes= this.comentarioRepository.findAll();// Aca toma todas los articulos de la BD y las mete en el List.

    }
    @Override
    public String crearComentario(ComentarioModel comentario) {

        String textoRespuesta = "";
        try {
            String descripcion = comentario.getDescripcion();
            String fecha = comentario.getFecha();
            UsuarioModel idUsuario = comentario.getIdUsuario();

            comentariosExistentes = this.comentarioRepository.findAll();

            if (descripcion == null || descripcion.isBlank()) {
                textoRespuesta += "La descripcion no puede estar vacia o ser null\n";
            }
            if (fecha == null || fecha.isBlank()) {
                textoRespuesta += "La fecha no puede ser nula o estar vacia\n";
            }
            if (idUsuario == null) {
                textoRespuesta += "El id de su usuario no puede ser null\n";
            }
            if (!textoRespuesta.isEmpty()) {
                textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
            }else{
                if (comentariosExistentes.isEmpty()) {
                    this.comentarioRepository.save(comentario);
                    textoRespuesta = "El comentario ha sido creado con éxito. "  + "\n";
                }else {
                    this.comentarioRepository.save(comentario);
                    Integer idComentario = comentario.getIdComentario();
                    textoRespuesta = "id: " + idComentario;
                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Verifique bien los campos\n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "verifique si el usuario ya se encuentra creado en la base de datos\n";
        }
        return textoRespuesta;
    }

    @Override
    public List<ComentarioModel> listarComentario() {
        return this.comentarioRepository.findAll();
    }

    @Override
    public Optional<ComentarioModel> obtenerComentarioPorId(Integer idComentario) {
        return this.comentarioRepository.findById(idComentario);
    }

    @Override
    public String actualizarComentarioPorId(ComentarioModel comentario, Integer idComentario) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<ComentarioModel> comentarioEncontrado = this.comentarioRepository.findById(idComentario);

        if(comentarioEncontrado.isPresent()){

            ComentarioModel comentarioActualizar = comentarioEncontrado.get();

            BeanUtils.copyProperties(comentario, comentarioActualizar);

            this.comentarioRepository.save(comentario);

            return "El comentario con id: " + idComentario + ", Ha sido actualizado con exito.";

        }else{

            textoRespuesta = "El comentario con id: "+ idComentario + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
}
