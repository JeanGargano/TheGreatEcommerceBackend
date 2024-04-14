package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "telefonoUsuario")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TelefonoUsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTelefonoUsuario;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;
    private Integer telefono;
}
