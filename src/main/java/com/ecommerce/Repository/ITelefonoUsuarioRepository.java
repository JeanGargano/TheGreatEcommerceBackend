package com.ecommerce.Repository;

import com.ecommerce.Model.TelefonoUsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ITelefonoUsuarioRepository extends JpaRepository<TelefonoUsuarioModel, Integer> {
}
