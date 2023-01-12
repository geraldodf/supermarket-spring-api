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

    @Column(name = "produto_preco_compra")
    private BigDecimal precoCompra;

    @Column(name = "produto_preco_venda")
    private BigDecimal precoVenda;

    @Column(name = "produto_lucro_liquido")
    private BigDecimal lucroLiquido;

    @Column(name = "produto_quantidade")
    private Long quantidade;

    @Column(name = "produto_codigo_barras")
    private Long codigoBarras;

    @Column(name = "produto_data_criacao")
    private String dataCriacao;

    @ManyToOne
    @JoinColumn(name = "tipo_produto_fk")
    private TipoProduto tipoProduto;

    @Override
    public String toString() {

        return ("Descrição: " + this.descricao + "\n"
                +
                "Preço: " + this.precoVenda + "\n" +
                "Quantidade: " + this.quantidade + "\n" +
                "Código: " + this.codigoBarras + "\n" +
                "Data: " + this.dataCriacao + "\n");
    }

    public void verificarProduto() throws Exception {
        if (this.codigoBarras == null) {
            throw new ProdutoCodigoNuloException("O código está nulo.");
        }
        if (this.codigoBarras < 0) {
            throw new ProdutoCodigoInvalidoException("O código está nulo.");
        }
        if (this.descricao == null) {
            throw new ProdutoDescricaoNulaException(
                    "A descrição do produto está nula! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (this.descricao.length() <= 5) {
            throw new ProdutoDescricaInvalidaException(
                    "Descrição inválida! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (this.precoVenda == null) {
            throw new ProdutoPrecoDeVendaNuloException("O produto deve ter um preço de venda.");
        }
        if (this.precoCompra == null) {
            throw new ProdutoPrecoDeCompraNuloException("O produto deve ter um preço de compra.");
        }
        if (this.quantidade == null) {
            throw new ProdutoQuantidadeNulaException("Quantidade deve ser listada.");
        }
        if (this.lucroLiquido == null) {
            throw new ProdutoLucroNuloException("Lucro está nulo, houve problema.");
        }
        if (this.dataCriacao == null) {
            throw new ProdutoDataDeCriacaoNulaException("Erro ao gerar a Data.");
        }
        if (this.tipoProduto == null) {
            throw new ProdutoTipoDoProdutoNuloException("Tipo do produto inválido");
        }
        if (this.precoVenda.subtract(this.precoCompra).doubleValue() != this.lucroLiquido.doubleValue()) {
            throw new ProdutoLucroInconsistenteException("Valor do lucro está inconsistente.");
        }
    }

    public boolean verificarAtributosProdutoNaoNulo() {
        if (this.quantidade != null && this.precoVenda != null && this.lucroLiquido != null &&
                this.precoCompra != null && this.codigoBarras != null && this.descricao != null) {
            return true;
        } else
            return false;
    }

}
