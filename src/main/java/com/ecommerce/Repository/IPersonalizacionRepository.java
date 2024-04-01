package com.ecommerce.Repository;

import com.ecommerce.Model.PersonalizacionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonalizacionRepository extends JpaRepository<PersonalizacionModel, Integer> {
}
