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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idOrdenArticulo;

    @ManyToOne
    @JoinColumn(name = "idOrden")
    private OrdenModel idOrden;


    @JoinColumn(name = "idArticulo")
    private Integer idArticulo;

    private Integer cantidad;





}
