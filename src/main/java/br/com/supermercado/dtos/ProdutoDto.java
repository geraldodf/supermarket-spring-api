package br.com.supermercado.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ProdutoDto {
    private String descricao;
    private BigDecimal precoDeCompra;
    private BigDecimal precoDeVenda;
    private Long quantidade;
    private Long codigo;
    private Long idTipoDoProduto;
}
