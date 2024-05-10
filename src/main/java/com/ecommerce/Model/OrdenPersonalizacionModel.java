package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "ordenpersonalizacion")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class OrdenPersonalizacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrdenPersonalizacion;

    @ManyToOne
    @JoinColumn(name = "idorden")
    private OrdenModel idOrden;

    @ManyToOne
    @JoinColumn(name = "idPersonalizacion")
    private PersonalizacionModel idPersonalizacion;


    private String reciboPago;
}
