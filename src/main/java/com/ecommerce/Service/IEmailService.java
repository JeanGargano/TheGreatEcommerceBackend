package com.ecommerce.Service;
import com.ecommerce.Model.OrdenModel;
public interface IEmailService {
    String enviarEmail(OrdenModel objO, Integer idUsuario, Double valorTotal);
}
