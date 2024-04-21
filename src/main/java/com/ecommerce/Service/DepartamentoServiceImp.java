package com.ecommerce.Service;

import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.CategoriaModel;
import com.ecommerce.Model.CiudadModel;
import com.ecommerce.Model.DepartamentoModel;
import com.ecommerce.Repository.IDepartamentoRepository;
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

public class DepartamentoServiceImp implements IDepartamentoService{

    @Autowired
    IDepartamentoRepository departamentoRepository;

    private List<DepartamentoModel> departamentosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        departamentosExistentes = this.departamentoRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }
    @Override
    public String crearDepartamento(DepartamentoModel departamento) {

        String textoRespuesta = "";
        try {

            String nombre = departamento.getNombre();

            departamentosExistentes = this.departamentoRepository.findAll();

            if (departamentosExistentes.isEmpty()) {
                this.departamentoRepository.save(departamento);
                textoRespuesta = "El departamento ha sido creado con éxito.";
            } else {
                if (nombre == null || nombre.isBlank()) {
                    textoRespuesta += "El nombre no puede ser nulo o estar vacio\n";
                }
                if (!textoRespuesta.isEmpty()) {
                    textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
                }else {
                    this.departamentoRepository.save(departamento);
                    textoRespuesta = "El departamento ha sido creado con éxito.";
                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Algún objeto es nulo\n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "verifique si la categoria ya se encuentra en la base de datos\n";
        }
        return textoRespuesta;
    }

    @Override
    public List<DepartamentoModel> listarDepartamento() {
        return this.departamentoRepository.findAll();
    }

    @Override
    public Optional<DepartamentoModel> obtenerDepartamentoPorId(Integer idDepartamento) {
        return this.departamentoRepository.findById(idDepartamento);
    }

    @Override
    public String actualizarDepartamentoPorId(DepartamentoModel departamento, Integer idDepartamento) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.
        try {
            Optional<DepartamentoModel> departamentoEncontrado = this.departamentoRepository.findById(idDepartamento);

            if (departamentoEncontrado.isPresent()) {

                DepartamentoModel departamentoActualizar = departamentoEncontrado.get();

                BeanUtils.copyProperties(departamento, departamentoActualizar);

                this.departamentoRepository.save(departamentoActualizar);

                return "El departamento con código: " + idDepartamento + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "El departamneto con código: " + idDepartamento + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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

