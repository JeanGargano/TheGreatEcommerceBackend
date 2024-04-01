package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "comentario")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class ComentarioModel {

    @Id
    private Integer idComentario;
    private String descripcion;
    private String fecha;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;
}
