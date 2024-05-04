package com.ecommerce.Model.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModelDto {

    private String correo;
    private String contrasenia;
}
