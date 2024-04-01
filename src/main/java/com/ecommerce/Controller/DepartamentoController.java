package com.ecommerce.Controller;


import com.ecommerce.Model.DepartamentoModel;
import com.ecommerce.Service.IDepartamentoService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamento")

public class DepartamentoController {

    @Autowired
    IDepartamentoService departamentoService;

    @PostMapping("/save")
    public ResponseEntity<String> crearDepartamento(@RequestBody DepartamentoModel departamento) {
        String resultadoHttp = departamentoService.crearDepartamento(departamento);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<DepartamentoModel>> listarDepartamento(){
        List<DepartamentoModel> departamentos = departamentoService.listarDepartamento();
        return new ResponseEntity<>(departamentos,HttpStatus.OK);
    }

    @GetMapping("/get/{idDepartamento}")
    public ResponseEntity<DepartamentoModel> buscarDepartamentoPorId(@RequestBody @PathVariable Integer idDepartamento) {
        DepartamentoModel departamento = this.departamentoService.obtenerDepartamentoPorId(idDepartamento)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el departamento con el id " + idDepartamento));
        return ResponseEntity.ok(departamento);
    }


    @PutMapping ("/put/{idDepartamento}")
    public ResponseEntity<String> actualizarDepartamentoPorId(@RequestBody DepartamentoModel departamento, @PathVariable Integer idDepartamento) {
        String resultado = this.departamentoService.actualizarDepartamentoPorId(departamento, idDepartamento);
        return ResponseEntity.ok(resultado);
    }

}
