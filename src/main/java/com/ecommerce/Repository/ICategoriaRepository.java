package com.ecommerce.Repository;

import com.ecommerce.Model.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaRepository extends JpaRepository <CategoriaModel, Integer> {
}
