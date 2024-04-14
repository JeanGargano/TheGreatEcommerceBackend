package com.ecommerce.Controller;

import com.ecommerce.Model.CiudadModel;
import com.ecommerce.Service.ICiudadService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ciudad")

public class CiudadController {

    @Autowired
    ICiudadService ciudadService;

    @PostMapping("/save")
    public ResponseEntity<String> crearCiudad(@RequestBody CiudadModel ciudad) {
        String resultadoHttp = ciudadService.crearCiudad(ciudad);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<CiudadModel>> listarCiudad(){
        List<CiudadModel> ciudades = ciudadService.listarCiudad();
        return new ResponseEntity<>(ciudades,HttpStatus.OK);
    }

    @GetMapping("/get/{idCiudad}")
    public ResponseEntity<CiudadModel> buscarCiudadPorId(@RequestBody @PathVariable Integer idCiudad) {
        CiudadModel ciudad = this.ciudadService.obtenerCiudadPorId(idCiudad)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró la ciudad con el id " + idCiudad));
        return ResponseEntity.ok(ciudad);
    }


    @PutMapping ("/put/{idCiudad}")
    public ResponseEntity<String> actaulizarCiudadPorId(@RequestBody CiudadModel ciudad, @PathVariable Integer idCiudad) {
        String resultado = this.ciudadService.actualizarCiudadPorId(ciudad, idCiudad);
        return ResponseEntity.ok(resultado);
    }
}
