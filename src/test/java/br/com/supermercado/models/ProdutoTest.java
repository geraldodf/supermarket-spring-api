package br.com.supermercado.models;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import br.com.supermercado.exceptions.*;
import br.com.supermercado.exceptions.ProdutoDescricaInvalidaException;

@SpringBootTest
class ProdutoTest {

    @Test
    void verificarProdutoDeveRetornarOk() throws Exception {

        Produto produto = produtoCriado();

        produto.verificarProduto();

        Assert.assertEquals(123L, produto.getCodigoBarras(), 0);
        Assert.assertEquals("Hoje", produto.getDataCriacao());
        Assert.assertEquals("Teste Descricao", produto.getDescricao());
        Assert.assertEquals(BigDecimal.valueOf(1.00), produto.getLucroLiquido());
        Assert.assertEquals(BigDecimal.valueOf(3.00), produto.getPrecoVenda());
        Assert.assertEquals(BigDecimal.valueOf(2.00), produto.getPrecoCompra());
        Assert.assertEquals(10L, produto.getQuantidade(), 0);
        Assert.assertNotNull(produto.getTipoProduto());
    }

    @Test
    void verificarProdutoNaoNuloDoProdutoCriadoNaoDeveLancarExcecao() {
        Produto produto = produtoCriado();
        produto.verificarAtributosProdutoNaoNulo();
    }

    @Test
    void verificarProdutoDeveLancarProdutoDescricaoInvalidaException() {
        Produto produto = produtoCriado();
        produto.setDescricao("");
        Assert.assertThrows(ProdutoDescricaInvalidaException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoDeveLancarProdutoDescricaoInvalidaExceptionCom5Caracteres() {
        Produto produto = produtoCriado();
        produto.setDescricao("Teste");
        Assert.assertThrows(ProdutoDescricaInvalidaException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoDeveLancarProdutoDescricaoNulaException() {
        Produto produto = produtoCriado();
        produto.setDescricao(null);
        Assert.assertThrows(ProdutoDescricaoNulaException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoDeveLancarProdutoCodigoNuloException() {
        Produto produto = produtoCriado();
        produto.setCodigoBarras(null);
        Assert.assertThrows(ProdutoCodigoNuloException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoDeveLancarProdutoCodigoInvalidoException() {
        Produto produto = produtoCriado();
        produto.setCodigoBarras(-213L);
        Assert.assertThrows(ProdutoCodigoInvalidoException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoDeveLancarProdutoDataDeCriacaoNulaException() {
        Produto produto = produtoCriado();
        produto.setDataCriacao(null);
        Assert.assertThrows(ProdutoDataDeCriacaoNulaException.class, () -> produto.verificarProduto());

    }

    @Test
    void verificarProdutoDeveLancarProdutoLucroNuloException() {
        Produto produto = produtoCriado();
        produto.setLucroLiquido(null);
        Assert.assertThrows(ProdutoLucroNuloException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoDeveLancarProdutoLucroInconsistenteException() {
        Produto produto = produtoCriado();
        produto.setLucroLiquido(BigDecimal.valueOf(5000));
        Assert.assertThrows(ProdutoLucroInconsistenteException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoDeveLancarProdutoPrecoDeCompraNuloException() {
        Produto produto = produtoCriado();
        produto.setPrecoCompra(null);
        Assert.assertThrows(ProdutoPrecoDeCompraNuloException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoDeveLancarProdutoPrecoDeVendaNuloException() {
        Produto produto = produtoCriado();
        produto.setPrecoVenda(null);
        Assert.assertThrows(ProdutoPrecoDeVendaNuloException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoDeveLancarProdutoQuantidadeNulaException() {
        Produto produto = produtoCriado();
        produto.setQuantidade(null);
        Assert.assertThrows(ProdutoQuantidadeNulaException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoNaoDeveLancarProblemaComQuantidadeNegativa() {
        Produto produto = produtoCriado();
        produto.setQuantidade(-200L);
    }

    Produto produtoCriado() {
        Produto produto = new Produto();
        produto.setCodigoBarras(123L);
        produto.setDataCriacao("Hoje");
        produto.setDescricao("Teste Descricao");
        produto.setLucroLiquido(BigDecimal.valueOf(1.00));
        produto.setPrecoCompra(BigDecimal.valueOf(2.00));
        produto.setPrecoVenda(BigDecimal.valueOf(3.00));
        produto.setQuantidade(10L);
        produto.setTipoProduto(new TipoProduto());
        return produto;
    }
}