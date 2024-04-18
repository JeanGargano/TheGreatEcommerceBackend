package com.ecommerce.Service;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Repository.IArticuloRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class ArticuloServiceImp implements IArticuloService {

    @Autowired
    IArticuloRepository ArticuloRepository;

    private List<ArticuloModel> ArticulosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        ArticulosExistentes = this.ArticuloRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearArticulo(ArticuloModel articulo) {

        String textoRespuesta = "";

        String urlImagen = articulo.getImagen();
        String descripcion = articulo.getDescripcion();
        String nombreArticulo = articulo.getNombre();
        Integer costo = articulo.getPrecio();
        Integer idCategoria = articulo.getIdCategoria().getIdCategoria();
        Boolean esPersonalizable = articulo.getEsPersonalizable();

        ArticulosExistentes = this.ArticuloRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(ArticulosExistentes.isEmpty()){

            this.ArticuloRepository.save(articulo);

            textoRespuesta =  "El artículo ha sido creado con éxito.";
            System.out.println("Anda entrando aca");

        } else {
            if (urlImagen == null || urlImagen.isBlank()) {
                textoRespuesta = "La URL imagén no puede estar vacia o ser nula";
            } else if (descripcion == null || descripcion.isBlank()) {
                textoRespuesta = "La descripción no puede estar vacia o ser nula";
            } else if (nombreArticulo == null || nombreArticulo.isBlank()) {
                textoRespuesta = "El nombre del artículo no puede estar vacia o ser nula";
            } else if (costo == null) {
                textoRespuesta = "El precio no puede ser nulo";
            } else if (idCategoria == null) {
                textoRespuesta = "La id de la categoria no puede estar vacia o nula";
            } else if (esPersonalizable == null) {
                textoRespuesta = "El campo de personalizable no puede estar vacio o ser nula";
            } else {
                this.ArticuloRepository.save(articulo);
                textoRespuesta = "El articulo ha sido creado con éxito.";
            }
        }
        return textoRespuesta;
    }

    @Override
    public List<ArticuloModel> listarArticulo() {
        return this.ArticuloRepository.findAll();
    }

    @Override
    public Optional<ArticuloModel> obtenerArticuloPorId(Integer idArticulo) {
        return this.ArticuloRepository.findById(idArticulo);
    }

    public String eliminarArticuloPorId(Integer idArticulo){
        String textoRespuesta = "";

        ArticulosExistentes = this.ArticuloRepository.findAll();

        if(ArticulosExistentes.isEmpty()){

            textoRespuesta = "El artículo con código: " + idArticulo + ", No existe, por ende no se ha realizado el proceso correctamente.";

        }else {


            // Verificamos si la area existe
            for (ArticuloModel i : ArticulosExistentes) {

                if (i.getIdArticulo().equals(idArticulo)) {

                    this.ArticuloRepository.deleteById(idArticulo);
                    textoRespuesta = "El artículo con código: " + idArticulo + ", Se ha borrado con éxito.";
                    break;
                } else {

                    textoRespuesta = "El artículo con código: " + idArticulo + ", No existe, por ende no se ha realizado el proceso correctamente.";
                }
            }
        }
        return textoRespuesta;
    }

    @Override
    public String actualizarArticuloPorId(ArticuloModel articulo, Integer idArticulo) {
        String textoRespuesta = "";

        // Verificamos si existe para actualizar.
        try {
            Optional<ArticuloModel> articuloEncontrado = this.ArticuloRepository.findById(idArticulo);

            if (articuloEncontrado.isPresent()) {

                ArticuloModel articuloActualizar = articuloEncontrado.get();

                BeanUtils.copyProperties(articulo, articuloActualizar);

                this.ArticuloRepository.save(articuloActualizar);

                return "El artículo con código: " + idArticulo + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "El artículo con código: " + idArticulo + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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


//






