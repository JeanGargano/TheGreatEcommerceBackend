package com.ecommerce.Repository.Functions;
import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenArticuloModel;
import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Repository.IArticuloRepository;
import com.ecommerce.Repository.IOrdenArticuloRepository;
import com.ecommerce.Service.ArticuloServiceImp;
import com.ecommerce.Service.OrdenArticuloServiceImp;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import com.ecommerce.Model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

@NoArgsConstructor
public class restarCantidad {





    public String actualizarCantidadEnBD(OrdenArticuloModel ordenArticulo, ArticuloModel articulo) {

        String textoRespuesta = "";
        Integer cantidadTotal = 0;



        System.out.println("Entro a la función");

        System.out.println(articulo.toString());
        try{
            System.out.println("Entro al try");


            Integer cantidadArticulo = articulo.getCantidad();
            System.out.println("cantidad mela de articulo" + cantidadArticulo);
            Integer cantidadOrden = ordenArticulo.getCantidad();
            System.out.println("..." + cantidadOrden);



            if(cantidadArticulo <=0){
                textoRespuesta = "No hay stock disponble para el articulo. ";
            }else if(cantidadOrden > cantidadArticulo){
                textoRespuesta = "La cantidad de la orden no puede superar a la del articulo";
            }else{
                cantidadTotal = cantidadArticulo - cantidadOrden;

                articulo.setCantidad(cantidadTotal);
                System.out.println("viva messi" + articulo.getCantidad());

                ArticuloServiceImp objA = new ArticuloServiceImp();

                objA.crearArticulo(articulo);

                textoRespuesta = "Ok";
            }

        }catch (NullPointerException e){
            textoRespuesta = "El producto no existe" + e.getLocalizedMessage();
        }catch (DataIntegrityViolationException e){
            textoRespuesta = "Un problema en el JSON, verifique";
        }
        return textoRespuesta;

    }
}
