package br.com.supermercado.services;

import br.com.supermercado.dtos.ProdutoDto;
import br.com.supermercado.exceptions.ProdutoDescricaInvalidaException;
import br.com.supermercado.exceptions.ProdutoDescricaoNulaException;
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
import java.util.ArrayList;

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
    public void verificandoDescricaoDeveLancarDescricaoInvalidaException() throws Exception {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("F");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(250L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Assert.assertThrows(ProdutoDescricaInvalidaException.class,() -> produtoService.criarProduto(produtoDto));
    }

    @Test
    public void verificandoDescricaoDeveLancarDescricaoNulaException() throws Exception {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao(null);
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(250L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Assert.assertThrows(ProdutoDescricaoNulaException.class,() -> produtoService.criarProduto(produtoDto));
    }


    @Test
    public void verificandoCriacaoDeProdutoComDto() {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Produto Teste");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(250L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        TipoDoProduto tipo = new TipoDoProduto();
        tipo.setNomeTipoDoProduto("Teste");
        tipo.setListaDeProdutos(null);

        Mockito.when(tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);
        Produto produto = produtoService.criandoProdutoComDto(produtoDto);

        Assertions.assertEquals(produto.getCodigo(), 12345L, 0.00);
        error.checkThat(produto.getDescricao(), is("Produto Teste"));
        error.checkThat(produto.getCodigo(), is(12345L));
        error.checkThat(produto.getQuantidade(), is(250L));
        error.checkThat(produto.getPrecoDeCompra(), is(BigDecimal.valueOf(1.79)));
        error.checkThat(produto.getPrecoDeVenda(), is(BigDecimal.valueOf(2.49)));
        error.checkThat(produto.getLucroLiquido(), is(BigDecimal.valueOf(0.70)));
        error.checkThat(produto.getDataDeCriacao(), is(DataUtilitario.getDataAtualComoString()));
        error.checkThat(produto.getTipoDoProduto(), is(null));
    }

}