package com.ecommerce.Service;

import com.ecommerce.Model.*;
import com.ecommerce.Repository.IEnvioRepository;
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

public class EnvioServiceImp implements IEnvioService{

    @Autowired
    IEnvioRepository envioRepository;

    private List<EnvioModel> enviosExistentes; // Se crea para mantener actualizado los datos entre bd y api

    @PostConstruct
    // PostConstructor, sirve para dar una INDICACIÓN luego de inicializar el main, por lo que al ejecutar esto se ejecutara.
    public void init(){ // Método públic, no devuelve nada (void) y de tipo init (inicializar algo)

        enviosExistentes= this.envioRepository.findAll(); // Aca toma todas los articulos de la BD y las mete en el List.

    }

    @Override
    public String crearEnvio(EnvioModel envio) {

        String textoRespuesta = "";
        try {
            String direccion = envio.getDireccion();
            DepartamentoModel idDepartamento = envio.getIdDepartamento();
            String tipoEntrega = envio.getTipoEntrega();
            OrdenModel idOrden = envio.getIdOrden();
            enviosExistentes = this.envioRepository.findAll();

            if (enviosExistentes.isEmpty()) {
                this.envioRepository.save(envio);
                textoRespuesta = "El envio ha sido creado con éxito.";
            } else {
                if (direccion == null || direccion.isBlank()) {
                    textoRespuesta += "La direccion no puede ser nula o estar vacia\n";
                }
                if (idDepartamento == null) {
                    textoRespuesta += "La id de Departamento no puede ser nula o estar vacia\n";
                }
                if (tipoEntrega == null || tipoEntrega.isBlank()) {
                    textoRespuesta += "El tipo de entrega no puede ser nulo o estar vacio\n";
                }
                if (idOrden == null ) {
                    textoRespuesta += "La id de Orden no puede ser nula o estar vacia\n";
                }
                if (!textoRespuesta.isEmpty()) {
                    textoRespuesta += "Por favor, corrija los problemas y vuelva a intentarlo.\n";
                }else {
                    this.envioRepository.save(envio);
                    textoRespuesta = "El envio ha sido creado con éxito.";
                }
            }
        } catch (NullPointerException e) {
            textoRespuesta += "Algún objeto es nulo\n";
        } catch (UncheckedIOException e) {
            textoRespuesta += "Errores\n";
        } catch (DataIntegrityViolationException e) {
            textoRespuesta += "verifique si el objeto ya se encuentra en la base de datos\n";
        }
        return textoRespuesta;
  }

    @Override
    public List<EnvioModel> listarEnvio() {
        return this.envioRepository.findAll();
    }

    @Override
    public Optional<EnvioModel> obtenerEnvioPorId(Integer idEnvio) {
        return this.envioRepository.findById(idEnvio);
    }

    @Override
    public String actualizarEnvioPorId(EnvioModel envio, Integer idEnvio) {

        String textoRespuesta = "";

        // Verificamos si existe para actualizar.
        try {
            Optional<EnvioModel> envioEncontrado = this.envioRepository.findById(idEnvio);

            if (envioEncontrado.isPresent()) {

                EnvioModel envioActualizar = envioEncontrado.get();

                BeanUtils.copyProperties(envio, envioActualizar);

                this.envioRepository.save(envioActualizar);

                return "El envio con código: " + idEnvio + ", Ha sido actualizado con éxito.";

            } else {

                textoRespuesta = "El envio con código: " + idEnvio + ", No existe en el sistema. Por ende el proceso no se realizo correctamente.";
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
