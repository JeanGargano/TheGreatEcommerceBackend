package com.ecommerce.Service;

import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Repository.IOrdenRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary

public class IOrdenImp implements IOrdenService {

    @Autowired
    IOrdenRepository ordenRepository;

    private List<OrdenModel> ordenesExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        ordenesExistentes = this.ordenRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }
    @Override
    public String crearOrden(OrdenModel orden) {

        String textoRespuesta = "";

        ordenesExistentes = this.ordenRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(ordenesExistentes.isEmpty()){

            this.ordenRepository.save(orden);

            textoRespuesta =  "La orden ha sido creado con éxito.";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (OrdenModel i : ordenesExistentes) {
                if (orden.getIdOrden().equals(i.getIdOrden())) {

                    textoRespuesta = "La Orden con ID: " + orden.getIdOrden() + ", Ya se encuentra creada.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.ordenRepository.save(orden);

                    textoRespuesta = "La orden ha sido creada con éxito.";
                }
            }
        }
        return textoRespuesta;
    }

    @Override
    public List<OrdenModel> listarOrden() {
        return this.ordenRepository.findAll();
    }

    @Override
    public Optional<OrdenModel> obtenerOrdenPorId(Integer idOrden) {
        return this.ordenRepository.findById(idOrden);
    }

    @Override
    public String actualizarOrdenPorId(OrdenModel orden, Integer idOrden) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<OrdenModel> ordenEncontrada = this.ordenRepository.findById(idOrden);

        if(ordenEncontrada.isPresent()){

            OrdenModel ordenActualizar = ordenEncontrada.get();

            BeanUtils.copyProperties(orden, ordenActualizar);

            this.ordenRepository.save(ordenActualizar);

            return "La orden con id: " + idOrden + ", Ha sido actualizada con éxito.";

        }else{

            textoRespuesta = "La orden con id: "+ idOrden + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
    }

