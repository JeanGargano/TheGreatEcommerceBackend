package com.ecommerce.Controller;

import com.ecommerce.Model.TallaModel;
import com.ecommerce.Service.ITallaService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/talla")
public class TallaController {

    @Autowired
    ITallaService tallaService;

    @PostMapping("/save")
    public ResponseEntity<String> crearTalla(@RequestBody TallaModel talla) {
        String resultadoHttp = tallaService.crearTalla(talla);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<TallaModel>> listarTalla(){
        List<TallaModel> tallas = tallaService.listarTalla();
        return new ResponseEntity<>(tallas,HttpStatus.OK);
    }

    @GetMapping("/get/{idTalla}")
    public ResponseEntity<TallaModel> buscarTallaPorId(@RequestBody @PathVariable Integer idTalla) {
        TallaModel talla = this.tallaService.obtenerTallaPorId(idTalla)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró la talla con el id " + idTalla));
        return ResponseEntity.ok(talla);
    }


    @PutMapping ("/put/{idTalla}")
    public ResponseEntity<String> actualizarTallaPorId(@RequestBody TallaModel talla, @PathVariable Integer idTalla) {
        String resultado = this.tallaService.actualizarTallaPorId(talla, idTalla);
        return ResponseEntity.ok(resultado);
    }
}
