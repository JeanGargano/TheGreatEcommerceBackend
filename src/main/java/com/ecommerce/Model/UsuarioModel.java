package com.ecommerce.Model;

import com.ecommerce.Model.Enums.TipoSexo;
import com.ecommerce.Model.Enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "usuario")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioModel {
    @Id
    private Integer idUsuario;
    private String nombre;
    private Integer telefono;
    private String correo;
    private String direccion;

    @Column(name= "rol")
    @Enumerated(EnumType.STRING)
    private TipoUsuario rol;

    @Column(name= "sexo")
    @Enumerated(EnumType.STRING)
    private TipoSexo Sexo;

    private Integer identificacion;



}