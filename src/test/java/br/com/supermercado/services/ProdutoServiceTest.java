package br.com.supermercado.services;

import br.com.supermercado.dtos.ProdutoDto;
import br.com.supermercado.exceptions.*;
import br.com.supermercado.models.Produto;
import br.com.supermercado.models.TipoDoProduto;
import br.com.supermercado.repositories.ProdutoRepository;
import br.com.supermercado.repositories.TipoDoProdutoRepository;
import br.com.supermercado.util.DataUtilitario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
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
    private TipoDoProdutoRepository tipoDoProdutoRepository;

    @Mock
    private ProdutoRepository ProdutoRepository;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Before
    public void setup() {
    }

    @Test
    public void verificandoCriacaoDeProdutoComDto() throws Exception {

        TipoDoProduto tipo = new TipoDoProduto();
        tipo.setNomeTipoDoProduto("Teste");
        tipo.setListaDeProdutos(null);

        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Produto Teste");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(250L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Produto produto = produtoService.criandoProdutoComDto(produtoDto);

        Mockito.when(tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        Assertions.assertEquals(produto.getDescricao(), "Produto Teste");
        Assertions.assertEquals(produto.getCodigo(), 12345L, 0);
        Assertions.assertEquals(produto.getQuantidade(), 250L, 0);
        Assertions.assertEquals(produto.getPrecoDeCompra(), BigDecimal.valueOf(1.79));
        Assertions.assertEquals(produto.getPrecoDeVenda(), BigDecimal.valueOf(2.49));
        Assertions.assertEquals(produto.getLucroLiquido().doubleValue(), 0.70);
        Assertions.assertEquals(produto.getDataDeCriacao(), DataUtilitario.getDataAtualComoString());
        Assertions.assertNull(produto.getTipoDoProduto());
    }

    @Test
    public void criarProdutoRecebeDtoERetornaProdutoComTodosAtributosCorretos() throws Exception {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Produto Teste");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(250L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        TipoDoProduto tipo = new TipoDoProduto();
        tipo.setNomeTipoDoProduto("Tipo Teste");
        tipo.setListaDeProdutos(null);
        Mockito.when(tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        Produto produto = produtoService.criarProduto(produtoDto);

        Assert.assertEquals(produto.getCodigo(), 12345L, 0.00);
        error.checkThat(produto.getDescricao(), is("Produto Teste"));
        error.checkThat(produto.getCodigo(), is(12345L));
        error.checkThat(produto.getQuantidade(), is(250L));
        error.checkThat(produto.getPrecoDeCompra(), is(BigDecimal.valueOf(1.79)));
        error.checkThat(produto.getPrecoDeVenda(), is(BigDecimal.valueOf(2.49)));
        error.checkThat(produto.getLucroLiquido(), is(BigDecimal.valueOf(0.70)));
        error.checkThat(produto.getDataDeCriacao(), is(DataUtilitario.getDataAtualComoString()));
        error.checkThat(produto.getTipoDoProduto(), is(null));

    }

    @Test
    public void verificandoDescricaoDeveLancarProdutoDescricaoInvalidaException() {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("F");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(250L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);


        Assert.assertThrows(ProdutoDescricaInvalidaException.class, () -> produtoService.criarProduto(produtoDto));
    }

    @Test
    public void verificandoDescricaoDeveLancarProdutoDescricaoNulaException() {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao(null);
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(250L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Assert.assertThrows(ProdutoDescricaoNulaException.class, () -> produtoService.criarProduto(produtoDto));
    }

    @Test
    public void verificandoCodigoDeveLancarProdutoCodigoNuloException() {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Teste");
        produtoDto.setCodigo(null);
        produtoDto.setQuantidade(250L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Assert.assertThrows(ProdutoCodigoNuloException.class, () -> produtoService.criarProduto(produtoDto));
    }

    @Test
    public void verificandoQtdDeveLancarProdutoCodigoNuloException() {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Teste0");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(null);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Assert.assertThrows(ProdutoQuantidadeNulaException.class, () -> produtoService.criarProduto(produtoDto));
    }

    @Test
    public void verificandoQtdNegativaNaoDeveGerarErro() throws Exception {

        TipoDoProduto tipo = new TipoDoProduto();
        tipo.setNomeTipoDoProduto("Teste");
        tipo.setListaDeProdutos(null);
        Mockito.when(tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Teste0");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(-1L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);


        Produto produto = produtoService.criarProduto(produtoDto);

        Assertions.assertEquals(-1, produto.getQuantidade());
    }

    @Test
    public void verificandoLucroDeveRetornarProdutoLucroInconsistenteException() throws Exception {

        TipoDoProduto tipo = new TipoDoProduto();
        tipo.setNomeTipoDoProduto("Teste");
        tipo.setListaDeProdutos(null);
        Mockito.when(tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Teste0");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(2L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Produto produto = produtoService.criarProduto(produtoDto);
        produto.setLucroLiquido(new BigDecimal(0.9));

        Assert.assertThrows(ProdutoLucroInconsistenteException.class, () -> produtoService.verificarProduto(produto));
    }
}