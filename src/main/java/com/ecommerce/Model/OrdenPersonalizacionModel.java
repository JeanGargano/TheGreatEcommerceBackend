package com.ecommerce.Model;

import com.ecommerce.Model.Enums.Estado;
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
    @JoinColumn(name="idOrden")
    private OrdenModel idOrden;

    @ManyToOne
    @JoinColumn(name = "idComentario")
    private ComentarioModel idComentario;

    @ManyToOne
    @JoinColumn(name="suDiseniador")
    private UsuarioModel suDiseniador;

    @JoinColumn(name = "imagenDisenio")
    private String imagenDisenio;

}
