package com.ecommerce.Service;

import com.ecommerce.Model.DisenioModel;
import com.ecommerce.Repository.IDisenioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary

public class DisenioServiceImp implements IDisenioService{

    @Autowired
    IDisenioRepository disenioRepository;

    private List<DisenioModel> diseniosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        diseniosExistentes= this.disenioRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearDisenio(DisenioModel diseno) {

        String textoRespuesta = "";

        diseniosExistentes = this.disenioRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(diseniosExistentes.isEmpty()){

            this.disenioRepository.save(diseno);

            textoRespuesta =  "El disenio ha sido creado con éxito";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (DisenioModel i : diseniosExistentes) {
                if (diseno.getIdDisenio().equals(i.getIdDisenio())) {

                    textoRespuesta = "El diseño con ID: " + diseno.getIdDisenio() + ", Ya se encuentra creado.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.disenioRepository.save(diseno);

                    textoRespuesta = "El disenio ha sido creado con éxito";
                }
            }
        }
        return textoRespuesta;
    }

    @Override
    public List<DisenioModel> listarDisenio() {
        return this.disenioRepository.findAll();
    }

    @Override
    public Optional<DisenioModel> obtenerDisenioPorId(Integer idDisenio) {
        return this.disenioRepository.findById(idDisenio);
    }

    @Override
    public String actualizarDisenioPorId(DisenioModel disenio, Integer idDisenio) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<DisenioModel> disenioEncontrado = this.disenioRepository.findById(idDisenio);

        if(disenioEncontrado.isPresent()){

            DisenioModel disenioActualizar = disenioEncontrado.get();

            BeanUtils.copyProperties(disenio, disenioActualizar);

            this.disenioRepository.save(disenioActualizar);

            return "El diseño con id: " + idDisenio + ", Ha sido actualizado con exito.";

        }else{

            textoRespuesta = "El diseño con id: "+ idDisenio + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
}
