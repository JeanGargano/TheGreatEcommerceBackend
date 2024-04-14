package com.ecommerce.Service;


import com.ecommerce.Model.PersonalizacionModel;
import java.util.List;
import java.util.Optional;

public interface IPersonalizacionService {

    String crearPersonalizacion(PersonalizacionModel personalizacion);

    List<PersonalizacionModel> listarPersonalizacion();
    Optional<PersonalizacionModel> obtenerPersonalizacionPorId(Integer idPersonalizacion);
    String actualizarPersonalizacionPorId(PersonalizacionModel personalizacion, Integer idPersonalizacion);
}
