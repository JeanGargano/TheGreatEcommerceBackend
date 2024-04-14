package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "articulotalla")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class ArticuloTallaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idArticuloTalla;

    @ManyToOne
    @JoinColumn(name = "idArticulo")
    private ArticuloModel idArticulo;

    @ManyToOne
    @JoinColumn(name = "idTalla")
    private TallaModel idTalla;

    private Integer cantidad;

}
