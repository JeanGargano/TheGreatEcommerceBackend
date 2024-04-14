package com.ecommerce.Service;



import com.ecommerce.Model.CategoriaModel;
import com.ecommerce.Repository.ICategoriaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary

public class CategoriaServiceImp implements ICategoriaService{

    @Autowired
    ICategoriaRepository categoriaRepository;

    private List<CategoriaModel> CategoriasExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        CategoriasExistentes = this.categoriaRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }


    @Override
    public String crearCategoria(CategoriaModel categoria) {
        String textoRespuesta = "";

        CategoriasExistentes = this.categoriaRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(CategoriasExistentes.isEmpty()){

            this.categoriaRepository.save(categoria);

            textoRespuesta = "La categoria ha sido creado con éxito.";

        }else {

            this.categoriaRepository.save(categoria);
            textoRespuesta = "La categoria ha sido creada con éxito.";
        }

        return textoRespuesta;
    }

    @Override
    public List<CategoriaModel> listarCategoria() {
        return this.categoriaRepository.findAll();
    }

    @Override
    public Optional<CategoriaModel> obtenerCategoriaPorId(Integer idCategoria) {
        return this.categoriaRepository.findById(idCategoria);
    }


    @Override
    public String actualizarCategoriaporId(CategoriaModel categoria, Integer idCategoria) {
        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<CategoriaModel> categoriaEncontrada = this.categoriaRepository.findById(idCategoria);

        if(categoriaEncontrada.isPresent()){

            CategoriaModel categoriaActualizar = categoriaEncontrada.get();

            BeanUtils.copyProperties(categoria, categoriaActualizar);

            this.categoriaRepository.save(categoriaActualizar);

            return "La categoria con ID: " + idCategoria + ", Ha sido actualizado con éxito.";

        }else{

            textoRespuesta = "La categoria con ID: "+ idCategoria + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }

}
