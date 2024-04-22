package com.ecommerce.Repository.Functions;
import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenArticuloModel;
import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Repository.IArticuloRepository;
import com.ecommerce.Repository.IOrdenArticuloRepository;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class restarCantidad {

    IOrdenArticuloRepository ordenArticuloRepository;
    IArticuloRepository articuloRepository;

    public void restarCantidad(){
        int cantidad = 0;
        if (cantidad > 0) {

            actualizarCantidadEnBD(); // Actualizar la cantidad en la base de datos

            if (cantidad == 0) {
                System.out.println("¡El producto está agotado!");
            }
        } else {
            System.out.println("¡El producto está agotado!");
        }

    }

    private String actualizarCantidadEnBD(Integer idOrden, Integer idArticulo) {

        String textoRespuesta = "";
        Integer cantidadTotal;
        try{

            Optional<OrdenArticuloModel> orden = ordenArticuloRepository.findById(idOrden);
            Optional<ArticuloModel> articulo = articuloRepository.findById(idArticulo);

            OrdenArticuloModel ordenArticulo = orden.get();
            ArticuloModel articuloModel = articulo.get();

            Integer cantidadOrden = ordenArticulo.getCantidad();
            Integer cantidadArticulo = articuloModel.getCantidad();

            if(cantidadArticulo <=0){
                textoRespuesta = "No hay stock disponble para el articulo: " + idArticulo;
            }else if(cantidadOrden > cantidadArticulo){
                textoRespuesta = "La cantidad de la orden no puede superar a la del articulo";
            }else{
                //cantidadTotal = cantidadArticulo - cantidadOrden;

                // articuloModel.setCantidad(cantidadTotal);

                articuloRepository.save(articulo.get());

                textoRespuesta = "Ok";
            }

        }catch (NullPointerException e){
            textoRespuesta = "El producto no existe";
        }catch (DataIntegrityViolationException e){
            textoRespuesta = "Un problema en el JSON, verifique";
        }
        return textoRespuesta;

    }
}
