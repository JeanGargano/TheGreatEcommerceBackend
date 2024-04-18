package com.ecommerce.Service;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.Enums.EstadoPersonalizacion;
import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Model.OrdenPersonalizacionModel;
import com.ecommerce.Model.PersonalizacionModel;
import com.ecommerce.Repository.IOrdenPersonalizacionRepository;
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

        OrdenModel idOrden = ordenPersonalizacion.getIdOrden();
        PersonalizacionModel idPersonalizacion = ordenPersonalizacion.getIdPersonalizacion();
        EstadoPersonalizacion estado = ordenPersonalizacion.getEstado();
        String reciboPago = ordenPersonalizacion.getReciboPago();


        ordenesPersonalizacionesExistentes = this.ordenPersonalizacionRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(ordenesPersonalizacionesExistentes.isEmpty()){

            this.ordenPersonalizacionRepository.save(ordenPersonalizacion);

            textoRespuesta =  "La orden de personalizacion ha sido creada con éxito.";
            System.out.println("Anda entrando aca");

        } else {
            if (idOrden == null ) {
                textoRespuesta = "el id de la orden no puede ser nulo";
            } else if (idPersonalizacion == null) {
                textoRespuesta = "el id de la personalizacion no puede ser nulo";
            } else if (estado == null) {
                textoRespuesta = "El estado no puede ser nulo";
            } else if (reciboPago == null || reciboPago.isBlank()) {
                textoRespuesta = "El recibo de pago no puede ser nulo o estar vacio";
            } else {
                this.ordenPersonalizacionRepository.save(ordenPersonalizacion);
                textoRespuesta = "El articulo ha sido creado con éxito.";
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
        try {
            Optional<OrdenPersonalizacionModel> ordenPersonalizacionEncontrada = this.ordenPersonalizacionRepository.findById(idOrdenPersonalizacion);

            if (ordenPersonalizacionEncontrada.isPresent()) {

                OrdenPersonalizacionModel ordenPersonalizacionActualizar = ordenPersonalizacionEncontrada.get();

                BeanUtils.copyProperties(ordenPersonalizacion, ordenPersonalizacionActualizar);

                this.ordenPersonalizacionRepository.save(ordenPersonalizacion);

                return "La orden de personalizacion con código: " + idOrdenPersonalizacion + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "La orden de personalizacion con código: " + idOrdenPersonalizacion + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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
