package com.ecommerce.Service;


import com.ecommerce.Model.DepartamentoModel;

import java.util.List;
import java.util.Optional;

public interface IDepartamentoService {

    String crearDepartamento(DepartamentoModel departamento);

    List<DepartamentoModel> listarDepartamento();
    Optional<DepartamentoModel> obtenerDepartamentoPorId(Integer idDepartamento);
    String actualizarDepartamentoPorId(DepartamentoModel departamento, Integer idDepartamento);
}
