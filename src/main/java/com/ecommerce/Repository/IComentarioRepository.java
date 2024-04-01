package com.ecommerce.Repository;

import com.ecommerce.Model.ComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComentarioRepository extends JpaRepository<ComentarioModel, Integer> {
}
