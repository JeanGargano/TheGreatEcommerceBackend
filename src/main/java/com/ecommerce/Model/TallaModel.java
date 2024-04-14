package com.ecommerce.Model;

import com.ecommerce.Model.Enums.Talla;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "talla")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class TallaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTalla;
    @Column(name= "talla")
    @Enumerated(EnumType.STRING)
    private Talla talla;
    private Integer cantidad;


}
