package com.ecommerce.Service;

import com.ecommerce.Model.DisenioModel;
import com.ecommerce.Model.Dto.DisenioDTO;
import com.ecommerce.Model.Enums.Estado;
import com.ecommerce.Model.UsuarioModel;
import com.ecommerce.Repository.IDisenioRepository;
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

public class DisenioServiceImp implements IDisenioService{

    @Autowired
    IDisenioRepository disenioRepository;

    private List<DisenioModel> diseniosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        diseniosExistentes= this.disenioRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearDisenio(DisenioModel diseno) {

        String textoRespuesta = "";

        UsuarioModel idUsuario = diseno.getIdUsuario();
        Estado estado = diseno.getEstado();


        diseniosExistentes = this.disenioRepository.findAll(); // Actualiza cada vez por si se agrego otra anteriormente.


        if (idUsuario == null) {
            textoRespuesta = "El id del usuario no puede ser nulo";
        } else if (estado == null) {
            textoRespuesta = "El estado no puede ser nulo";
        }else{
            if(diseniosExistentes.isEmpty()) {

                this.disenioRepository.save(diseno);

                textoRespuesta = "El diseño ha sido creado con éxito.";


            }else {
                this.disenioRepository.save(diseno);
                textoRespuesta = "El diseño ha sido creado con éxito.";
            }
        }
        return textoRespuesta;
    }

    @Override
    public List<DisenioModel> listarDisenio() {
        return this.disenioRepository.findAll();
    }

    @Override
    public Optional<DisenioDTO> obtenerDisenioPorId(Integer idDisenio) {


        Integer idDiseño = 0;
        Integer idUsuario = 0;
        Estado estadoDiseño;
        Integer idPersonalizacion = 0;

        DisenioDTO objD = new DisenioDTO();

        Optional<DisenioModel> DisenioEncontrado = this.disenioRepository.findById(idDisenio);

        if(DisenioEncontrado.isPresent()){

            idDiseño = DisenioEncontrado.get().getIdDisenio();
            idUsuario = DisenioEncontrado.get().getIdUsuario().getIdUsuario();
            estadoDiseño = DisenioEncontrado.get().getEstado();

            objD.setIdDisenio(idDiseño);
            objD.setIdUsuario(idUsuario);
            objD.setEstado(estadoDiseño);
            objD.setIdPersonalizacion(idPersonalizacion);

            return Optional.of(objD);


        }
        return Optional.of(objD);
    }

    @Override
    public String actualizarDisenioPorId(DisenioModel disenio, Integer idDisenio) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.
        try {
            Optional<DisenioModel> disenoEncontrado = this.disenioRepository.findById(idDisenio);

            if (disenoEncontrado.isPresent()) {

                DisenioModel disenioActualizar = disenoEncontrado.get();

                BeanUtils.copyProperties(disenio, disenioActualizar);

                this.disenioRepository.save(disenioActualizar);

                return "El diseño con código: " + idDisenio + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "El artículo con código: " + idDisenio + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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
