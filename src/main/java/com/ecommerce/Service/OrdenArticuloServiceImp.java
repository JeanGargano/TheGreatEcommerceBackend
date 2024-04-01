package com.ecommerce.Service;

import com.ecommerce.Model.OrdenArticuloModel;
import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Repository.IOrdenArticuloRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary

public class OrdenArticuloServiceImp implements IOrdenArticuloService {

    @Autowired
    IOrdenArticuloRepository ordenArticuloRepository;

    private List<OrdenArticuloModel> ordenesArticulosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        ordenesArticulosExistentes= this.ordenArticuloRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearOrdenArticulo(OrdenArticuloModel ordenArticulo) {

        String textoRespuesta = "";

        ordenesArticulosExistentes = this.ordenArticuloRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(ordenesArticulosExistentes.isEmpty()){

            this.ordenArticuloRepository.save(ordenArticulo);

            textoRespuesta =  "La orden de articulo ha sido creada con éxito";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (OrdenArticuloModel i : ordenesArticulosExistentes) {
                if (ordenArticulo.getIdOrdenArticulo().equals(i.getIdOrdenArticulo())) {

                    textoRespuesta = "La orden de articulo con ID: " + ordenArticulo.getIdOrdenArticulo() + ", Ya se encuentra creada.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.ordenArticuloRepository.save(ordenArticulo);

                    textoRespuesta = "La orden de articulo ha sido creada con éxito";
                }
            }
        }
        return textoRespuesta;
    }

    @Override
    public List<OrdenArticuloModel> listarOrdenArticulo() {
        return this.ordenArticuloRepository.findAll();
    }

    @Override
    public Optional<OrdenArticuloModel> obtenerOrdenArticuloPorId(Integer idOrdenArticulo) {
        return this.ordenArticuloRepository.findById(idOrdenArticulo);
    }

    @Override
    public String actualizarOrdenArticulo(OrdenArticuloModel ordenArticulo, Integer idOrdenArticulo) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.

        Optional<OrdenArticuloModel> ordenArticuloEncontrada = this.ordenArticuloRepository.findById(idOrdenArticulo);

        if(ordenArticuloEncontrada.isPresent()){

            OrdenArticuloModel ordenArticuloActualizar = ordenArticuloEncontrada.get();

            BeanUtils.copyProperties(ordenArticulo, ordenArticuloActualizar);

            this.ordenArticuloRepository.save(ordenArticulo);

            return "La orden de articulo con id: " + idOrdenArticulo + ", Ha sido actualizada con exito.";

        }else{

            textoRespuesta = "La orden de articulo con id: "+ idOrdenArticulo + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
}
