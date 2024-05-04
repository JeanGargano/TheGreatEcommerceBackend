package com.ecommerce.Model.Dto;
import java.util.List;
import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenModel;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ArticuloModelDTO  {

    private OrdenModel idOrden;

    @OneToMany
    private List<ArticuloModel> idArticulo;


}
