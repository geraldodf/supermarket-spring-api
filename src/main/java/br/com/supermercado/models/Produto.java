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

    public void verificarProduto() throws Exception {
        if (this.codigo == null) {
            throw new ProdutoCodigoNuloException("O código está nulo.");
        }
        if (this.codigo < 0) {
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
        if (this.precoDeVenda == null) {
            throw new ProdutoPrecoDeVendaNuloException("O produto deve ter um preço de venda.");
        }
        if (this.precoDeCompra == null) {
            throw new ProdutoPrecoDeCompraNuloException("O produto deve ter um preço de compra.");
        }
        if (this.quantidade == null) {
            throw new ProdutoQuantidadeNulaException("Quantidade deve ser listada.");
        }
        if (this.lucroLiquido == null) {
            throw new ProdutoLucroNuloException("Lucro está nulo, houve problema.");
        }
        if (this.dataDeCriacao == null) {
            throw new ProdutoDataDeCriacaoNulaException("Erro ao gerar a Data.");
        }
        if (this.tipoDoProduto == null) {
            throw new ProdutoTipoDoProdutoNuloException("Tipo do produto inválido");
        }
        if (this.precoDeVenda.subtract(this.precoDeCompra).doubleValue() != this.lucroLiquido.doubleValue()) {
            throw new ProdutoLucroInconsistenteException("Valor do lucro está inconsistente.");
        }
    }

    public boolean verificarAtributosProdutoNaoNulo() {
        if (this.quantidade != null && this.precoDeVenda != null && this.lucroLiquido != null &&
                this.precoDeCompra != null && this.codigo != null && this.descricao != null) {
            return true;
        } else
            return false;
    }

}
