package br.com.supermercado.models;

import lombok.Getter;
import lombok.Setter;
import br.com.supermercado.exceptions.*;
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

     @Override
    public String toString() {

        return ("Descrição: " + this.descricao + "\n"
                +
                "Preço: " + this.precoDeVenda + "\n" +
                "Quantidade: " + this.quantidade + "\n" +
                "Código: " + this.codigo + "\n" +
                "Data: " + this.dataDeCriacao + "\n");
    }

    public void verificarProduto(Produto produto) throws Exception {
        if (produto.getCodigo() == null) {
            throw new ProdutoCodigoNuloException("O código está nulo.");
        }
        if (produto.getDescricao() == null) {
            throw new ProdutoDescricaoNulaException(
                    "A descrição do produto está nula! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (produto.getDescricao().length() <= 5) {
            throw new ProdutoDescricaInvalidaException(
                    "Descrição inválida! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (produto.getPrecoDeVenda() == null) {
            throw new ProdutoPrecoDeVendaNuloException("O produto deve ter um preço de venda.");
        }
        if (produto.getPrecoDeCompra() == null) {
            throw new ProdutoPrecoDeCompraNuloException("O produto deve ter um preço de compra.");
        }
        if (produto.getQuantidade() == null) {
            throw new ProdutoQuantidadeNulaException("Quantidade deve ser listada.");
        }
        if (produto.getLucroLiquido() == null) {
            throw new ProdutoLucroNuloException("Quantidade deve ser listada.");
        }
        if (produto.getDataDeCriacao() == null) {
            throw new ProdutoDataDeCriacaoNulaException("Erro ao gerar a Data.");
        }
        if (produto.getTipoDoProduto() == null) {
            throw new ProdutoTipoDoProdutoNuloException("Tipo do produto inválido");
        }
        if (produto.getPrecoDeVenda().subtract(produto.getPrecoDeCompra()).doubleValue() != produto.getLucroLiquido()
                .doubleValue()) {
            throw new ProdutoLucroInconsistenteException("Valor do lucro está inconsistente.");
        }
    }

    public boolean verificarAtributosProdutoNaoNulo(Produto produto) {
        if (produto.getQuantidade() != null && produto.getPrecoDeVenda() != null && produto.getLucroLiquido() != null &&
                produto.getPrecoDeCompra() != null && produto.getCodigo() != null && produto.getDescricao() != null) {
            return true;
        } else
            return false;
    }


}
