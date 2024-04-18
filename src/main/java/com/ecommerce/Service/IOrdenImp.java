package com.ecommerce.Service;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Repository.IOrdenRepository;
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

        String fecha = orden.getFecha();
        Double valorTotal = orden.getValorTotal();
        UsuarioModel idUsuario = orden.getIdUsuario();

        ordenesExistentes = this.ordenRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(ordenesExistentes.isEmpty()){

            this.ordenRepository.save(orden);

            textoRespuesta =  "La orden ha sido creada con éxito.";
            System.out.println("Anda entrando aca");

        } else {
            if (fecha == null || fecha.isBlank()) {
                textoRespuesta = "La fecha no puede estar vacia o ser nula";
            } else if (valorTotal == null) {
                textoRespuesta = "El valor total no puede ser nulo";
            } else if (idUsuario == null) {
                textoRespuesta = "El id de su usuario no puede ser nulo";
            } else {
                this.ordenRepository.save(orden);
                textoRespuesta = "La orden ha sido creado con éxito.";
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
        try {
            Optional<OrdenModel> ordenEncontrada = this.ordenRepository.findById(idOrden);

            if (ordenEncontrada.isPresent()) {

                OrdenModel ordenActualizar = ordenEncontrada.get();

                BeanUtils.copyProperties(orden, ordenActualizar);

                this.ordenRepository.save(ordenActualizar);

                return "La orden con código: " + idOrden + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "La orden con código: " + idOrden + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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

