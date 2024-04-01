package com.ecommerce.Controller;

import com.ecommerce.Model.OrdenPersonalizacionModel;
import com.ecommerce.Service.IOrdenPersonalizacionService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordenPersonalizacion")

public class OrdenPersonalizacionController {

    @Autowired
    IOrdenPersonalizacionService ordenPersonalizacionService;

    @PostMapping("/save")
    public ResponseEntity<String> crearOrdenPersonalizacion(@RequestBody OrdenPersonalizacionModel ordenPersonalizacion) {
        String resultadoHttp = ordenPersonalizacionService.crearOrdenPersonalizacion(ordenPersonalizacion);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<OrdenPersonalizacionModel>> listarOrdenPersonalizacion(){
        List<OrdenPersonalizacionModel> ordenesPersonalizaciones = ordenPersonalizacionService.listarOrdenPersonalizacion();
        return new ResponseEntity<>(ordenesPersonalizaciones,HttpStatus.OK);
    }

    @GetMapping("/get/{idOrdenPersonalizacion}")
    public ResponseEntity<OrdenPersonalizacionModel> buscarOrdenPersonalizacionPorId(@RequestBody @PathVariable Integer idOrdenPersonalizacion) {
        OrdenPersonalizacionModel ordenPersonalizacion = this.ordenPersonalizacionService.obtenerOrdenPersonalizacionPorId(idOrdenPersonalizacion)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró la orden de personalizacion con el id " + idOrdenPersonalizacion));
        return ResponseEntity.ok(ordenPersonalizacion);
    }


    @PutMapping ("/put/{idOrdenPersonalizacion}")
    public ResponseEntity<String> actualizarOrdenPersonalizacionPorId(@RequestBody OrdenPersonalizacionModel ordenPersonalizacion, @PathVariable Integer idOrdenPersonalizacion) {
        String resultado = this.ordenPersonalizacionService.actualizarOrdenPersonalizacionPorId(ordenPersonalizacion, idOrdenPersonalizacion);
        return ResponseEntity.ok(resultado);
    }

}
