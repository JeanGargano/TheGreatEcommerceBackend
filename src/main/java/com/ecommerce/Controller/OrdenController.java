package com.ecommerce.Controller;


import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Service.IArticuloService;
import com.ecommerce.Service.IOrdenService;
import com.ecommerce.Service.IUsuarioService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orden")
public class OrdenController {

    @Autowired
    IOrdenService ordenService;

    @PostMapping("/save")
    public ResponseEntity<String> crearOrden(@RequestBody OrdenModel orden) {
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


}
