package com.ecommerce.Repository;

import com.ecommerce.Model.OrdenModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrdenRepository extends JpaRepository<OrdenModel, Integer> {
}
