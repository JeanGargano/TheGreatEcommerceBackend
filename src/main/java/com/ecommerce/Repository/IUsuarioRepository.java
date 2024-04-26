package com.ecommerce.Repository;

import com.ecommerce.Model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer>{

    Optional<UsuarioModel> findUsuarioModelByCorreo(String correo);
}
