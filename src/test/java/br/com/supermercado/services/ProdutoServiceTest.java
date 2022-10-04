package br.com.supermercado.services;

import br.com.supermercado.dtos.ProdutoDto;
import br.com.supermercado.models.Produto;
import br.com.supermercado.models.TipoDoProduto;
import br.com.supermercado.repositories.ProdutoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;

@SpringBootTest
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private TipoDoProdutoService tipoDoProdutoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Before
    public void setup() {
    }


//    private Produto criarProdutoParaTeste() {
//
//
//    }

    private ProdutoDto criarProdutoDtoParaTeste() {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Produto Teste");
        produtoDto.setCodigo(1L);
        produtoDto.setQuantidade(1L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2));
        produtoDto.setIdTipoDoProduto(2L);
        return produtoDto;
    }

    @Test
    public void criarProdutoRecebeDtoERetornaProdutoOk() throws Exception {

        TipoDoProduto tipo = new TipoDoProduto();
        tipo.setNomeTipoDoProduto("Teste");
        tipo.setListaDeProdutos(null);

        Mockito.when(tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        Produto produto = produtoService.criarProduto(criarProdutoDtoParaTeste());


        Assert.assertEquals(produto.getCodigo(), 1, 0.00);
        error.checkThat(produto.getDescricao(), is("Produto Teste"));
        error.checkThat(produto.getCodigo(), is(1l));
        error.checkThat(produto.getQuantidade(), is(1L));
        error.checkThat(produto.getPrecoDeCompra(), is(BigDecimal.valueOf(1)));
        error.checkThat(produto.getPrecoDeVenda(), is(BigDecimal.valueOf(2)));

    }

    @Test
    public void verificandoDescricao() throws Exception {
        Produto produto = produtoService.criarProduto(criarProdutoDtoParaTeste());


        Assert.assertEquals(produto.getDescricao(), "Produto Teste");
    }

    @Test
    public void verificandoQuantidade() throws Exception {

        Produto produto = produtoService.criarProduto(criarProdutoDtoParaTeste());

        Assert.assertEquals(produto.getCodigo(), 1, 0.00);
    }

    @Test
    public void verificandoPrecoDeCompra() throws Exception {


        Produto produto = produtoService.criarProduto(criarProdutoDtoParaTeste());

        Assert.assertSame(produto.getPrecoDeCompra(), BigDecimal.valueOf(1));
    }

    @Test
    public void verificandoPrecoDeVenda() throws Exception {
w        Produto produto = produtoService.criarProduto(criarProdutoDtoParaTeste());
        Assert.assertSame(produto.getPrecoDeVenda(), BigDecimal.valueOf(2));
    }

    @Test
    public void verificandoCriacaoDeProdutoComDto() {
        TipoDoProduto tipo = new TipoDoProduto();
        tipo.setNomeTipoDoProduto("Teste");
        tipo.setListaDeProdutos(null);

        Mockito.when(tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);
        Produto produto = produtoService.criandoProdutoComDto(criarProdutoDtoParaTeste());

        Assert.assertEquals(produto.getCodigo(), 1, 0.00);
        error.checkThat(produto.getDescricao(), is("Produto Teste"));
        error.checkThat(produto.getCodigo(), is(1l));
        error.checkThat(produto.getQuantidade(), is(1L));
        error.checkThat(produto.getPrecoDeCompra(), is(BigDecimal.valueOf(1)));
        error.checkThat(produto.getPrecoDeVenda(), is(BigDecimal.valueOf(2)));
        Assert.assertEquals(produto.getLucroLiquido(), BigDecimal.valueOf(1));
    }

}