package com.ecommerce.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ecommerce.Model.ArticuloDTO.ArticuloModelDTO;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.ManyToAny;

import java.util.List;

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
