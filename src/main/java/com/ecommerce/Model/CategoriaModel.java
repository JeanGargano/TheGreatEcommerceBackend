package com.ecommerce.Model;
import com.ecommerce.Model.Enums.TipoSexo;
import com.ecommerce.Model.Enums.TipoRopa;
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
    private Integer idCategoria;

    @Column(name= "tipoSexo")
    @Enumerated(EnumType.STRING)
    private TipoSexo tipoSexo;

    @Column(name= "tipoRopa")
    @Enumerated(EnumType.STRING)
    private TipoRopa tipoRopa;
}