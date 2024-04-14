package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "personalizacion")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class PersonalizacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersonalizacion;


    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;

    @ManyToOne
    @JoinColumn(name = "idArticulo")
    private ArticuloModel idArticulo;

    @ManyToOne
    @JoinColumn(name = "idComentario")
    private ComentarioModel idComentario;

}
