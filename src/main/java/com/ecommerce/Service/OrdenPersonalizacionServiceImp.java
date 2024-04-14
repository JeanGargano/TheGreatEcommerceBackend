package com.ecommerce.Service;

import com.ecommerce.Model.OrdenPersonalizacionModel;
import com.ecommerce.Repository.IOrdenPersonalizacionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary

public class OrdenPersonalizacionServiceImp implements IOrdenPersonalizacionService {

    @Autowired
    IOrdenPersonalizacionRepository ordenPersonalizacionRepository;

    private List<OrdenPersonalizacionModel> ordenesPersonalizacionesExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        ordenesPersonalizacionesExistentes = this.ordenPersonalizacionRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearOrdenPersonalizacion(OrdenPersonalizacionModel ordenPersonalizacion) {

        String textoRespuesta = "";

        ordenesPersonalizacionesExistentes = this.ordenPersonalizacionRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(ordenesPersonalizacionesExistentes.isEmpty()){

            this.ordenPersonalizacionRepository.save(ordenPersonalizacion);

            textoRespuesta =  "La orden de personalizacion ha sido creada con éxito";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (OrdenPersonalizacionModel i : ordenesPersonalizacionesExistentes) {
                if (ordenPersonalizacion.getIdOrdenPersonalizacion().equals(i.getIdOrdenPersonalizacion())) {

                    textoRespuesta = "La orden de personalizacion con ID: " + ordenPersonalizacion.getIdPersonalizacion() + ", Ya se encuentra creada.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.ordenPersonalizacionRepository.save(ordenPersonalizacion);

                    textoRespuesta = "La orden de personalizacion ha sido creado con éxito";
                }
            }
        }
        return textoRespuesta;
    }

    @Override
    public List<OrdenPersonalizacionModel> listarOrdenPersonalizacion() {
        return this.ordenPersonalizacionRepository.findAll();
    }

    @Override
    public Optional<OrdenPersonalizacionModel> obtenerOrdenPersonalizacionPorId(Integer idOrdenPersonalizacion) {
        return this.ordenPersonalizacionRepository.findById(idOrdenPersonalizacion);
    }

    @Override
    public String actualizarOrdenPersonalizacionPorId(OrdenPersonalizacionModel ordenPersonalizacion, Integer idOrdenPersonalizacion) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<OrdenPersonalizacionModel> ordenesPersonalizacionEncontradas = this.ordenPersonalizacionRepository.findById(idOrdenPersonalizacion);

        if(ordenesPersonalizacionEncontradas.isPresent()){

            OrdenPersonalizacionModel ordenPersonalizacionActualizar = ordenesPersonalizacionEncontradas.get();

            BeanUtils.copyProperties(ordenPersonalizacion, ordenPersonalizacionActualizar);

            this.ordenPersonalizacionRepository.save(ordenPersonalizacionActualizar);

            return "La orden de personalizacion con id: " + ordenPersonalizacion.getIdPersonalizacion() + ", Ha sido actualizada con exito.";

        }else{

            textoRespuesta = "La orden de personalizacion con id: "+ idOrdenPersonalizacion + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
}
