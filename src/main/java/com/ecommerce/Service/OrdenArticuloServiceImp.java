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
        try {

            OrdenModel idOrden = ordenArticulo.getIdOrden();
            ArticuloModel idArticulo = ordenArticulo.getIdArticulo();
            Integer cantidad = ordenArticulo.getCantidad();

            ordenesArticulosExistentes = this.ordenArticuloRepository.findAll();

            if (ordenesArticulosExistentes.isEmpty()) {
                this.ordenArticuloRepository.save(ordenArticulo);
                textoRespuesta = "La OrdenArticulo ha sido creada con éxito.";
            } else {
                if (idOrden == null ) {
                    textoRespuesta += "El id de la Orden no puede ser nulo o estar vacio\n";
                }
                if (idArticulo == null ) {
                    textoRespuesta += "El id del Articulo no puede ser nulo o estar vacio\n";

                }
                if (cantidad == null ) {
                    textoRespuesta += "La cantidad no puede ser nula o estar vacia\n";

                }
                if (!textoRespuesta.isEmpty()) {
                    textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
                }else {
                    this.ordenArticuloRepository.save(ordenArticulo);
                    textoRespuesta = "La OrdenArticulo ha sido creada con éxito.";
                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Algún objeto es nulo\n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "verifique el JSON\n";
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
