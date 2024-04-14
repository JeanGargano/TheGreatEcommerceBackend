package com.ecommerce.Repository;

import com.ecommerce.Model.OrdenArticuloModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdenArticuloRepository extends JpaRepository<OrdenArticuloModel, Integer> {
}
