package com.ecommerce.Repository;

import com.ecommerce.Model.CiudadModel;
import com.ecommerce.Model.ComentarioDisenioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComentarioDisenioRepository extends JpaRepository<ComentarioDisenioModel, Integer> {
}
