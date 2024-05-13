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

        List<ArticuloModel> articulos;

        articulos = orden.getIdArticulo();

        String textoRespuesta = "";
        try {

            for(ArticuloModel i: articulos){

                if (i.getIdArticulo() == null || i.getIdArticulo() < 0) {

                    textoRespuesta = "El id del Articulo no puede ser nulo ó ser menor a 0\n";


                }
                else if (i.getCantidad() == null || i.getCantidad() < 0) {
                    textoRespuesta = "La cantidad no puede ser nula ó menor a 0\n";


                }

            }

            String fecha = orden.getFecha();
            String direccion = orden.getDireccion();
            DepartamentoModel idDepartamento = orden.getIdDepartamento();
            String tipoEntrega = orden.getTipoEntrega();
            UsuarioModel idUsuario = orden.getIdUsuario();
            Estado estado = orden.getEstado();

            if (fecha == null || fecha.isBlank()) {
                textoRespuesta += "La fecha no puede ser nula o estar vacia\n";

            }if (direccion == null || direccion.isBlank()){

                textoRespuesta += "La dirección no puede estar vacia, verifique.";
            }if(idDepartamento == null || idDepartamento.getIdDepartamento() < 0){

                textoRespuesta += "La id Del departamento no puede ser nula";

            }if(tipoEntrega == null || tipoEntrega.isBlank()){

                textoRespuesta += "El tipo de entrega no puede estar vacio";
            }
            if (idUsuario == null) {
                textoRespuesta += "El id de Usuario no puede ser nulo o estar vacio\n";
            }


            ordenesExistentes = this.ordenRepository.findAll();

            if (ordenesExistentes.isEmpty()) {

                Integer idArticulo = 0;
                Integer cantidad = 0;
                Integer precioArticulo = 0;
                Double totalPagarArticulo = 0.0;
                Double totalPagar = 0.0;


                if(articulos.size() == 1) {


                    OrdenModel objO = new OrdenModel();




                    objO.setFecha(fecha);
                    objO.setDireccion(direccion);
                    objO.setIdDepartamento(idDepartamento);
                    objO.setTipoEntrega(tipoEntrega);
                    objO.setIdUsuario(idUsuario);
                    objO.setEstado(estado);

                    for (int i = 0; i < articulos.size(); i++) {



                        ArticuloModel objArticulo = articulos.get(i);
                        idArticulo = objArticulo.getIdArticulo();
                        Optional<ArticuloModel> datosArticulo = articuloRepository.findById(idArticulo);
                        ArticuloModel datosA = datosArticulo.get();

                        cantidad = objArticulo.getCantidad();
                        precioArticulo = datosA.getPrecio();
                        objO.setIdArticulo(idArticulo);
                        objO.setCantidad(cantidad);

                        generarPago objGP = new generarPago();
                        totalPagarArticulo = objGP.generarPago(precioArticulo, cantidad);
                        totalPagar += totalPagarArticulo;
                        objO.setValorTotal(totalPagarArticulo);
                        this.articuloService.actualizarCantidadEnBd(objO, objArticulo);
                        this.ordenRepository.save(objO);



                        break;
                    }
                    textoRespuesta = "La orden ha sido creada con éxito.";
                    System.out.println("El total a pagar es de: $" + totalPagar + " COP");
                }else if(articulos.size() == 0){
                    textoRespuesta += "Articulos no seleccionados.";
                }else{



                    for (int i = 0; i < articulos.size(); i++) {

                        OrdenModel objO = new OrdenModel();

                        ArticuloModel objArticulo = articulos.get(i);
                        idArticulo = objArticulo.getIdArticulo();
                        cantidad = objArticulo.getCantidad();

                        Optional<ArticuloModel> datosArticulo = articuloRepository.findById(idArticulo);
                        ArticuloModel datosA = datosArticulo.get();

                        objO.setIdArticulo(idArticulo);
                        objO.setCantidad(cantidad);
                        objO.setFecha(fecha);

                        objO.setDireccion(direccion);
                        objO.setIdDepartamento(idDepartamento);
                        objO.setTipoEntrega(tipoEntrega);
                        objO.setIdUsuario(idUsuario);
                        objO.setEstado(estado);
                        precioArticulo = datosA.getPrecio();
                        generarPago objGP = new generarPago();
                        totalPagarArticulo = objGP.generarPago(precioArticulo, cantidad);
                        totalPagar += totalPagarArticulo;
                        objO.setValorTotal(totalPagarArticulo);
                        this.articuloService.actualizarCantidadEnBd(objO, objArticulo);
                        this.ordenRepository.save(objO);



                    }
                    textoRespuesta = "La orden ha sido creada con éxito.";
                    System.out.println("El total a pagar es de: $" + totalPagar + " COP");

                }
            } else {
                if (!textoRespuesta.isEmpty()) {
                    textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
                } else {
                    Integer idArticulo = 0;
                    Integer cantidad = 0;
                    Integer precioArticulo = 0;
                    Double totalPagarArticulo = 0.0;
                    Double totalPagar = 0.0;

                    if(articulos.size() == 1) {


                        OrdenModel objO = new OrdenModel();

                        objO.setFecha(fecha);
                        objO.setDireccion(direccion);
                        objO.setIdDepartamento(idDepartamento);
                        objO.setTipoEntrega(tipoEntrega);
                        objO.setIdUsuario(idUsuario);
                        objO.setEstado(estado);

                        for (int i = 0; i < articulos.size(); i++) {

                            ArticuloModel objArticulo = articulos.get(i);
                            idArticulo = objArticulo.getIdArticulo();

                            Optional<ArticuloModel> datosArticulo = articuloRepository.findById(idArticulo);
                            ArticuloModel datosA = datosArticulo.get();

                            cantidad = objArticulo.getCantidad();
                            precioArticulo = datosA.getPrecio();

                            objO.setIdArticulo(idArticulo);
                            objO.setCantidad(cantidad);
                            generarPago objGP = new generarPago();
                            totalPagarArticulo = objGP.generarPago(precioArticulo, cantidad);
                            objO.setValorTotal(totalPagarArticulo);
                            totalPagar += totalPagarArticulo;
                            this.articuloService.actualizarCantidadEnBd(objO, objArticulo);
                            this.ordenRepository.save(objO);


                            break;
                        }
                        textoRespuesta = "La orden ha sido creada con éxito.";
                        System.out.println("El total a pagar es de: $" + totalPagar + " COP");
                    }else if(articulos.size() == 0){
                        textoRespuesta += "Articulos no seleccionados.";
                    }else{



                        for (int i = 0; i < articulos.size(); i++) {

                            OrdenModel objO = new OrdenModel();

                            ArticuloModel objArticulo = articulos.get(i);
                            idArticulo = objArticulo.getIdArticulo();

                            Optional<ArticuloModel> datosArticulo = articuloRepository.findById(idArticulo);
                            ArticuloModel datosA = datosArticulo.get();

                            cantidad = objArticulo.getCantidad();
                            System.out.println("la cantidad es" + cantidad);
                            objO.setIdArticulo(idArticulo);
                            objO.setCantidad(cantidad);
                            objO.setFecha(fecha);
                            objO.setDireccion(direccion);
                            objO.setIdDepartamento(idDepartamento);
                            objO.setTipoEntrega(tipoEntrega);
                            objO.setIdUsuario(idUsuario);
                            objO.setEstado(estado);
                            precioArticulo = datosA.getPrecio();
                            generarPago objGP = new generarPago();
                            totalPagarArticulo = objGP.generarPago(precioArticulo, cantidad);
                            totalPagar += totalPagarArticulo;
                            objO.setValorTotal(totalPagarArticulo);
                            this.articuloService.actualizarCantidadEnBd(objO, objArticulo);
                            this.ordenRepository.save(objO);



                        }
                        textoRespuesta = "La orden ha sido creada con éxito.";
                        System.out.println("El total a pagar es de: $" + totalPagar + " COP");

                    }

                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Algún objeto es nulo\n";
            System.out.println(("message error: " + e.getLocalizedMessage()));

        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            System.out.println(orden.toString());
            System.out.println("Message error:" + e.getLocalizedMessage());
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


        ordenesEncontrada.sort((o1, o2) -> o2.getFecha().compareTo(o1.getFecha()));

        return ordenesEncontrada;
    };

}
