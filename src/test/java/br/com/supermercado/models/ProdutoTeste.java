package br.com.supermercado.models;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import br.com.supermercado.exceptions.*;
import br.com.supermercado.exceptions.ProdutoDescricaInvalidaException;

@SpringBootTest
class ProdutoTeste {

    @Test
    void verificarProdutoDeveRetornarOk() throws Exception {

        Produto produto = produtoCriado();

        produto.verificarProduto();

        Assert.assertEquals(123L, produto.getCodigo(), 0);
        Assert.assertEquals("Hoje", produto.getDataDeCriacao());
        Assert.assertEquals("Teste Descricao", produto.getDescricao());
        Assert.assertEquals(BigDecimal.valueOf(1.00), produto.getLucroLiquido());
        Assert.assertEquals(BigDecimal.valueOf(3.00), produto.getPrecoDeVenda());
        Assert.assertEquals(BigDecimal.valueOf(2.00), produto.getPrecoDeCompra());
        Assert.assertEquals(10L, produto.getQuantidade(), 0);
        Assert.assertNotNull(produto.getTipoDoProduto());
    }

    @Test
    void verificarProdutoNaoNuloDoProdutoCriadoNaoDeveLancarExcecao() {

        Produto produto = produtoCriado();
        produto.verificarAtributosProdutoNaoNulo();

    }

    @Test
    void verificarProdutoDeveLancarProdutoDescricaoInvalidaException(){
        Produto produto = produtoCriado();
        produto.setDescricao("");
        Assert.assertThrows(ProdutoDescricaInvalidaException.class, () -> produto.verificarProduto());
    }

    @Test
    void verificarProdutoDeveLancarProdutoDescricaoInvalidaExceptionComDescricaoCom5Carcateres(){
        Produto produto = produtoCriado();
        produto.setDescricao("Teste");
        Assert.assertThrows(ProdutoDescricaInvalidaException.class, () -> produto.verificarProduto());
    }
    
    @Test
    void verificarProdutoDeveLancarProdutoDescricaoNulaException(){
        Produto produto = produtoCriado();
        produto.setDescricao(null);
        Assert.assertThrows(ProdutoDescricaoNulaException.class, () -> produto.verificarProduto());
    }
    








    Produto produtoCriado() {
        Produto produto = new Produto();
        produto.setCodigo(123L);
        produto.setDataDeCriacao("Hoje");
        produto.setDescricao("Teste Descricao");
        produto.setLucroLiquido(BigDecimal.valueOf(1.00));
        produto.setPrecoDeCompra(BigDecimal.valueOf(2.00));
        produto.setPrecoDeVenda(BigDecimal.valueOf(3.00));
        produto.setQuantidade(10L);
        produto.setTipoDoProduto(new TipoDoProduto());
        return produto;
    }
}