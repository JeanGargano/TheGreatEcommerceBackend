package com.ecommerce.Service;

import com.ecommerce.Model.*;
import com.ecommerce.Repository.IEnvioRepository;
import com.ecommerce.Repository.IOrdenArticuloRepository;
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
    @Autowired
    IEnvioRepository envioRepository;
    @Autowired
    IOrdenArticuloRepository ordenArticuloRepository;

    private List<OrdenModel> ordenesExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init() { // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        ordenesExistentes = this.ordenRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearOrden(OrdenModel orden) {

        String textoRespuesta = "";
        try {

            String fecha = orden.getFecha();
            Double valorTotal = orden.getValorTotal();
            UsuarioModel idUsuario = orden.getIdUsuario();

            ordenesExistentes = this.ordenRepository.findAll();

            if (ordenesExistentes.isEmpty()) {
                this.ordenRepository.save(orden);
                textoRespuesta = "La orden ha sido creada con éxito.";
            } else {
                if (fecha == null || fecha.isBlank()) {
                    textoRespuesta += "La fecha no puede ser nula o estar vacia\n";
                }
                if (valorTotal == null) {
                    textoRespuesta += "El Valor Totalno puede ser nulo o estar vacio\n";

                }
                if (idUsuario == null) {
                    textoRespuesta += "El id de Usuario no puede ser nulo o estar vacio\n";
                }
                if (!textoRespuesta.isEmpty()) {
                    textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
                } else {
                    this.ordenRepository.save(orden);
                    textoRespuesta = "La orden ha sido creada con éxito.";


                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Algún objeto es nulo\n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "verifique que el usuario este creado en la base de datos\n";
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
        } catch (NullPointerException e) {
            textoRespuesta = "Alguno de los valores son nulos, verifique los campos";
        } catch (UncheckedIOException e) {
            textoRespuesta = "Se presento un error, inesperado. Verifique el JSON y los valores no puede ser nulos.";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta = "Un error en el JSON, verifique.";
        }

        return textoRespuesta;
    }

    @Override
    public Optional<String> listarInformacion(Integer idOrden) {

        String textoRespuesta = "";
        //Optional<OrdenArticuloModel> ordenArticulo = this.ordenArticuloRepository.findById(idOrden);
        Optional<EnvioModel> envioEncontrado = this.envioRepository.findById(idOrden);
        Optional<OrdenModel>  ordenEncontrada = this.ordenRepository.findById(idOrden);
        if (ordenEncontrada.isPresent()) {

            String fecha = ordenEncontrada.get().getFecha();
            Double valorTotal = ordenEncontrada.get().getValorTotal();
            String nombreCliente = ordenEncontrada.get().getIdUsuario().getNombre();
            Integer identificacion = ordenEncontrada.get().getIdUsuario().getIdentificacion();
            String direccion = envioEncontrado.get().getDireccion();
            String tipoEntrega = envioEncontrado.get().getTipoEntrega();
            textoRespuesta = "Fecha: " + fecha + "\n" +
                    "Valor: " + valorTotal + "\n" +
                    "Nombre: " + nombreCliente + "\n" +
                    "ID: " + identificacion + "\n" +
                    "Direccion: " + direccion + "\n" +
                    "Tipo de entrega: " + tipoEntrega + "\n";

        }
        return textoRespuesta.describeConstable();


    }

}
