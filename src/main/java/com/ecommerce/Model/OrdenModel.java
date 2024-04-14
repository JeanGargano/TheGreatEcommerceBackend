package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table (name = "orden")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class OrdenModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrden;
    private String fecha;
    private Double valorTotal;
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;



}
