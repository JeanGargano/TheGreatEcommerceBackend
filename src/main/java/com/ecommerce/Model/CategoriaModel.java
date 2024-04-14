package com.ecommerce.Model;
import com.ecommerce.Model.Enums.TipoSexo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "categoria")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class CategoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    @Column(name= "tipoSexo")
    @Enumerated(EnumType.STRING)
    private TipoSexo tipoSexo;

    private String tipoRopa;
}