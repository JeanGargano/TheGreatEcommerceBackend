package com.ecommerce.Repository;

import com.ecommerce.Model.CiudadModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICiudadRepository extends JpaRepository<CiudadModel, Integer> {
}
