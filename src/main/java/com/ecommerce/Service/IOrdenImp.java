package com.ecommerce.Service;

import com.ecommerce.Model.*;
import com.ecommerce.Model.Dto.OrdenModelDTO;
import com.ecommerce.Model.Enums.Estado;
import com.ecommerce.Repository.IArticuloRepository;
import com.ecommerce.Repository.IOrdenRepository;
import com.ecommerce.Service.Functions.generarPago;
import com.ecommerce.exception.RecursoNoEncontradoException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Primary

public class IOrdenImp implements IOrdenService {

    @Autowired
    IOrdenRepository ordenRepository;

    @Autowired
    IArticuloService articuloService;

    @Autowired
    IArticuloRepository articuloRepository;

    private List<OrdenModel> ordenesExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init() { // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        ordenesExistentes = this.ordenRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearOrden(OrdenModelDTO orden) {
        List<ArticuloModel> articulos = orden.getIdArticulo();
        StringBuilder textoRespuesta = new StringBuilder();

        // Validaciones de la orden
        if (orden.getFecha() == null || orden.getFecha().isBlank()) {
            textoRespuesta.append("La fecha no puede ser nula o estar vacía\n");
        }
        if (orden.getDireccion() == null || orden.getDireccion().isBlank()) {
            textoRespuesta.append("La dirección no puede estar vacía, verifique.\n");
        }
        if (orden.getIdDepartamento() == null || orden.getIdDepartamento().getIdDepartamento() < 0) {
            textoRespuesta.append("La id del departamento no puede ser nula o menor a 0\n");
        }
        if (orden.getTipoEntrega() == null || orden.getTipoEntrega().isBlank()) {
            textoRespuesta.append("El tipo de entrega no puede estar vacío\n");
        }
        if (orden.getIdUsuario() == null) {
            textoRespuesta.append("El id de Usuario no puede ser nulo o estar vacío\n");
        }

        // Validaciones de los artículos
        for (ArticuloModel articulo : articulos) {
            if (articulo.getIdArticulo() == null || articulo.getIdArticulo() < 0) {
                textoRespuesta.append("El id del Articulo no puede ser nulo o menor a 0\n");
            }
            if (articulo.getCantidad() == null || articulo.getCantidad() < 0) {
                textoRespuesta.append("La cantidad no puede ser nula o menor a 0\n");
            } else {
                Optional<ArticuloModel> datosArticulo = articuloRepository.findById(articulo.getIdArticulo());
                if (datosArticulo.isPresent()) {
                    ArticuloModel datosA = datosArticulo.get();
                    String nombreArticulo = datosA.getNombre();
                    if (articulo.getCantidad() > datosA.getCantidad()) {
                        textoRespuesta.append("La orden no se puede crear debido a que la cantidad del articulo con nombre " )
                                .append(nombreArticulo).append(" supera a la del stock \n");
                    }
                } else {
                    textoRespuesta.append("El artículo con ID ").append(articulo.getIdArticulo()).append(" no existe\n");
                }
            }
        }

        // Si hay errores en las validaciones, retornar los mensajes de error
        if (textoRespuesta.length() > 0) {
            return textoRespuesta.toString();
        }

        // Si no hay errores, proceder con la creación de la orden
        double totalPagar = 0.0;
        for (ArticuloModel articulo : articulos) {
            Optional<ArticuloModel> datosArticulo = articuloRepository.findById(articulo.getIdArticulo());
            ArticuloModel datosA = datosArticulo.get();
            int precioArticulo = datosA.getPrecio();
            int cantidad = articulo.getCantidad();

            // Calcular el total a pagar por artículo
            generarPago objGP = new generarPago();
            double totalPagarArticulo = objGP.generarPago(precioArticulo, cantidad);
            totalPagar += totalPagarArticulo;

            // Crear y guardar la orden
            OrdenModel objO = new OrdenModel();
            objO.setFecha(orden.getFecha());
            objO.setDireccion(orden.getDireccion());
            objO.setIdDepartamento(orden.getIdDepartamento());
            objO.setTipoEntrega(orden.getTipoEntrega());
            objO.setIdUsuario(orden.getIdUsuario());
            objO.setEstado(orden.getEstado());
            objO.setIdArticulo(articulo.getIdArticulo());
            objO.setCantidad(cantidad);
            objO.setValorTotal(totalPagarArticulo);

            // Actualizar la cantidad en el stock
            articuloService.actualizarCantidadEnBd(objO, articulo);
            this.ordenRepository.save(objO);
        }

        // Retornar mensaje de éxito
        return "La orden ha sido creada con éxito. El total a pagar es de: $" + totalPagar + " COP";
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

        Optional<OrdenModel>  ordenEncontrada = this.ordenRepository.findById(idOrden);
        if (ordenEncontrada.isPresent()) {

            String fecha = ordenEncontrada.get().getFecha();
            Double valorTotal = ordenEncontrada.get().getValorTotal();
            String nombreCliente = ordenEncontrada.get().getIdUsuario().getNombre();
            Integer identificacion = ordenEncontrada.get().getIdUsuario().getIdentificacion();
            String direccion = ordenEncontrada.get().getDireccion();
            String tipoEntrega = ordenEncontrada.get().getTipoEntrega();
            textoRespuesta = "Fecha: " + fecha + "\n" +
                    "Valor: " + valorTotal + "\n" +
                    "Nombre: " + nombreCliente + "\n" +
                    "ID: " + identificacion + "\n" +
                    "Direccion: " + direccion + "\n" +
                    "Tipo de entrega: " + tipoEntrega + "\n";

        }
        return textoRespuesta.describeConstable();


    }

    @Override
    public List<OrdenModel> ordenarOrden(){


        List<OrdenModel> ordenesEncontrada = ordenRepository.findAll();


        //ordenesEncontrada.sort((o1, o2) -> o2.getFecha().compareTo(o1.getFecha()));

        return ordenesEncontrada;
    }

    public List<OrdenModel> paginacionOrdenes(String año) {
        List<OrdenModel> ordenesEncontradas = ordenRepository.findAll();
        List<OrdenModel> ordenesFiltradas = new ArrayList<>();

        for (OrdenModel orden : ordenesEncontradas) {
            String fecha = orden.getFecha();
            if (fecha != null && fecha.startsWith(año)) {
                ordenesFiltradas.add(orden);

            }
        }
        return ordenesFiltradas;
    }



    }




