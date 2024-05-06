package com.ecommerce.Model;

import com.ecommerce.Model.Dto.EnvioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.ManyToAny;
import org.springframework.web.bind.annotation.RequestBody;


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
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private UsuarioModel idUsuario;

    @Transient
    private EnvioDTO envio;

}
