package br.com.supermercado.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VendaDto {
    private List<Long> idProduto;
}
