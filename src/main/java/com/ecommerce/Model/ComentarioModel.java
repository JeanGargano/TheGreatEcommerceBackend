package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table (name = "comentario")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class ComentarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComentario;
    private String descripcion;
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;
}
