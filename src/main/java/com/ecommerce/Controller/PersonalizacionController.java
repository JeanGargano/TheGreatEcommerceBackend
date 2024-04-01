package com.ecommerce.Controller;


import com.ecommerce.Model.ArticuloTallaModel;
import com.ecommerce.Model.PersonalizacionModel;
import com.ecommerce.Service.IArticuloTallaService;
import com.ecommerce.Service.IPersonalizacionService;
import com.ecommerce.exception.RecursoNoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personalizacion")
public class PersonalizacionController {

    @Autowired
    IPersonalizacionService personalizacionService;
    @PostMapping("/save")
    public ResponseEntity<String> crearPersonalizacion(@RequestBody PersonalizacionModel personalizacion) {
        String resultadoHttp = personalizacionService.crearPersonalizacion(personalizacion);
        return new ResponseEntity<String>(resultadoHttp, HttpStatus.OK);
    }


    @GetMapping("/get/all")
    public ResponseEntity<List<PersonalizacionModel>> listarPersonalizaciones(){
        List<PersonalizacionModel> personalizaciones = personalizacionService.listarPersonalizacion();
        return new ResponseEntity<>(personalizaciones,HttpStatus.OK);
    }

    @GetMapping("/get/{idPersonalizacion}")
    public ResponseEntity<PersonalizacionModel> buscarPersonalizacionPorId(@RequestBody @PathVariable Integer idPersonalizacion) {
        PersonalizacionModel personalizacion = this.personalizacionService.obtenerPersonalizacionPorId(idPersonalizacion)
                .orElseThrow(() -> new RecursoNoEncontradoException("Error! No se encontró la personalización con  con el id " + idPersonalizacion));
        return ResponseEntity.ok(personalizacion);
    }

    @PutMapping ("/put/{idPersonalizacion}")
    public ResponseEntity<String> actualizarPersonalizacionPorId(@RequestBody PersonalizacionModel personalizacion, @PathVariable Integer idPersonalizacion) {
        String resultado = this.personalizacionService.actualizarPersonalizacionPorId(personalizacion, idPersonalizacion);
        return ResponseEntity.ok(resultado);
    }

}
