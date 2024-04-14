package com.ecommerce.Controller;

import com.ecommerce.Model.EnvioModel;
import com.ecommerce.Service.IEnvioService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envio")

public class EnvioController {

    @Autowired
    IEnvioService envioService;

    @PostMapping("/save")
    public ResponseEntity<String> crearEnvio(@RequestBody EnvioModel envio) {
        String resultadoHttp = envioService.crearEnvio(envio);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<EnvioModel>> listarEnvio(){
        List<EnvioModel> envios = envioService.listarEnvio();
        return new ResponseEntity<>(envios,HttpStatus.OK);
    }

    @GetMapping("/get/{idEnvio}")
    public ResponseEntity<EnvioModel> buscarEnvioPorId(@RequestBody @PathVariable Integer idEnvio) {
        EnvioModel envio = this.envioService.obtenerEnvioPorId(idEnvio)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró el envio con el id " + idEnvio));
        return ResponseEntity.ok(envio);
    }


    @PutMapping ("/put/{idEnvio}")
    public ResponseEntity<String> actualizarUsuarioPorId(@RequestBody EnvioModel envio, @PathVariable Integer idEnvio) {
        String resultado = this.envioService.actualizarEnvioPorId(envio, idEnvio);
        return ResponseEntity.ok(resultado);
    }
}
