package com.ecommerce.Model;

import com.ecommerce.Model.Enums.Estado;
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
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "idDepartamento")
    private DepartamentoModel idDepartamento;

    private String tipoEntrega;

    @JoinColumn(name = "idArticulo")
    private Integer idArticulo;

    @JoinColumn(name = "cantidad")
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private Estado estado;




}
