package com.ecommerce.Controller;

import com.ecommerce.Model.OrdenArticuloModel;
import com.ecommerce.Service.IOrdenArticuloService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenArticulo")

public class OrdenArticuloController {

    @Autowired
    IOrdenArticuloService ordenArticuloService;

    @PostMapping("/save")
    public ResponseEntity<String> crearArticuloEnvio(@RequestBody OrdenArticuloModel ordenArticulo) {
        String resultadoHttp = ordenArticuloService.crearOrdenArticulo(ordenArticulo);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<OrdenArticuloModel>> listarOrdenArticulo(){
        List<OrdenArticuloModel> ordenesArticulos = ordenArticuloService.listarOrdenArticulo();
        return new ResponseEntity<>(ordenesArticulos,HttpStatus.OK);
    }

    @GetMapping("/get/{idOrdenArticulo}")
    public ResponseEntity<OrdenArticuloModel> buscarOrdenArticuloPorId(@RequestBody @PathVariable Integer idOrdenArticulo) {
        OrdenArticuloModel ordenArticulo = this.ordenArticuloService.obtenerOrdenArticuloPorId(idOrdenArticulo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró la orden de articulo con el id " + idOrdenArticulo));
        return ResponseEntity.ok(ordenArticulo);
    }


    @PutMapping ("/put/{idOrdenArticulo}")
    public ResponseEntity<String> actualizarOrdenArticuloPorId(@RequestBody OrdenArticuloModel ordenArticulo, @PathVariable Integer idOrdenArticulo) {
        String resultado = this.ordenArticuloService.actualizarOrdenArticulo(ordenArticulo, idOrdenArticulo);
        return ResponseEntity.ok(resultado);
    }
}
