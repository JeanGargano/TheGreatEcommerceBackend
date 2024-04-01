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
    private Integer idEnvio;
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "idDepartamento")
    private DepartamentoModel idDepartamento;

    private String tipoEntrega;
}
