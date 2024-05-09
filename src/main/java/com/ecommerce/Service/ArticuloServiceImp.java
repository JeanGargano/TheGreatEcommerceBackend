package com.ecommerce.Service;

import com.ecommerce.Model.*;
import com.ecommerce.Repository.IArticuloRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
        try {
            String imagen = articulo.getImagen();
            String descripcion = articulo.getDescripcion();
            String nombre = articulo.getNombre();
            Integer precio = articulo.getPrecio();
            CategoriaModel categoria = articulo.getIdCategoria();
            Boolean personalizable = articulo.getEsPersonalizable();
            Integer cantidad = articulo.getCantidad();
            TallaModel  nombreTalla = articulo.getIdTalla();

            ArticulosExistentes = this.ArticuloRepository.findAll();

            if (ArticulosExistentes.isEmpty()) {
                this.ArticuloRepository.save(articulo);
                textoRespuesta = "El artículo ha sido creado con éxito.";
            } else {
                if (imagen == null || imagen.isBlank()) {
                    textoRespuesta += "La imagen no puede ser nula o estar vacia\n";
                }
                if (descripcion == null || descripcion.isBlank()) {
                    textoRespuesta += "La descripcion no puede ser nula o estar vacia\n";
                }
                if (nombre == null || nombre.isBlank()) {
                    textoRespuesta += "El nombre no puede ser nulo o estar vacio\n";
                }
                if (precio == null) {
                    textoRespuesta += "El precio no puede estar vacio\n";

                }
                if (cantidad == null) {
                    textoRespuesta += "La cantidad no puede ser nula\n";}

                if (nombre == null || nombre.isBlank()) {
                    textoRespuesta += "El nombre no puede ser nulo o estar vacio\n";
                }
                if (nombreTalla == null) {
                    textoRespuesta += "La talla no puede ser nula\n";
                }
                if (categoria == null) {
                    textoRespuesta += "La categoria no puede ser nula.\n";
                }if (personalizable == null) {
                    textoRespuesta += "El campo es personalizable no puede ser nulo.\n";
                }
                if (!textoRespuesta.isEmpty()) {
                    textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
                } else {
                    this.ArticuloRepository.save(articulo);
                    textoRespuesta = "El artículo ha sido creado con éxito.";
                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Algún objeto es nulo\n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "verifique si la categoria o la talla ya se encuentran en la base de datos\n";
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

    public List<ArticuloModel> obtenerPorCategoria(String nombreCategoria) {
            List<ArticuloModel> articulosPorCategoria = new ArrayList<>();

            // Consulta la base de datos para obtener todos los artículos de la categoría especificada
            List<ArticuloModel> todosLosArticulos = ArticuloRepository.findAll();
            for (ArticuloModel articulo : todosLosArticulos) {
                if (articulo.getIdCategoria().getTipoRopa().equals(nombreCategoria)) {
                    articulosPorCategoria.add(articulo);
                }
            }

            return articulosPorCategoria;
        }

    @Override
    public String actualizarCantidadEnBd(OrdenModel ordenArticulo, ArticuloModel articulo) {

        Integer idArticulo = articulo.getIdArticulo();

        Optional<ArticuloModel> articuloEncontrado = ArticuloRepository.findById(idArticulo);

        ArticuloModel objA = new ArticuloModel();
        objA = articuloEncontrado.get();

        String textoRespuesta = "";
        int cantidadTotal = 0;

        Integer cantidadArticulo = 0;
        Integer cantidadOrden = 0;


        try{

            cantidadArticulo = objA.getCantidad();
            cantidadOrden = ordenArticulo.getCantidad();

            System.out.println("la Cantidad recuperada es:" + cantidadArticulo);
            if(cantidadArticulo == 0  || cantidadArticulo < 0){
                textoRespuesta = "No hay stock disponble para el articulo. ";
            }else if(cantidadOrden > cantidadArticulo){
                textoRespuesta = "La cantidad de la orden no puede superar a la del articulo";
            }else{

                cantidadTotal = cantidadArticulo - cantidadOrden;
                objA.setCantidad(cantidadTotal);
                this.ArticuloRepository.save(objA);
                textoRespuesta = "Se ha actualizado el proceso correctamente.";
            }

        }catch (NullPointerException e){
            textoRespuesta = "El producto no existe" + e.getLocalizedMessage();
        }catch (DataIntegrityViolationException e){
            textoRespuesta = "Un problema en el JSON, verifique";
        }
        return textoRespuesta;

    }
    }







//






