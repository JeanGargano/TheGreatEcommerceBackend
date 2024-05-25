package com.ecommerce.Repository;

import com.ecommerce.Model.Enums.TipoUsuario;
import com.ecommerce.Model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Integer>{

    Optional<UsuarioModel> findUsuarioModelByCorreo(String correo);

    @Query("SELECT u.nombre, u.idUsuario FROM UsuarioModel u WHERE u.rol = :rol")
    List<Object[]> mostrarDiseñadores(@Param("rol") TipoUsuario rol);

}
