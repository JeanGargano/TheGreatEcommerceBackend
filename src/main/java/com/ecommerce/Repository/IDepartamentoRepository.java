package com.ecommerce.Repository;

import com.ecommerce.Model.DepartamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDepartamentoRepository extends JpaRepository<DepartamentoModel, Integer> {
}
