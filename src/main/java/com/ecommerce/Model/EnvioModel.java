package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table (name = "envio")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class EnvioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnvio;
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "idDepartamento")
    private DepartamentoModel idDepartamento;

    private String tipoEntrega;

    @ManyToOne
    @JoinColumn(name = "idOrden")
    private OrdenModel idOrden;
}
