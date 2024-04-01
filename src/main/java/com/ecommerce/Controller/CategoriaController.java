package com.ecommerce.Controller;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.CategoriaModel;
import com.ecommerce.Service.IArticuloService;
import com.ecommerce.Service.ICategoriaService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    ICategoriaService categoriaService;
    @PostMapping("/save")
    public ResponseEntity<String> crearCategoria(@RequestBody CategoriaModel categoria) {
        String resultadoHttp = categoriaService.crearCategoria(categoria);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<CategoriaModel>> listarCategorias(){
        List<CategoriaModel> categorias = categoriaService.listarCategoria();
        return new ResponseEntity<>(categorias,HttpStatus.OK);
    }

    @GetMapping("/get/{idCategoria}")
    public ResponseEntity<CategoriaModel> buscarCategoriaPorId(@RequestBody @PathVariable Integer idCategoria) {
        CategoriaModel categoria = this.categoriaService.obtenerCategoriaPorId(idCategoria)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró la categoria con el id " + idCategoria));
        return ResponseEntity.ok(categoria);
    }

    @PutMapping ("/put/{idCategoria}")
    public ResponseEntity<String> actualizarCategoriaPorId(@RequestBody CategoriaModel detallesCategoria, @PathVariable Integer idCategoria) {
        String resultado = this.categoriaService.actualizarCategoriaporId(detallesCategoria, idCategoria);
        return ResponseEntity.ok(resultado);
    }
}
