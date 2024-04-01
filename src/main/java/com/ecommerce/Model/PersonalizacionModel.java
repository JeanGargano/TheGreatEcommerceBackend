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
    private Integer idPersonalizacion;


    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;

    @ManyToOne
    @JoinColumn(name = "idDisenio")
    private DisenioModel idDisenio;

}
