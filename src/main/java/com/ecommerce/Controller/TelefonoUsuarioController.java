package com.ecommerce.Controller;

import com.ecommerce.Model.TelefonoUsuarioModel;
import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Service.ITelefonoUsuarioService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefonoUsuario")

public class TelefonoUsuarioController {

    @Autowired
    ITelefonoUsuarioService telefonoUsuarioService;

    @PostMapping("/save")
    public ResponseEntity<String> crearTelefonoUsuario(@RequestBody TelefonoUsuarioModel telefonoUsuario) {
        String resultadoHttp = telefonoUsuarioService.crearTelefonoUsuario(telefonoUsuario);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<TelefonoUsuarioModel>> listarTelefonoUsuario(){
        List<TelefonoUsuarioModel> telefonosUsuarios = telefonoUsuarioService.listarTelefonoUsuario();
        return new ResponseEntity<>(telefonosUsuarios,HttpStatus.OK);
    }

    @GetMapping("/get/{idTelefonoUsuario}")
    public ResponseEntity<TelefonoUsuarioModel> buscarTelefonoUsuarioPorId(@RequestBody @PathVariable Integer idTelefonoUsuario) {
        TelefonoUsuarioModel telefonoUsuario = this.telefonoUsuarioService.obtenerTelefonoUsuarioPorId(idTelefonoUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el telefono del usuario con el id: " + idTelefonoUsuario));
        return ResponseEntity.ok(telefonoUsuario);
    }


    @PutMapping ("/put/{idTelefonoUsuario}")
    public ResponseEntity<String> actualizarTelefonoUsuarioPorId(@RequestBody TelefonoUsuarioModel telefonoUsuario, @PathVariable Integer idTelefonoUsuario) {
        String resultado = this.telefonoUsuarioService.actualizarTelefonoUsuarioPorId(telefonoUsuario, idTelefonoUsuario);
        return ResponseEntity.ok(resultado);
    }
}
