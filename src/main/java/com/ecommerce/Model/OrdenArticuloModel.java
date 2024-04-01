package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ordenarticulo")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrdenArticuloModel {

    @Id
    private Integer idOrdenArticulo;

    @ManyToOne
    @JoinColumn(name = "idOrden")
    private OrdenModel idOrden;

    @ManyToOne
    @JoinColumn(name = "idArticulo")
    private ArticuloModel idArticulo;
}
