package com.ecommerce.Service;

import com.ecommerce.Model.OrdenPersonalizacionModel;
import com.ecommerce.Model.TelefonoUsuarioModel;
import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Repository.ITelefonoUsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary


public class TelefonoUsuarioServiceImp implements ITelefonoUsuarioService {

    @Autowired
    ITelefonoUsuarioRepository telefonoUsuarioRepository;

    private List<TelefonoUsuarioModel> telefonoUsuariosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        telefonoUsuariosExistentes= this.telefonoUsuarioRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }
    @Override
    public String crearTelefonoUsuario(TelefonoUsuarioModel telefonoUsuario) {

        String textoRespuesta = "";

        telefonoUsuariosExistentes = this.telefonoUsuarioRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(telefonoUsuariosExistentes.isEmpty()){

            this.telefonoUsuarioRepository.save(telefonoUsuario);

            textoRespuesta =  "El telefono del usuario ha sido creado con éxito";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (TelefonoUsuarioModel i : telefonoUsuariosExistentes) {
                if (telefonoUsuario.getIdTelefonoUsuario().equals(i.getIdTelefonoUsuario())) {

                    textoRespuesta = "El telefono del usuario con ID: " + telefonoUsuario.getIdUsuario() + ", Ya se encuentra creada.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.telefonoUsuarioRepository.save(telefonoUsuario);

                    textoRespuesta = "El telefono del usuario ha sido creado con éxito";
                }
            }
        }
        return textoRespuesta;
    }

    @Override
    public List<TelefonoUsuarioModel> listarTelefonoUsuario() {
        return this.telefonoUsuarioRepository.findAll();
    }

    @Override
    public Optional<TelefonoUsuarioModel> obtenerTelefonoUsuarioPorId(Integer idTelefonoUsuario) {
        return this.telefonoUsuarioRepository.findById(idTelefonoUsuario);
    }

    @Override
    public String actualizarTelefonoUsuarioPorId(TelefonoUsuarioModel telefonoUsuario, Integer idTelefonoUsuario) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<TelefonoUsuarioModel> telefonoUsuarioEncontrado = this.telefonoUsuarioRepository.findById(idTelefonoUsuario);

        if(telefonoUsuarioEncontrado.isPresent()){

            TelefonoUsuarioModel telefonoUsuarioActualizzar = telefonoUsuarioEncontrado.get();

            BeanUtils.copyProperties(telefonoUsuario, telefonoUsuarioActualizzar);

            this.telefonoUsuarioRepository.save(telefonoUsuario);

            return "El telefono delusuario con id: " + telefonoUsuario.getTelefono() + ", Ha sido actualizada con exito.";

        }else{

            textoRespuesta = "El telefono del usuario con id: "+ telefonoUsuario.getIdUsuario() + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
}
