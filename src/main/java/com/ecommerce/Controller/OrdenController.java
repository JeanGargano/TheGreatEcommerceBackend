package com.ecommerce.Controller;


import com.ecommerce.Model.Dto.OrdenModelDTO;
import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Service.IOrdenService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orden")
public class OrdenController {

    @Autowired
    IOrdenService ordenService;

    @PostMapping("/save")
    public ResponseEntity<String> crearOrden(@RequestBody OrdenModelDTO orden) {
        String resultadoHttp = ordenService.crearOrden(orden);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<OrdenModel>> listarOrden(){
        List<OrdenModel> ordenes = ordenService.listarOrden();
        return new ResponseEntity<>(ordenes,HttpStatus.OK);
    }

    @GetMapping("/get/{idOrden}")
    public ResponseEntity<OrdenModel> buscarOrdenPorId(@RequestBody @PathVariable Integer idOrden) {
        OrdenModel orden = this.ordenService.obtenerOrdenPorId(idOrden)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró la orden con el id " + idOrden));
        return ResponseEntity.ok(orden);
    }


    @PutMapping ("/put/{idOrden}")
    public ResponseEntity<String> actualizarOrdenPorId(@RequestBody OrdenModel orden, @PathVariable Integer idOrden) {
        String resultado = this.ordenService.actualizarOrdenPorId(orden, idOrden);
        return ResponseEntity.ok(resultado);
    }


    @GetMapping("get/listarInformacion/{idOrden}")
    public ResponseEntity<Optional<String>> getOrdenPorId(@RequestBody @PathVariable Integer idOrden) {

        Optional<String> orden = this.ordenService.listarInformacion(idOrden)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró la orden con el id " + idOrden)).describeConstable();
        return ResponseEntity.ok(orden);
    }


}
