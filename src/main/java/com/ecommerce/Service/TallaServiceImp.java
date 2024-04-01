package com.ecommerce.Service;


import com.ecommerce.Model.TallaModel;

import com.ecommerce.Repository.ITallaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary

public class TallaServiceImp implements ITallaService {

    @Autowired
    ITallaRepository tallaRepository;

    private List<TallaModel> tallasExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        tallasExistentes= this.tallaRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearTalla(TallaModel talla) {

        String textoRespuesta = "";

        tallasExistentes = this.tallaRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(tallasExistentes.isEmpty()){

            this.tallaRepository.save(talla);

            textoRespuesta =  "El talla ha sido creada con éxito";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (TallaModel i : tallasExistentes) {
                if (talla.getIdTalla().equals(i.getIdTalla())) {

                    textoRespuesta = "La talla con ID: " + talla.getIdTalla() + ", Ya se encuentra creada.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.tallaRepository.save(talla);

                    textoRespuesta = "La talla ha sido creada con éxito";
                }
            }
        }
        return textoRespuesta;
    }

    @Override
    public List<TallaModel> listarTalla() {
        return this.tallaRepository.findAll();
    }

    @Override
    public Optional<TallaModel> obtenerTallaPorId(Integer idTalla) {
        return this.tallaRepository.findById(idTalla);
    }

    @Override
    public String actualizarTallaPorId(TallaModel talla, Integer idTalla) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<TallaModel> tallaEncontrada = this.tallaRepository.findById(idTalla);

        if(tallaEncontrada.isPresent()){

            TallaModel tallaActualizar = tallaEncontrada.get();

            BeanUtils.copyProperties(talla, tallaActualizar);

            this.tallaRepository.save(tallaActualizar);

            return "La talla con id: " + idTalla + ", Ha sido actualizada con exito.";

        }else{

            textoRespuesta = "La talla con id: "+ idTalla + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
}
