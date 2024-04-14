package com.ecommerce.Service;


import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.ArticuloTallaModel;
import com.ecommerce.Repository.IArticuloRepository;
import com.ecommerce.Repository.IArticuloTallaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
    public String crearArticuloTalla(ArticuloTallaModel articuloTalla){



        String textoRespuesta = "";

        ArticulosTallaExistentes = this.ArticuloTallaRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(ArticulosTallaExistentes.isEmpty()){

            this.ArticuloTallaRepository.save(articuloTalla);
            textoRespuesta =  "La talla al artículo con ID: " + articuloTalla.getIdArticulo() +" ,ha sido creado con éxito.";

        }else {


            // Verificamos si el articulo existe (Para evitar duplicados)
            for (ArticuloTallaModel i : ArticulosTallaExistentes) {
                if (articuloTalla.getIdArticuloTalla().equals(i.getIdArticuloTalla())) {

                    textoRespuesta = "La talla del articulo con ID: " + articuloTalla.getIdArticuloTalla() + ", Ya se encuentra creado.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.ArticuloTallaRepository.save(articuloTalla);
                    textoRespuesta = "La talla al artículo con ID: " + articuloTalla.getIdArticulo() + " ,ha sido creado con éxito.";
                }
            }
        }
        return textoRespuesta;

    };

    public List<ArticuloTallaModel> listarArticuloTalla(){ return this.ArticuloTallaRepository.findAll();};
    public Optional<ArticuloTallaModel> obtenerArticuloTallaPorId(Integer idArticuloTalla){

        return this.ArticuloTallaRepository.findById(idArticuloTalla);

    };
    public String actualizarArticuloTallaPorId(ArticuloTallaModel articuloTalla, Integer idArticuloTalla){


        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<ArticuloTallaModel> articuloTallaEncontrado = this.ArticuloTallaRepository.findById(idArticuloTalla);

        if(articuloTallaEncontrado.isPresent()){

            ArticuloTallaModel articuloActualizar = articuloTallaEncontrado.get();

            BeanUtils.copyProperties(articuloTalla, articuloActualizar);

            this.ArticuloTallaRepository.save(articuloActualizar);

            return "La talla del artículo con ID: " + articuloTalla.getIdArticulo()  + ", Ha sido actualizado con éxito.";

        }else{

            textoRespuesta = "La talla del artículo con código: "+ idArticuloTalla + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;


    };
}
