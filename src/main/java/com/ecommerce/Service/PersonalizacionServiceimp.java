package com.ecommerce.Service;

import com.ecommerce.Model.*;
import com.ecommerce.Repository.IPersonalizacionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;

@Service
@Primary

public class PersonalizacionServiceimp implements IPersonalizacionService {


    @Autowired
    IPersonalizacionRepository personalizacionRepository;

    private List<PersonalizacionModel> personalizacionesExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        personalizacionesExistentes= this.personalizacionRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearPersonalizacion(PersonalizacionModel personalizacion) {
        String textoRespuesta = "";

        UsuarioModel idUsuario = personalizacion.getIdUsuario();
        ArticuloModel idArticulo = personalizacion.getIdArticulo();
        ComentarioModel idComentario = personalizacion.getIdComentario();


        personalizacionesExistentes = this.personalizacionRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(personalizacionesExistentes.isEmpty()){

            this.personalizacionRepository.save(personalizacion);

            textoRespuesta =  "La personalizacion ha sido creado con éxito.";
            System.out.println("Anda entrando aca");

        } else {
            if (idUsuario == null ) {
                textoRespuesta = "el id de su personalizacion no puede ser null.";
            } else if (idArticulo == null) {
                textoRespuesta = "El id de su articulo no puede ser null.";
            } else if (idComentario == null) {
                textoRespuesta = "El id de su comentario no puede ser null.";
            } else {
                this.personalizacionRepository.save(personalizacion);
                textoRespuesta = "La personalizacion ha sido creada con exito";
            }
        }
        return textoRespuesta;
    }


    @Override
    public List<PersonalizacionModel> listarPersonalizacion() {
        return this.personalizacionRepository.findAll();
    }

    @Override
    public Optional<PersonalizacionModel> obtenerPersonalizacionPorId(Integer idPersonalizacion) {
        return this.personalizacionRepository.findById(idPersonalizacion);
    }

    @Override
    public String actualizarPersonalizacionPorId(PersonalizacionModel personalizacion, Integer idPersonalizacion) {
        String textoRespuesta = "";

        // Verificamos si existe para actualizar.
        try {
            Optional<PersonalizacionModel> personalizacionEncontrada = this.personalizacionRepository.findById(idPersonalizacion);

            if (personalizacionEncontrada.isPresent()) {

                PersonalizacionModel personalizacionActualizar = personalizacionEncontrada.get();

                BeanUtils.copyProperties(personalizacion, personalizacionActualizar);

                this.personalizacionRepository.save(personalizacionActualizar);

                return "La personalizacion con código: " + idPersonalizacion + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "La personalizacion con código: " + idPersonalizacion + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
            }
        }catch(NullPointerException e){
            textoRespuesta = "Alguno de los valores son nulos, verifique los campos";
        }catch(UncheckedIOException e){
            textoRespuesta = "Se presento un error, inesperado. Verifique el JSON y los valores no puede ser nulos.";
        }catch(DataIntegrityViolationException e){
            textoRespuesta = "Un error en el JSON, verifique.";
        }

        return textoRespuesta;
    }
}
