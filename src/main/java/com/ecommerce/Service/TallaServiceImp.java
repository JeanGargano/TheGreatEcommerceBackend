package com.ecommerce.Service;


import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.Enums.Talla;
import com.ecommerce.Model.TallaModel;

import com.ecommerce.Repository.ITallaRepository;
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

        Talla tallaP = talla.getTalla();

        tallasExistentes = this.tallaRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if (tallasExistentes.isEmpty()) {

            this.tallaRepository.save(talla);

            textoRespuesta = "La talla ha sido creado con éxito.";
            System.out.println("Anda entrando aca");

        } else {
            if (tallaP == null) {
                textoRespuesta = "La talla no puede estar vacia o ser nula";
            } else {
                this.tallaRepository.save(talla);
                textoRespuesta = "La talla ha sido creado con éxito.";
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
        try {
            Optional<TallaModel> tallEncontrada = this.tallaRepository.findById(idTalla);

            if (tallEncontrada.isPresent()) {

                TallaModel tallaActualizar = tallEncontrada.get();

                BeanUtils.copyProperties(talla, tallaActualizar);

                this.tallaRepository.save(tallaActualizar);

                return "La talla con código: " + idTalla + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "La talla con código: " + idTalla + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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

