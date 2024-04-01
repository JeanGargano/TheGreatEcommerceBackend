package com.ecommerce.Controller;


import com.ecommerce.Model.ArticuloTallaModel;
import com.ecommerce.Service.IArticuloTallaService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articuloTalla")
public class ArticuloTallaController {


    @Autowired
    IArticuloTallaService articuloTallaService;
    @PostMapping("/save")
    public ResponseEntity<String> crearArticuloTalla(@RequestBody ArticuloTallaModel articuloTalla) {
        String resultadoHttp = articuloTallaService.crearArticuloTalla(articuloTalla);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<ArticuloTallaModel>> listarArticuloTalla(){
        List<ArticuloTallaModel> articulosTalla = articuloTallaService.listarArticuloTalla();
        return new ResponseEntity<>(articulosTalla,HttpStatus.OK);
    }

    @GetMapping("/get/{idArticuloTalla}")
    public ResponseEntity<ArticuloTallaModel> buscarArticuloTallaPorId(@RequestBody @PathVariable Integer idArticuloTalla) {
        ArticuloTallaModel articulosT = this.articuloTallaService.obtenerArticuloTallaPorId(idArticuloTalla)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró la talla del articulo con  con el id " + idArticuloTalla));
        return ResponseEntity.ok(articulosT);
    }

    @PutMapping ("/put/{idArticuloTalla}")
    public ResponseEntity<String> actualizarArticuloPorId(@RequestBody ArticuloTallaModel detallesArticuloTalla, @PathVariable Integer idArticuloTalla) {
        String resultado = this.articuloTallaService.actualizarArticuloTallaPorId(detallesArticuloTalla, idArticuloTalla);
        return ResponseEntity.ok(resultado);
    }

}

    

