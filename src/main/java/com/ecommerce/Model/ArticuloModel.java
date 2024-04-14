package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "articulo")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class ArticuloModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idArticulo;
    private String imagen;
    private String descripcion;
    private String nombre;
    private Integer precio;


    @ManyToOne
    @JoinColumn(name= "idCategoria")
    private CategoriaModel idCategoria;

    private Boolean esPersonalizable;



}
