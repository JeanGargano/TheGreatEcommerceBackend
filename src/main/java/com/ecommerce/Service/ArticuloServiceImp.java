package com.ecommerce.Service;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Repository.IArticuloRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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

        ArticulosExistentes = this.ArticuloRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(ArticulosExistentes.isEmpty()){

            this.ArticuloRepository.save(articulo);

            textoRespuesta =  "El artículo ha sido creado con éxito.";
            System.out.println("Anda entrando aca");

        } else {
            this.ArticuloRepository.save(articulo);
            textoRespuesta = "El artículo ha sido creado con éxito.";
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

        Optional<ArticuloModel> articuloEncontrado = this.ArticuloRepository.findById(idArticulo);

        if(articuloEncontrado.isPresent()){

            ArticuloModel articuloActualizar = articuloEncontrado.get();

            BeanUtils.copyProperties(articulo, articuloActualizar);

            this.ArticuloRepository.save(articuloActualizar);

            return "El artículo con código: " + idArticulo + ", Ha sido actualizado con éxito.";

        }else{

            textoRespuesta = "El artículo con código: "+ idArticulo + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }

}


//






