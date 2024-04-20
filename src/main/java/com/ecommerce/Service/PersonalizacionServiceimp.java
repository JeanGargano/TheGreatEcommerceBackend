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

        try {
            UsuarioModel idUsuario = personalizacion.getIdUsuario();
            ArticuloModel idArticulo = personalizacion.getIdArticulo();
            ComentarioModel idComentario = personalizacion.getIdComentario();

            personalizacionesExistentes = this.personalizacionRepository.findAll();

            if (personalizacionesExistentes.isEmpty()) {
                this.personalizacionRepository.save(personalizacion);
                textoRespuesta = "la personalizacion ha sido creada con éxito.";
            } else {
                if (idUsuario == null) {
                    textoRespuesta += "El id de su usario no puede ser nulo no puede ser nula\n";
                }
                if (idArticulo == null) {
                    textoRespuesta += "El ID del articulo no puede ser nulo\n";
                }
                if (idComentario == null) {
                    textoRespuesta += "El ID del comentario no puede ser nulo\n";
                }
                if (!textoRespuesta.isEmpty()) {
                    textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
                } else {
                    this.personalizacionRepository.save(personalizacion);
                    textoRespuesta = "La personalizacion ha sido creado con éxito.";
                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Algún objeto es nulo\n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "Verifique si el usuario, el articulo o el comentario ya se encunetran en la base de datos\n";
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
