package br.com.supermarket.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ProductDto {
    private String descricao;
    private BigDecimal precoDeCompra;
    private BigDecimal precoDeVenda;
    private Long quantidade;
    private Long codigo;
    private Long idTipoDoProduto;
}
