package com.ecommerce.Service;

import com.ecommerce.Model.EnvioModel;
import com.ecommerce.Repository.IEnvioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary

public class EnvioServiceImp implements IEnvioService{

    @Autowired
    IEnvioRepository envioRepository;

    private List<EnvioModel> enviosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        enviosExistentes= this.envioRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearEnvio(EnvioModel envio) {

        String textoRespuesta = "";

        enviosExistentes = this.envioRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(enviosExistentes.isEmpty()){

            this.envioRepository.save(envio);

            textoRespuesta =  "El usuario ha sido creado con éxito";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (EnvioModel i : enviosExistentes) {
                if (envio.getIdEnvio().equals(i.getIdEnvio())) {

                    textoRespuesta = "El envio con ID: " + envio.getIdEnvio() + ", Ya se encuentra creado.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.envioRepository.save(envio);
                    textoRespuesta = "El envio ha sido creado con éxito";
                }
            }
        }
        return textoRespuesta;
    }

    @Override
    public List<EnvioModel> listarEnvio() {
        return this.envioRepository.findAll();
    }

    @Override
    public Optional<EnvioModel> obtenerEnvioPorId(Integer idEnvio) {
        return this.envioRepository.findById(idEnvio);
    }

    @Override
    public String actualizarEnvioPorId(EnvioModel envio, Integer idEnvio) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<EnvioModel> envioEncontrado = this.envioRepository.findById(idEnvio);

        if(envioEncontrado.isPresent()){

            EnvioModel envioActualizar = envioEncontrado.get();

            BeanUtils.copyProperties(envio, envioActualizar);

            this.envioRepository.save(envioActualizar);

            return "El envio con id: " + idEnvio + ", Ha sido actualizado con exito.";

        }else{

            textoRespuesta = "El envio con id: "+ idEnvio + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
}
