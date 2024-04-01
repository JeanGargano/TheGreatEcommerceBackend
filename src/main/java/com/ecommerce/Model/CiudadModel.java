package com.ecommerce.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "ciudad")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class CiudadModel {

    @Id
    private Integer idCiudad;
    private String nombre;
}
