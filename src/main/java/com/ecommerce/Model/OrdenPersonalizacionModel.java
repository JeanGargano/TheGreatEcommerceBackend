package com.ecommerce.Model;

import com.ecommerce.Model.Enums.EstadoPersonalizacion;
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

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoPersonalizacion estado;
    private String reciboPago;
}
