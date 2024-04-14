package com.ecommerce.Service;

import com.ecommerce.Model.DepartamentoModel;
import com.ecommerce.Repository.IDepartamentoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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

        departamentosExistentes = this.departamentoRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.

        if(departamentosExistentes.isEmpty()){

            this.departamentoRepository.save(departamento);

            textoRespuesta =  "El departamento ha sido creado con éxito.";

        }else {
            // Verificamos si el articulo existe (Para evitar duplicados)
            for (DepartamentoModel i : departamentosExistentes) {
                if (departamento.getIdDepartamento().equals(i.getIdDepartamento())) {

                    textoRespuesta = "El departamento con ID: " + departamento.getIdDepartamento() + ", Ya se encuentra creado.";
                    // No es necesario continuar verificando una vez que se encuentra un área existente
                } else {

                    this.departamentoRepository.save(departamento);

                    textoRespuesta = "El departamento ha sido creado con éxito.";
                }
            }
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

        Optional<DepartamentoModel> departamentoEncontrado = this.departamentoRepository.findById(idDepartamento);

        if(departamentoEncontrado.isPresent()){

            DepartamentoModel departamentoActualizar = departamentoEncontrado.get();

            BeanUtils.copyProperties(departamento, departamentoActualizar);

            this.departamentoRepository.save(departamentoActualizar);

            return "El departamento con id: " + idDepartamento + ", Ha sido actualizado con éxito.";

        }else{

            textoRespuesta = "El departamento con id: "+ idDepartamento + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
        }

        return textoRespuesta;
    }
    }

