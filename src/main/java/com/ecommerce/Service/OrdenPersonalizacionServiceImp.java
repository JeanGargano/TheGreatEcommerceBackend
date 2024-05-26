package com.ecommerce.Service;

import com.ecommerce.Model.*;
import com.ecommerce.Repository.IComentarioRepository;
import com.ecommerce.Repository.IOrdenPersonalizacionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.ecommerce.Model.ComentarioModel;

import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Primary

public class OrdenPersonalizacionServiceImp implements IOrdenPersonalizacionService {

    @Autowired
    IOrdenPersonalizacionRepository ordenPersonalizacionRepository;

    @Autowired
    IComentarioRepository comentarioRepository;

    private List<OrdenPersonalizacionModel> ordenesPersonalizacionesExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        ordenesPersonalizacionesExistentes = this.ordenPersonalizacionRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearOrdenPersonalizacion(OrdenPersonalizacionModel ordenPersonalizacion) {

        String textoRespuesta = "";

        try {
            ComentarioModel comentarioOrden = ordenPersonalizacion.getIdComentario();

            String descripcion = comentarioOrden.getDescripcion();
            String fechaComentario = comentarioOrden.getFecha();
            UsuarioModel idUsuarioComentario = comentarioOrden.getIdUsuario();

            String imagenDisenio = ordenPersonalizacion.getImagenDisenio();


            UsuarioModel suDiseniador = ordenPersonalizacion.getSuDiseniador();

            Integer idUsuarioDiseniador = suDiseniador.getIdUsuario();



            ordenesPersonalizacionesExistentes = this.ordenPersonalizacionRepository.findAll();

            if (idUsuarioComentario == null) {
                textoRespuesta += "el id del comentario de usuario no puede ser nulo\n";
            }

            if (descripcion == null || descripcion.isBlank()) {
                textoRespuesta += "La descripción no puede ser nula.\n";
            }

            if(fechaComentario == null || fechaComentario.isBlank()){
                textoRespuesta += "La fecha del comentario no puede ser nula.";
            }

            if (!textoRespuesta.isEmpty()) {
                textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
            }else{
                 // Este proceso se hace, ya que si se guarda comentario se sobreescribe el obj en vez de guardarlo como otro
                ComentarioModel comentario = ordenPersonalizacion.getIdComentario();
                ComentarioModel objC = new ComentarioModel();

                objC.setDescripcion(descripcion);
                objC.setFecha(fechaComentario);
                objC.setIdUsuario(idUsuarioComentario);

                if (ordenesPersonalizacionesExistentes.isEmpty()) {
                    this.ordenPersonalizacionRepository.save(ordenPersonalizacion);
                    this.comentarioRepository.save(objC);
                    textoRespuesta = "La orden de personalizacion ha sido creado con éxito.";
                }else {



                    this.ordenPersonalizacionRepository.save(ordenPersonalizacion);
                    this.comentarioRepository.save(objC);
                    textoRespuesta = "La orden de personalizacion ha sido creada con exito.";
                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Algún objeto es nulo\n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "Verifique si la personalizacion o la orden ya se encuentra creadas en la base de datos\n";
        }

        return textoRespuesta;
    }

    @Override
    public List<OrdenPersonalizacionModel> listarOrdenPersonalizacion() {
        return this.ordenPersonalizacionRepository.findAll();
    }

    @Override
    public Optional<OrdenPersonalizacionModel> obtenerOrdenPersonalizacionPorId(Integer idOrdenPersonalizacion) {
        return this.ordenPersonalizacionRepository.findById(idOrdenPersonalizacion);
    }

    @Override
    public String actualizarOrdenPersonalizacionPorId(OrdenPersonalizacionModel ordenPersonalizacion, Integer idOrdenPersonalizacion) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.
        try {
            Optional<OrdenPersonalizacionModel> ordenPersonalizacionEncontrada = this.ordenPersonalizacionRepository.findById(idOrdenPersonalizacion);

            if (ordenPersonalizacionEncontrada.isPresent()) {

                OrdenPersonalizacionModel ordenPersonalizacionActualizar = ordenPersonalizacionEncontrada.get();

                BeanUtils.copyProperties(ordenPersonalizacion, ordenPersonalizacionActualizar);

                this.ordenPersonalizacionRepository.save(ordenPersonalizacion);

                return "La orden de personalizacion con código: " + idOrdenPersonalizacion + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "La orden de personalizacion con código: " + idOrdenPersonalizacion + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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
