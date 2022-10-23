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
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ErrorCollector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

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
    public void criarProdutoComDtoDeveRetornarProdutoComTodosAtributosOK() throws Exception {

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
    public void criarProdutoDeveLancarProdutoDescricaoInvalidaException() {
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
    public void criarProdutoDeveLancarProdutoDescricaoNulaException() {
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
    public void criarProdutoDeveLancarProdutoCodigoNuloException() {
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
    public void criarProdutoDeveLancarProdutoQuantidadeNulaException() {
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
    public void criarProdutoQtdNegativaNaoDeveGerarErro() throws Exception {

        TipoDoProduto tipo = new TipoDoProduto();
        tipo.setNomeTipoDoProduto("Teste");
        tipo.setListaDeProdutos(null);
        Mockito.when(tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Teste0");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(-60L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);


        Produto produto = produtoService.criarProduto(produtoDto);

        Assertions.assertEquals(-60, produto.getQuantidade());
    }

    @Test
    public void criarProdutoDeveRetornarProdutoLucroInconsistenteException() throws Exception {

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

    @Test
    public void criarProdutoDeveRetornarComLucroDeveSerNegativo() throws Exception{

        TipoDoProduto tipo = new TipoDoProduto();
        tipo.setNomeTipoDoProduto("Teste");
        tipo.setListaDeProdutos(null);
        Mockito.when(tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Teste0");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(2L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(2.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Produto produto = produtoService.criarProduto(produtoDto);
        System.out.println(produto.getLucroLiquido());

        Assert.assertEquals(produto.getLucroLiquido().doubleValue(), -0,30);
    }

    @Test
    void deveRetornarListaDeProdutosVazia(){
        ArrayList<Produto> listaDeProdutos = new ArrayList();
        Mockito.when(produtoService.pegarTodosProdutos()).thenReturn(listaDeProdutos);
        Assert.assertEquals(produtoService.pegarTodosProdutos().size(), 0);
        Assert.assertEquals(produtoService.pegarTodosProdutos().isEmpty(), true);
    }

    @Test
    void deveRetornarListaDeProdutosComUmProduto(){
        ArrayList<Produto> listaDeProdutos = new ArrayList();
        listaDeProdutos.add(new Produto());
        
        Mockito.when(produtoService.pegarTodosProdutos()).thenReturn(listaDeProdutos);
        Assert.assertEquals( 1, produtoService.pegarTodosProdutos().size());
        Assert.assertEquals( false, produtoService.pegarTodosProdutos().isEmpty());

    }

    @Test
    void deveRetornarListaDeProdutosComDoisProdutos(){
        ArrayList<Produto> listaDeProdutos = new ArrayList();
        listaDeProdutos.add(new Produto());
        listaDeProdutos.add(new Produto());

        Mockito.when(produtoService.pegarTodosProdutos()).thenReturn(listaDeProdutos);
        Assert.assertEquals( 2, produtoService.pegarTodosProdutos().size());
        Assert.assertEquals( false, produtoService.pegarTodosProdutos().isEmpty());

    }

    @Test
    void deveRetornarListaDeProdutosComDoisProdutosEVerificandoDescricao(){
        ArrayList<Produto> listaDeProdutos = new ArrayList();
        listaDeProdutos.add(new Produto());
        listaDeProdutos.add(new Produto());
        Produto produto = new Produto();
        produto.setDescricao("Teste Descricao");
        listaDeProdutos.add(produto);

        Mockito.when(produtoService.pegarTodosProdutos()).thenReturn(listaDeProdutos);
        Assert.assertEquals(2, produtoService.pegarTodosProdutos().size());
        Assert.assertEquals(false, produtoService.pegarTodosProdutos().isEmpty());
        Assert.assertEquals("Teste Descricao", produtoService.pegarTodosProdutos().get(2).getDescricao());

    }

}