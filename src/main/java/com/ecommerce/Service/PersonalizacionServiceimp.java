package com.ecommerce.Service;

import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Model.PersonalizacionModel;
import com.ecommerce.Repository.IPersonalizacionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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

        personalizacionesExistentes = this.personalizacionRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(personalizacionesExistentes.isEmpty()){

            this.personalizacionRepository.save(personalizacion);

            textoRespuesta =  "La personalizacion ha sido creada con exito";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (PersonalizacionModel i : personalizacionesExistentes) {
                if (personalizacion.getIdPersonalizacion().equals(i.getIdPersonalizacion())) {

                    textoRespuesta = "La personalizacion con ID: " + personalizacion.getIdPersonalizacion() + ", Ya se encuentra creada.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.personalizacionRepository.save(personalizacion);

                    textoRespuesta = "La personalizacion ha sido creada con exito";
                }
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

        Optional<PersonalizacionModel> personalizacionEncontrada = this.personalizacionRepository.findById(idPersonalizacion);

        if(personalizacionEncontrada.isPresent()){

            PersonalizacionModel personalizacionActualizar = personalizacionEncontrada.get();

            BeanUtils.copyProperties(personalizacion, personalizacionActualizar);

            this.personalizacionRepository.save(personalizacionActualizar);

            return "La personalizacion con id: " + idPersonalizacion + ", Ha sido actualizada con exito.";

        }else{

            textoRespuesta = "La personalizacion con id: "+ idPersonalizacion + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
}
