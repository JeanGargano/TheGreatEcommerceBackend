package com.ecommerce.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.Model.OrdenModel;

public interface IOrdenRepository extends JpaRepository<OrdenModel, Integer> {
}
