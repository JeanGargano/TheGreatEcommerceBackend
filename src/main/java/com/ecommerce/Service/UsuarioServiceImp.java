package com.ecommerce.Service;


import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Repository.IUsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary

public class UsuarioServiceImp implements IUsuarioService {

    @Autowired
    IUsuarioRepository usuarioRepository;

    private List<UsuarioModel> usuariosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        usuariosExistentes= this.usuarioRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }
    @Override
    public String crearUsuario(UsuarioModel usuario) {
        String textoRespuesta = "";

        usuariosExistentes = this.usuarioRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(usuariosExistentes.isEmpty()){

            this.usuarioRepository.save(usuario);

            textoRespuesta =  "El usuario ha sido creado con éxito";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (UsuarioModel i : usuariosExistentes) {
                if (usuario.getIdUsuario().equals(i.getIdUsuario())) {

                    textoRespuesta = "El usuario con ID: " + usuario.getIdUsuario() + ", Ya se encuentra creada.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.usuarioRepository.save(usuario);

                    textoRespuesta = "El usuario ha sido creado con éxito";
                }
            }
        }
        return textoRespuesta;
    }

    @Override
    public List<UsuarioModel> listarUsuario() {

        return this.usuarioRepository.findAll();
    }

    @Override
    public Optional<UsuarioModel> obtenerUsuarioPorId(Integer idUsuario) {
        return this.usuarioRepository.findById(idUsuario);
    }

    @Override
    public String actualizarUsuarioPorId(UsuarioModel usuario, Integer idUsuario) {
        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<UsuarioModel> usuarioEncontrado = this.usuarioRepository.findById(idUsuario);

        if(usuarioEncontrado.isPresent()){

            UsuarioModel usuarioActualizar = usuarioEncontrado.get();

            BeanUtils.copyProperties(usuario, usuarioActualizar);

            this.usuarioRepository.save(usuarioActualizar);

            return "El usuario con id: " + idUsuario + ", Ha sido actualizada con exito.";

        }else{

            textoRespuesta = "El usuario con id: "+ idUsuario + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
}
