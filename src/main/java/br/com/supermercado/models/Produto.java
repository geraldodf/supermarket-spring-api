package br.com.supermercado.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @Column(name = "produto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "produto_descricao")
    private String descricao;

    @Column(name = "produto_preco_de_compra")
    private BigDecimal precoDeCompra;

    @Column(name = "produto_preco_de_venda")
    private BigDecimal precoDeVenda;

    @Column(name = "lucro_liquido")
    private BigDecimal lucroLiquido;

    @Column(name = "produto_quantidade")
    private Long quantidade;

    @Column(name = "produto_codigo")
    private Long codigo;

    @Column(name = "produto_data_criacao")
    private String dataDeCriacao;

    @ManyToOne
    @JoinColumn(name = "tipo_do_produto_fk")
    private TipoDoProduto tipoDoProduto;



}
