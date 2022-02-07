package br.com.supermercado.Dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoDto {

    private Long id;
    private String descricao;
    private BigDecimal precoDeCompra;
    private BigDecimal precoDeVenda;
    private Long quantidade;
    private Long codigo;

}
