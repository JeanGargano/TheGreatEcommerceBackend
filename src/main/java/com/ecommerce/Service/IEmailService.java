package com.ecommerce.Service;
import com.ecommerce.Model.ArticuloModel;
import com.ecommerce.Model.OrdenModel;
import java.util.List;
public interface IEmailService {
    String enviarEmail(OrdenModel objO, Integer idUsuario, Double valorTotal, List<ArticuloModel> articulos);
}
