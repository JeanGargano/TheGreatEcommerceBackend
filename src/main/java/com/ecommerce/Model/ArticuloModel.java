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
    private Integer idArticulo;
    private String imagen;
    private String descripcion;
    private String nombre;
    private Integer precio;
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name= "idCategoria")
    private CategoriaModel idCategoria;



}
