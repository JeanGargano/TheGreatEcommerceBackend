package com.ecommerce.Service;

import com.ecommerce.Model.ArticuloDTO.ArticuloModelDTO;
import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenArticuloModel;
import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Repository.IArticuloRepository;
import com.ecommerce.Repository.IOrdenArticuloRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Primary

public class OrdenArticuloServiceImp implements IOrdenArticuloService {

    @Autowired
    IOrdenArticuloRepository ordenArticuloRepository;
    @Autowired
    IArticuloRepository articuloRepository;
    @Autowired
    IArticuloService articuloService;

    private List<OrdenArticuloModel> ordenesArticulosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        ordenesArticulosExistentes= this.ordenArticuloRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearOrdenArticulo(ArticuloModelDTO ordenArticulo) {


        List<ArticuloModel> articulos;

        articulos = ordenArticulo.getIdArticulo();

        String textoRespuesta = "";
        try {

            for(ArticuloModel i: articulos){

                if (i.getIdArticulo() == null ) {

                    textoRespuesta = "El id del Articulo no puede ser nulo o estar vacio\n";


                }
                else if (i.getCantidad() == null ) {
                    textoRespuesta = "La cantidad no puede ser nula o estar vacia\n";


                }

            }
            ordenesArticulosExistentes = this.ordenArticuloRepository.findAll();


            if (ordenesArticulosExistentes.isEmpty()) {

                Integer idArticulo = 0;
                Integer cantidad = 0;
                OrdenModel idOrden = ordenArticulo.getIdOrden();

                if(articulos.size() == 1){

                    OrdenArticuloModel objOA = new OrdenArticuloModel();
                    for(int i=0; i < articulos.size(); i++){

                        ArticuloModel objArticulo = articulos.get(i);
                        idArticulo = objArticulo.getIdArticulo();
                        cantidad = objArticulo.getCantidad();
                        objOA.setIdArticulo(idArticulo);
                        objOA.setIdOrden(idOrden);
                        objOA.setCantidad(cantidad);
                        this.ordenArticuloRepository.save(objOA);
                        articuloService.actualizarCantidadEnBd(objOA, objArticulo);

                        break;
                    }
                    textoRespuesta = "La OrdenArticulo ha sido creada con éxito.";

                }else{


                    for(int i=0; i< articulos.size(); i++){

                        OrdenArticuloModel objOA = new OrdenArticuloModel();

                        ArticuloModel objArticulo = articulos.get(i);

                        idArticulo = objArticulo.getIdArticulo();
                        cantidad = objArticulo.getCantidad();
                        objOA.setIdArticulo(idArticulo);
                        objOA.setIdOrden(idOrden);
                        objOA.setCantidad(cantidad);
                        this.ordenArticuloRepository.save(objOA);
                        articuloService.actualizarCantidadEnBd(objOA, objArticulo);

                    }
                    textoRespuesta = "La OrdenArticulo ha sido creada con éxito.";



                }

            } else {

                OrdenModel idOrden = ordenArticulo.getIdOrden();

                if (idOrden == null ) {
                    textoRespuesta += "El id de la Orden no puede ser nulo o estar vacio\n";
                }
                else if (!textoRespuesta.isEmpty()) {
                    textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";

                }else {
                    Integer idArticulo = 0;
                    Integer cantidad = 0;

                    for(int i=0; i<articulos.size(); i++){

                        OrdenArticuloModel objOA = new OrdenArticuloModel();
                        ArticuloModel objArticulo = articulos.get(i);
                        idArticulo = objArticulo.getIdArticulo();
                        cantidad = objArticulo.getCantidad();
                        objOA.setIdArticulo(idArticulo);
                        objOA.setIdOrden(idOrden);
                        objOA.setCantidad(cantidad);
                        this.ordenArticuloRepository.save(objOA);
                        articuloService.actualizarCantidadEnBd(objOA, objArticulo);


                    }

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
