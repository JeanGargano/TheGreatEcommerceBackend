package com.ecommerce.Service;


import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.Enums.TipoSexo;
import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Repository.IUsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
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

        String nombre = usuario.getNombre();
        Integer telefono = usuario.getTelefono();
        String correo = usuario.getCorreo();
        String direccion = usuario.getDireccion();
        TipoSexo sexo = usuario.getSexo();
        Integer identificacion = usuario.getIdentificacion();


        usuariosExistentes = this.usuarioRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(usuariosExistentes.isEmpty()){

            this.usuarioRepository.save(usuario);

            textoRespuesta =  "El usuario ha sido creado con éxito.";
            System.out.println("Anda entrando aca");

        } else {
            if (nombre == null || nombre.isBlank()) {
                textoRespuesta = "El nombre no puede estar vacia o ser nula";
            } else if (telefono == null ) {
                textoRespuesta = "El telefono no puede estar vacia o ser nula";
            } else if (correo == null || correo.isBlank()) {
                textoRespuesta = "El correo no puede estar vacia o ser nula";
            } else if (direccion == null) {
                textoRespuesta = "La direccion no puede estar vacia o ser nula";
            } else if (sexo == null) {
                textoRespuesta = "El sexo no puede estar vacia o ser nulo";
            } else if (identificacion == null) {
                textoRespuesta = "La identificacion no puede estar vacia o ser nulo";
            } else {
                this.usuarioRepository.save(usuario);
                textoRespuesta = "El usuario ha sido creado con exito";
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
        try {
            Optional<UsuarioModel> usuarioEncontrado = this.usuarioRepository.findById(idUsuario);

            if (usuarioEncontrado.isPresent()) {

                UsuarioModel usuarioActualizar = usuarioEncontrado.get();

                BeanUtils.copyProperties(usuario, usuarioActualizar);

                this.usuarioRepository.save(usuarioActualizar);

                return "El usuario con código: " + idUsuario + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "El usuario con código: " + idUsuario + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
            }
        }catch(NullPointerException e){
            textoRespuesta = "Alguno de los valores son nulos, verifique los campos";
        }catch(UncheckedIOException e){
            textoRespuesta = "Se presento un error, inesperado. Verifique el JSON y los valores no puede ser nulos.";
        }catch(DataIntegrityViolationException e){
            textoRespuesta = "Un error en el JSON, verifique.";
        }

        return textoRespuesta;
    }
}
