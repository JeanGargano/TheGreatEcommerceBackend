package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "departamento")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class DepartamentoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDepartamento;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "idCiudad")
    private CiudadModel idCiudad;
}
