package com.ecommerce.Repository;

import com.ecommerce.Model.OrdenPersonalizacionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrdenPersonalizacionRepository extends JpaRepository<OrdenPersonalizacionModel, Integer> {

}
