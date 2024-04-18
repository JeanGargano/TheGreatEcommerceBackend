package com.ecommerce.Service;



import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.CategoriaModel;
import com.ecommerce.Model.CiudadModel;
import com.ecommerce.Repository.ICiudadRepository;
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




public class CiudadServiceImp implements ICiudadService {

    @Autowired
    ICiudadRepository ciudadRepository;


    private List<CiudadModel> CiudadesExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        CiudadesExistentes = this.ciudadRepository.findAll();// Aca toma todas los articulos de la BD y las mete en el List.

    }


    @Override
    public String crearCiudad(CiudadModel ciudad) {

        String textoRespuesta = "";

        String nombre = ciudad.getNombre();


        CiudadesExistentes = this.ciudadRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(CiudadesExistentes.isEmpty()){

            this.ciudadRepository.save(ciudad);

            textoRespuesta =  "La ciudad ha sido creado con éxito.";
            System.out.println("Anda entrando aca");

        } else {
            if (nombre == null || nombre.isBlank()) {
                textoRespuesta = "El nombre de la ciudad no puede estar vacio o ser nulo";
            } else {
                this.ciudadRepository.save(ciudad);
                textoRespuesta = "La ciudad ha sido creado con éxito.";
            }
        }
        return textoRespuesta;
    }



    @Override
    public List<CiudadModel> listarCiudad() {
        return this.ciudadRepository.findAll();
    }

    @Override
    public Optional<CiudadModel> obtenerCiudadPorId(Integer idCiudad) {
        return this.ciudadRepository.findById(idCiudad);
    }

    @Override
    public String actualizarCiudadPorId(CiudadModel ciudad, Integer idCiudad) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.
        try {
            Optional<CiudadModel> ciudadEncontrada = this.ciudadRepository.findById(idCiudad);

            if (ciudadEncontrada.isPresent()) {

                CiudadModel ciudadActualizar = ciudadEncontrada.get();

                BeanUtils.copyProperties(ciudad, ciudadActualizar);

                this.ciudadRepository.save(ciudadActualizar);

                return "La ciudad con código: " + idCiudad + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "La ciudad con código: " + idCiudad + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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
