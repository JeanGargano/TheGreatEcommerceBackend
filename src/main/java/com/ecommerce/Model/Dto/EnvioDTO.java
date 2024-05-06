package com.ecommerce.Model.Dto;


import com.ecommerce.Model.DepartamentoModel;
import com.ecommerce.Model.OrdenModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EnvioDTO {

    private String direccion;
    private DepartamentoModel idDepartamento;
    private String tipoEntrega;
    private OrdenModel idOrden;

}
