package com.ecommerce.Model.Dto;

import com.ecommerce.Model.Enums.Estado;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisenioDTO {


    private Integer idDisenio;
    private Integer idUsuario;

    @Enumerated(EnumType.STRING)
    private Estado estado;
    private Integer idPersonalizacion;

}
