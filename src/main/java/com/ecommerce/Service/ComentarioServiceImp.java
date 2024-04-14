package com.ecommerce.Service;

import com.ecommerce.Model.ComentarioModel;
import com.ecommerce.Repository.IComentarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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

        comentariosExistentes = this.comentarioRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(comentariosExistentes.isEmpty()){

            this.comentarioRepository.save(comentario);

            textoRespuesta =  "El comentario ha sido creado con éxito";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (ComentarioModel i : comentariosExistentes) {
                if (comentario.getIdComentario().equals(i.getIdComentario())) {

                    textoRespuesta = "El comentario con ID: " + comentario.getIdComentario() + ", Ya se encuentra creado.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.comentarioRepository.save(comentario);

                    textoRespuesta = "El comentario ha sido creado con éxito";
                }
            }
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
