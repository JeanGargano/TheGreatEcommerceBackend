package com.ecommerce.Service.Functions;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenModel;

import java.util.List;

public class generarPago {

    public Double generarPago(Integer precioArticulo, Integer cantidadArticulo){

        Double totalPagar = 0.0;

        totalPagar += precioArticulo * cantidadArticulo;

        return totalPagar;

    }








}
