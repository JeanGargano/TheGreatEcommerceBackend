package com.ecommerce.Controller;

import com.ecommerce.Model.ComentarioModel;
import com.ecommerce.Service.IComentarioService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentario")

public class ComentarioController {

    @Autowired
    IComentarioService comentarioService;

    @PostMapping("/save")
    public ResponseEntity<String> crearComentario(@RequestBody ComentarioModel comentario) {
        String resultadoHttp = comentarioService.crearComentario(comentario);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<ComentarioModel>> listarComentarios(){
        List<ComentarioModel> comentarios = comentarioService.listarComentario();
        return new ResponseEntity<>(comentarios,HttpStatus.OK);
    }

    @GetMapping("/get/{idComentario}")
    public ResponseEntity<ComentarioModel> buscarComentarioPorId(@RequestBody @PathVariable Integer idComentario) {
        ComentarioModel comentario = this.comentarioService.obtenerComentarioPorId(idComentario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el comentario con el id " + idComentario));
        return ResponseEntity.ok(comentario);
    }


    @PutMapping ("/put/{idComentario}")
    public ResponseEntity<String> actualizarComentarioPorId(@RequestBody ComentarioModel comentario, @PathVariable Integer idComentario) {
        String resultado = this.comentarioService.actualizarComentarioPorId(comentario, idComentario);
        return ResponseEntity.ok(resultado);
    }




}
