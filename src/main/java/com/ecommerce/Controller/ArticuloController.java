package com.ecommerce.Controller;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Service.IArticuloService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articulo")
public class ArticuloController {

    @Autowired
    IArticuloService articuloService;
    @PostMapping("/save")
    public ResponseEntity<String> crearArticulo(@RequestBody ArticuloModel articulo) {
        String resultadoHttp = articuloService.crearArticulo(articulo);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<ArticuloModel>> listarArticulo(){
        List<ArticuloModel> articulos = articuloService.listarArticulo();
        return new ResponseEntity<>(articulos,HttpStatus.OK);
    }

    @GetMapping("/get/{idArticulo}")
    public ResponseEntity<ArticuloModel> buscarArticuloPorId(@RequestBody @PathVariable Integer idArticulo) {
        ArticuloModel articulo = this.articuloService.obtenerArticuloPorId(idArticulo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el articulo con el id " + idArticulo));
        return ResponseEntity.ok(articulo);
    }

    @DeleteMapping("/del/{idArticulo}")
    public ResponseEntity<String> eliminarArticuloPorId(@RequestBody @PathVariable Integer idArticulo) {
        String resultado = this.articuloService.eliminarArticuloPorId(idArticulo);
        return ResponseEntity.ok(resultado);
    }

    @PutMapping ("/put/{idArticulo}")
    public ResponseEntity<String> actualizarArticuloPorId(@RequestBody ArticuloModel detallesArticulo, @PathVariable Integer idArticulo) {
        String resultado = this.articuloService.actualizarArticuloPorId(detallesArticulo, idArticulo);
        return ResponseEntity.ok(resultado);
    }

}
