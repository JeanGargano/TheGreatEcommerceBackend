package com.ecommerce.Controller;


import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Service.IUsuarioService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    IUsuarioService usuarioService;

    @PostMapping("/save")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioModel usuario) {
        String resultadoHttp = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<UsuarioModel>> listarUsuario(){
        List<UsuarioModel> usuarios = usuarioService.listarUsuario();
        return new ResponseEntity<>(usuarios,HttpStatus.OK);
    }

    @GetMapping("/get/{idUsuario}")
    public ResponseEntity<UsuarioModel> buscarUsuarioPorId(@RequestBody @PathVariable Integer idUsuario) {
        UsuarioModel usuario = this.usuarioService.obtenerUsuarioPorId(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el usuario con el id " + idUsuario));
        return ResponseEntity.ok(usuario);
    }


    @PutMapping ("/put/{idUsuario}")
    public ResponseEntity<String> actualizarUsuarioPorId(@RequestBody UsuarioModel usuario, @PathVariable Integer idUsuario) {
        String resultado = this.usuarioService.actualizarUsuarioPorId(usuario, idUsuario);
        return ResponseEntity.ok(resultado);
    }

}
