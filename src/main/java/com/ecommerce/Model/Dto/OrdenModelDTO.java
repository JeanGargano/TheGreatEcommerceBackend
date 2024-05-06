package com.ecommerce.Model.Dto;
import java.util.List;
import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.DepartamentoModel;
import com.ecommerce.Model.OrdenModel;
import com.ecommerce.Model.UsuarioModel;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class OrdenModelDTO {



    private String fecha;
    private Double valorTotal;
    private String direccion;

    private DepartamentoModel idDepartamento;
    private String tipoEntrega;

    private UsuarioModel idUsuario;

    @OneToMany
    private List<ArticuloModel> idArticulo;


}
