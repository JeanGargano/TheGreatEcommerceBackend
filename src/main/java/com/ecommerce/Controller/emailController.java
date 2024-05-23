package com.ecommerce.Controller;


import com.ecommerce.Service.IComentarioService;
import com.ecommerce.Service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class emailController {

    @Autowired
    IEmailService emailService;

    @PostMapping("/send/{idOrden}")
    public ResponseEntity<String> enviarCorreo(@RequestBody @PathVariable Integer idOrden){
        String resultadoCorreo = emailService.enviarEmail(idOrden);
        return new ResponseEntity<String>(resultadoCorreo, HttpStatus.OK);
    }
}
