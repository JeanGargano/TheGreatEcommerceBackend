package com.ecommerce.Repository;

import com.ecommerce.Model.EnvioModel;
import org.springframework.data.jpa.repository.JpaRepository;
public interface IEnvioRepository extends JpaRepository<EnvioModel, Integer> {
}
