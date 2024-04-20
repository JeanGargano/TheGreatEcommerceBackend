package com.ecommerce.Service;



import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.CategoriaModel;
import com.ecommerce.Model.Enums.TipoSexo;
import com.ecommerce.Model.TallaModel;
import com.ecommerce.Repository.ICategoriaRepository;
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

        try {
            TipoSexo tipoSexo = categoria.getTipoSexo();
            String tipoRopa = categoria.getTipoRopa();


            CategoriasExistentes = this.categoriaRepository.findAll();

            if (CategoriasExistentes.isEmpty()) {
                this.categoriaRepository.save(categoria);
                textoRespuesta = "La categoria ha sido creado con éxito.";
            } else {
                if (tipoSexo == null) {
                    textoRespuesta += "El tipo de sexo no puede ser nulo\n";
                }if(tipoRopa == null){
                    textoRespuesta += "El tipo de ropa no puede ser nulo\n";
                } else {
                    this.categoriaRepository.save(categoria);
                    textoRespuesta = "La categoria ha sido creado con éxito.";
                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Tiene errores en el json \n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "Corrija los errores en el json y vuelva a intentar\n";
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
        try {
            Optional<CategoriaModel> categoriaEncontrada = this.categoriaRepository.findById(idCategoria);

            if (categoriaEncontrada.isPresent()) {

                CategoriaModel categoriaActualizar = categoriaEncontrada.get();

                BeanUtils.copyProperties(categoria, categoriaActualizar);

                this.categoriaRepository.save(categoriaActualizar);

                return "La categoria con código: " + idCategoria + ", Ha sido actualizada con éxito.";

            } else {

                textoRespuesta = "La categoria con código: " + idCategoria + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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
