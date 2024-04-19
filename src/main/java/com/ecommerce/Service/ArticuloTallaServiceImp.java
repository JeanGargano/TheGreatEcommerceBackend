package com.ecommerce.Service;


import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.ArticuloTallaModel;
import com.ecommerce.Model.TallaModel;
import com.ecommerce.Repository.IArticuloRepository;
import com.ecommerce.Repository.IArticuloTallaRepository;
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


public class ArticuloTallaServiceImp implements IArticuloTallaService{

    @Autowired
    IArticuloTallaRepository ArticuloTallaRepository;

    private List<ArticuloTallaModel> ArticulosTallaExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        ArticulosTallaExistentes = this.ArticuloTallaRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }
    public String crearArticuloTalla(ArticuloTallaModel articuloTalla) {
        String textoRespuesta = "";

        try {
            Integer cantidad = articuloTalla.getCantidad();
            TallaModel idTalla = articuloTalla.getIdTalla();
            ArticuloModel idArticulo = articuloTalla.getIdArticulo();

            ArticulosTallaExistentes = this.ArticuloTallaRepository.findAll();

            if (ArticulosTallaExistentes.isEmpty()) {
                this.ArticuloTallaRepository.save(articuloTalla);
                textoRespuesta = "El artículo talla ha sido creado con éxito.";
            } else {
                if (cantidad == null) {
                    textoRespuesta += "La cantidad no puede ser nula\n";
                }
                if (idTalla == null) {
                    textoRespuesta += "El ID de la talla no puede ser nulo\n";
                }
                if (idArticulo == null) {
                    textoRespuesta += "El ID del artículo no puede ser nulo\n";
                }
                if (!textoRespuesta.isEmpty()) {
                    textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
                } else {
                    this.ArticuloTallaRepository.save(articuloTalla);
                    textoRespuesta = "El artículo talla ha sido creado con éxito.";
                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Algún objeto es nulo\n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "El id que proporciona en alguna clase aun no ha sido creado\n";
        }

        return textoRespuesta;
    }

    public List<ArticuloTallaModel> listarArticuloTalla(){ return this.ArticuloTallaRepository.findAll();};
    public Optional<ArticuloTallaModel> obtenerArticuloTallaPorId(Integer idArticuloTalla){

        return this.ArticuloTallaRepository.findById(idArticuloTalla);

    };
    public String actualizarArticuloTallaPorId(ArticuloTallaModel articuloTalla, Integer idArticuloTalla){


        String textoRespuesta = "";

        // Verificamos si existe para actualizar.
        try {
            Optional<ArticuloTallaModel> articuloTallaEncontrado = this.ArticuloTallaRepository.findById(idArticuloTalla);

            if (articuloTallaEncontrado.isPresent()) {

                ArticuloTallaModel articuloTallaActualizar = articuloTallaEncontrado.get();

                BeanUtils.copyProperties(articuloTalla, articuloTallaActualizar);

                this.ArticuloTallaRepository.save(articuloTallaActualizar);

                return "El artículo talla con código: " + idArticuloTalla + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "El artículo talla con código: " + idArticuloTalla + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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
