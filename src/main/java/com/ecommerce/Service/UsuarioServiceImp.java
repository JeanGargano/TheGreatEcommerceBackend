package com.ecommerce.Service;


import com.ecommerce.Model.Enums.TipoSexo;
import com.ecommerce.Model.Enums.TipoUsuario;
import com.ecommerce.Model.Dto.UsuarioModelDto;
import com.ecommerce.Model.OrdenPersonalizacionModel;
import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Repository.IOrdenPersonalizacionRepository;
import com.ecommerce.Repository.IUsuarioRepository;
import com.ecommerce.exception.AccesoNoAutorizadoException;
import com.ecommerce.exception.RecursoNoEncontradoException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary

public class UsuarioServiceImp implements IUsuarioService {

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Autowired
    IOrdenPersonalizacionRepository ordenPersonalizacionRepository;

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

            if (nombre == null || nombre.isBlank()) {
                textoRespuesta += "El nombre no puede ser nulo o estar vacio\n";
            }
            if (telefono == null || telefono < 0) {
                textoRespuesta += "su telefono no puede ser null ó menor a 0\n";
            }
            if (correo == null || correo.isBlank() || correo.indexOf('@') == -1) {
                textoRespuesta += "El correo no puede ser nulo o estar vacio, además verifique si contiene '@'\n";
            }
            if (rol == null ) {
                textoRespuesta += "El rol no puede ser null\n";
            }
            if (sexo == null ) {
                textoRespuesta += "El sexo no puede ser null\n";
            }
            if (identificacion == null || identificacion < 0) {
                textoRespuesta += "La identificacion no puede ser null ó ser menor a 0\n";
            }
            if (!textoRespuesta.isEmpty()) {
                textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
            }else {

                if (usuariosExistentes.isEmpty()) {
                    this.usuarioRepository.save(usuario);
                    textoRespuesta = "El usuario ha sido creado con éxito.";
                }else{
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


    @Override
    public UsuarioModel asignarRol(Integer idUsuario, TipoUsuario rol, TipoUsuario rolUsuario) {
        //try catch por si se ingresa mal el valor del rol
        try {
            // Obtener el usuario
            UsuarioModel usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

            // Verificar si el usuario tiene permisos de administrador
            if (!TipoUsuario.Administrador.equals(rolUsuario)) {
                throw new AccesoNoAutorizadoException("No tienes permisos para asignar roles");
            }

            // Asignar el rol al usuario
            usuario.setRol(rol);
            usuarioRepository.save(usuario);

            return usuario;

        } catch (MethodArgumentTypeMismatchException ex){
            String mensajeError = "El valor proporcionado para el rol no es válido";
            throw new IllegalArgumentException(mensajeError, ex);
        }
    }


    @Override
    public OrdenPersonalizacionModel asignarDiseniador(Integer idOrdenPersonalizacion, Integer idUsuario, TipoUsuario rolUsuario) {

        OrdenPersonalizacionModel ordenP = ordenPersonalizacionRepository.findById(idOrdenPersonalizacion)
                .orElseThrow(() -> new RecursoNoEncontradoException("Orden no encontrada"));

        if (!TipoUsuario.Encargado.equals(rolUsuario)) {
            throw new AccesoNoAutorizadoException("No tienes permisos para asignar diseñadores");
        }

        UsuarioModel usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        if (!TipoUsuario.Diseniador.equals(usuario.getRol())) {
            throw new AccesoNoAutorizadoException("El usuario no es un diseñador");
        }




        return ordenP;
    }


}
