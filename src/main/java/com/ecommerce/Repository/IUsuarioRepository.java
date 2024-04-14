package com.ecommerce.Repository;

import com.ecommerce.Model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer>{
}
