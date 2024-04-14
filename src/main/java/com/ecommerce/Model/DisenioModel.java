package com.ecommerce.Model;

import com.ecommerce.Model.Enums.Estado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "disenio")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class DisenioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDisenio;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;

    @Column(name= "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;


    @ManyToOne
    @JoinColumn(name = "idPersonalizacion")
    private PersonalizacionModel idPersonalizacion;
}