package com.ecommerce.Controller;

import com.ecommerce.Model.DisenioModel;
import com.ecommerce.Service.IDisenioService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disenio")

public class DisenioController {

    @Autowired
    IDisenioService disenioService;

    @PostMapping("/save")
    public ResponseEntity<String> crearDisenio(@RequestBody DisenioModel disenio) {
        String resultadoHttp = disenioService.crearDisenio(disenio);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<DisenioModel>> listarDisenio(){
        List<DisenioModel> disenios = disenioService.listarDisenio();
        return new ResponseEntity<>(disenios,HttpStatus.OK);
    }

    @GetMapping("/get/{idDisenio}")
    public ResponseEntity<DisenioModel> buscarDisenioPorId(@RequestBody @PathVariable Integer idDisenio) {
        DisenioModel disenio = this.disenioService.obtenerDisenioPorId(idDisenio)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el diseño con el id " + idDisenio));
        return ResponseEntity.ok(disenio);
    }


    @PutMapping ("/put/{idDisenio}")
    public ResponseEntity<String> actualizarUsuarioPorId(@RequestBody DisenioModel disenio, @PathVariable Integer idDisenio) {
        String resultado = this.disenioService.actualizarDisenioPorId(disenio, idDisenio);
        return ResponseEntity.ok(resultado);
    }
}
