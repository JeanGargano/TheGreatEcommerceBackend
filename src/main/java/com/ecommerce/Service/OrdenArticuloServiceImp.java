package com.ecommerce.Service;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenArticuloModel;
import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Repository.IOrdenArticuloRepository;
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
    public String crearArticulo(OrdenArticuloModel ordenArticulo) {

        String textoRespuesta = "";

        OrdenModel idOrden = ordenArticulo.getIdOrden();
        ArticuloModel idArtticulo = ordenArticulo.getIdArticulo();
        Integer cantidad = ordenArticulo.getCantidad();


        ordenesArticulosExistentes = this.ordenArticuloRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(ordenesArticulosExistentes.isEmpty()){

            this.ordenArticuloRepository.save(ordenArticulo);

            textoRespuesta =  "La orden de artículo ha sido creado con éxito.";
            System.out.println("Anda entrando aca");

        } else {
            if (idOrden == null) {
                textoRespuesta = "El id de su orden no puede ser nulo";
            } else if (idArtticulo == null ) {
                textoRespuesta = "El id de su articulo no puede ser nulo";
            } else if (cantidad == null) {
                textoRespuesta = "La cantidad no puede ser nula o estar vacia";
            } else {
                this.ordenArticuloRepository.save(ordenArticulo);
                textoRespuesta = "La orden de articulo ha sido creado con éxito.";
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
        try {
            Optional<OrdenArticuloModel> ordenArticuloEncontrada = this.ordenArticuloRepository.findById(idOrdenArticulo);

            if (ordenArticuloEncontrada.isPresent()) {

                OrdenArticuloModel ordenArticuloActualizar = ordenArticuloEncontrada.get();

                BeanUtils.copyProperties(ordenArticulo, ordenArticuloActualizar);

                this.ordenArticuloRepository.save(ordenArticuloActualizar);

                return "La orden de artículo con código: " + idOrdenArticulo + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "La orden de artículo con código: " + idOrdenArticulo + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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
