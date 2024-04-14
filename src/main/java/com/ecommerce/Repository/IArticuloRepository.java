package com.ecommerce.Repository;
import com.ecommerce.Model.ArticuloModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IArticuloRepository extends JpaRepository<ArticuloModel, Integer> {
}
