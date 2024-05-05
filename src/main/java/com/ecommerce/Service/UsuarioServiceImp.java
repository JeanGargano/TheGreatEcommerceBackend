package com.ecommerce.Service;


import com.ecommerce.Model.Enums.TipoSexo;
import com.ecommerce.Model.Enums.TipoUsuario;
import com.ecommerce.Model.Dto.UsuarioModelDto;
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
import java.util.stream.Collectors;

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

        try {
            String nombre = usuario.getNombre();
            String correo = usuario.getCorreo();
            TipoUsuario rol = usuario.getRol();
            TipoSexo sexo = usuario.getSexo();
            Integer identificacion = usuario.getIdentificacion();
            Long telefono = usuario.getTelefono();


            usuariosExistentes = this.usuarioRepository.findAll();

            if (usuariosExistentes.isEmpty()) {
                this.usuarioRepository.save(usuario);
                textoRespuesta = "El usuario ha sido creado con éxito.";
            } else {
                if (nombre == null || nombre.isBlank()) {
                    textoRespuesta += "El nombre no puede ser nulo o estar vacio\n";
                }
                if (telefono == null ) {
                    textoRespuesta += "su telefono no puede ser null\n";
                }
                if (correo == null || correo.isBlank()) {
                    textoRespuesta += "El correo no puede ser nulo o estar vacio\n";
                }
                if (rol == null ) {
                    textoRespuesta += "El rol no puede ser null\n";
                }
                if (sexo == null ) {
                    textoRespuesta += "El sexo no puede ser null\n";
                }
                if (identificacion == null ) {
                    textoRespuesta += "La identificacion no puede ser null\n";
                }
                if (!textoRespuesta.isEmpty()) {
                    textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
                } else {
                    this.usuarioRepository.save(usuario);
                    textoRespuesta = "El usuario ha sido creado con éxito.";
                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Algún objeto es null\n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "Verifique los campos y vuelva a enciar el json\n";
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

    @Override
    public Optional<UsuarioModel> verificarUsuario(UsuarioModelDto usuarioDto) {
        String correo = usuarioDto.getCorreo();
        String contrasenia = usuarioDto.getContrasenia();
        return usuarioRepository.findUsuarioModelByCorreo(correo)
                .filter(usuarioEncontrado -> usuarioEncontrado.getContrasenia().equals(contrasenia));

    }

    @Override
    public List<String> listarDiseniadores() {
        List<UsuarioModel> usuarios = this.usuarioRepository.findAll();

        // Filtrar los usuarios por diseñador
        List<String> diseniadores = usuarios.stream()
                .filter(usuario -> usuario.getRol() == TipoUsuario.Diseniador)
                .map(UsuarioModel::getNombre)
                .collect(Collectors.toList());

        return diseniadores;
    }

}
