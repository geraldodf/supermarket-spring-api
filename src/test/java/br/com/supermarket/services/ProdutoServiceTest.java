package br.com.supermarket.services;

import br.com.supermarket.dtos.ProductDto;
import br.com.supermarket.exceptions.*;
import br.com.supermarket.models.Produto;
import br.com.supermarket.models.TipoProduto;
import br.com.supermarket.repositories.ProdutoRepository;
import br.com.supermarket.repositories.TipoProdutoRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private TipoProdutoService tipoProdutoService;

    @Mock
    private TipoProdutoRepository tipoProdutoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Before
    public void setup() {
    }

    @Test
    void criarProdutoDeveLancarProdutoDescricaoInvalidaException() {
        ProductDto productDto = new ProductDto();
        productDto.setDescricao("F");
        productDto.setCodigo(12345L);
        productDto.setQuantidade(250L);
        productDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        productDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        productDto.setIdTipoDoProduto(2L);

        Assert.assertThrows(ProductDescriptionInvalidException.class, () -> produtoService.criarProduto(productDto));
    }

    @Test
    void criarProdutoDeveLancarProdutoDescricaoInvalidaExceptionCom5Caracteres() {
        ProductDto productDto = new ProductDto();
        productDto.setDescricao("Teste");
        productDto.setCodigo(12345L);
        productDto.setQuantidade(250L);
        productDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        productDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        productDto.setIdTipoDoProduto(2L);

        Assert.assertThrows(ProductDescriptionInvalidException.class, () -> produtoService.criarProduto(productDto));
    }

    @Test
    void criarProdutoDeveLancarProdutoDescricaoNulaException() {
        ProductDto productDto = new ProductDto();
        productDto.setDescricao(null);
        productDto.setCodigo(12345L);
        productDto.setQuantidade(250L);
        productDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        productDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        productDto.setIdTipoDoProduto(2L);

        Assert.assertThrows(ProductDescriptionNullException.class, () -> produtoService.criarProduto(productDto));
    }

    @Test
    void criarProdutoDeveLancarProdutoCodigoNuloException() {
        ProductDto productDto = new ProductDto();
        productDto.setDescricao("Teste");
        productDto.setCodigo(null);
        productDto.setQuantidade(250L);
        productDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        productDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        productDto.setIdTipoDoProduto(2L);

        Assert.assertThrows(ProductNullBarcodeException.class, () -> produtoService.criarProduto(productDto));
    }

    @Test
    void criarProdutoQtdNegativaNaoDeveLancarException() throws Exception {

        TipoProduto tipo = new TipoProduto();
        tipo.setNomeTipoProduto("Teste");
        tipo.setListaProdutos(null);
        Mockito.when(tipoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProductDto productDto = new ProductDto();
        productDto.setDescricao("Teste0");
        productDto.setCodigo(12345L);
        productDto.setQuantidade(-60L);
        productDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        productDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        productDto.setIdTipoDoProduto(2L);

        Produto produto = produtoService.criarProduto(productDto);

        Assertions.assertEquals(-60, produto.getQuantidade());
    }

    @Test
    void criarProdutoDeveRetornarProdutoLucroInconsistenteException() throws Exception {

        TipoProduto tipo = new TipoProduto();
        tipo.setNomeTipoProduto("Teste");
        tipo.setListaProdutos(null);
        Mockito.when(tipoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProductDto productDto = new ProductDto();
        productDto.setDescricao("Teste0");
        productDto.setCodigo(12345L);
        productDto.setQuantidade(2L);
        productDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        productDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        productDto.setIdTipoDoProduto(2L);

        Produto produto = produtoService.criarProduto(productDto);
        produto.setLucroLiquido(new BigDecimal(0.9));

        Assert.assertThrows(ProductProfitInconsistentException.class, () -> produto.verificarProduto());
    }

    @Test
    void criarProdutoDeveRetornarComLucroDeveSerNegativo() throws Exception {

        TipoProduto tipo = new TipoProduto();
        tipo.setNomeTipoProduto("Teste");
        tipo.setListaProdutos(null);
        Mockito.when(tipoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProductDto productDto = new ProductDto();
        productDto.setDescricao("Teste0");
        productDto.setCodigo(12345L);
        productDto.setQuantidade(2L);
        productDto.setPrecoDeCompra(BigDecimal.valueOf(2.79));
        productDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        productDto.setIdTipoDoProduto(2L);

        Produto produto = produtoService.criarProduto(productDto);
        System.out.println(produto.getLucroLiquido());

        Assert.assertEquals(produto.getLucroLiquido().doubleValue(), -0, 30);
    }

    @Test
    void pegarTodosProdutosDeveRetornarListaDeProdutosVazia() {
        ArrayList<Produto> listaDeProdutos = new ArrayList();
        Mockito.when(produtoService.pegarTodosProdutos()).thenReturn(listaDeProdutos);
        Assert.assertEquals(produtoService.pegarTodosProdutos().size(), 0);
        Assert.assertEquals(produtoService.pegarTodosProdutos().isEmpty(), true);
    }

    @Test
    void pegarTodosProdutosDeveRetornarListaDeProdutosComUmProduto() {
        ArrayList<Produto> listaDeProdutos = new ArrayList();
        listaDeProdutos.add(new Produto());

        Mockito.when(produtoRepository.findAll()).thenReturn(listaDeProdutos);
        Assert.assertEquals(1, produtoService.pegarTodosProdutos().size());
        Assert.assertEquals(false, produtoService.pegarTodosProdutos().isEmpty());

    }

    @Test
    void pegarTodosProdutosDeveRetornarListaDeProdutosComDoisProdutos() {
        ArrayList<Produto> listaDeProdutos = new ArrayList();
        listaDeProdutos.add(new Produto());
        listaDeProdutos.add(new Produto());

        Mockito.when(produtoRepository.findAll()).thenReturn(listaDeProdutos);
        Assert.assertEquals(2, produtoService.pegarTodosProdutos().size());
        Assert.assertEquals(false, produtoService.pegarTodosProdutos().isEmpty());

    }

    @Test
    void pegarTodosProdutosDeveRetornarListaDeProdutosComDoisProdutosEVerificandoDescricao() {
        ArrayList<Produto> listaDeProdutos = new ArrayList();
        listaDeProdutos.add(new Produto());
        listaDeProdutos.add(new Produto());
        Produto produto = new Produto();
        produto.setDescricao("Teste Descricao");
        listaDeProdutos.add(produto);

        Mockito.when(produtoRepository.findAll()).thenReturn(listaDeProdutos);
        Assert.assertEquals(3, produtoService.pegarTodosProdutos().size());
        Assert.assertEquals(false, produtoService.pegarTodosProdutos().isEmpty());
        Assert.assertEquals("Teste Descricao", produtoService.pegarTodosProdutos().get(2).getDescricao());
    }

    @Test
    void pegarUmProdutoPeloIdDeveRetornarProdutoVazio() throws Exception {
        Long id = 3L;

        Mockito.when(produtoRepository.findById(id)).thenReturn(Optional.of(new Produto()));
        Assert.assertNotNull(produtoService.pegarUmProduto(id));
    }

    @Test
    void pegarProdutosPorDescricaoDeveRetornarListaDeProdutoVazio() throws Exception {
        String descricao = "Teste";
        ArrayList<Produto> listaDeProdutos = new ArrayList<>();

        Mockito.when(produtoRepository.pesquisaPorDescricao(descricao)).thenReturn(listaDeProdutos);
        Assert.assertEquals(true, produtoRepository.pesquisaPorDescricao(descricao).isEmpty());
        Assert.assertEquals(0, produtoRepository.pesquisaPorDescricao(descricao).size());
    }

    @Test
    void pegarProdutosPorDescicaoDeveRetornarListaComUmProduto() throws Exception {
        String descricao = "Teste";
        ArrayList<Produto> listaDeProdutos = new ArrayList<>();
        listaDeProdutos.add(new Produto());

        Mockito.when(produtoRepository.pesquisaPorDescricao(descricao)).thenReturn(listaDeProdutos);
        Assert.assertEquals(false, produtoRepository.pesquisaPorDescricao(descricao).isEmpty());
        Assert.assertEquals(1, produtoRepository.pesquisaPorDescricao(descricao).size());
    }

    @Test
    void pegarProdutosPorDescricaoDeveRetornarListaComDoisProdutosEVerificarAsDescricoes() throws Exception {
        String descricao = "Teste";
        ArrayList<Produto> listaDeProdutos = new ArrayList<>();
        Produto p1 = new Produto();
        Produto p2 = new Produto();
        p1.setDescricao("Teste 1");
        p2.setDescricao("Teste 2");
        listaDeProdutos.add(p1);
        listaDeProdutos.add(p2);

        Mockito.when(produtoRepository.pesquisaPorDescricao(descricao)).thenReturn(listaDeProdutos);
        Assert.assertEquals(false, produtoRepository.pesquisaPorDescricao(descricao).isEmpty());
        Assert.assertEquals(2, produtoRepository.pesquisaPorDescricao(descricao).size());
        Assert.assertEquals("Teste 1", produtoRepository.pesquisaPorDescricao(descricao).get(0).getDescricao());
        Assert.assertEquals("Teste 2", produtoRepository.pesquisaPorDescricao(descricao).get(1).getDescricao());
    }

    @Test
    void pegarProdutosPorCodigoDeveRetornarListaDeProdutoVazio() throws Exception {
        Long codigo = 1010L;
        ArrayList<Produto> listaDeProdutos = new ArrayList<>();

        Mockito.when(produtoRepository.pesquisaPorCodigo(codigo)).thenReturn(listaDeProdutos);
        Assert.assertEquals(true, produtoRepository.pesquisaPorCodigo(codigo).isEmpty());
        Assert.assertEquals(0, produtoRepository.pesquisaPorCodigo(codigo).size());
    }

    @Test
    void pegarProdutosPorCodigoDeveRetornarListaComUmProduto() throws Exception {
        Long codigo = 1010L;
        ArrayList<Produto> listaDeProdutos = new ArrayList<>();
        listaDeProdutos.add(new Produto());

        Mockito.when(produtoRepository.pesquisaPorCodigo(codigo)).thenReturn(listaDeProdutos);
        Assert.assertEquals(false, produtoRepository.pesquisaPorCodigo(codigo).isEmpty());
        Assert.assertEquals(1, produtoRepository.pesquisaPorCodigo(codigo).size());
    }

    @Test
    void pegarProdutosPorCodigoDeveRetornarListaComDoisProdutosEVerificarAsQtd() throws Exception {
        Long codigo = 1010L;
        ArrayList<Produto> listaDeProdutos = new ArrayList<>();
        Produto p1 = new Produto();
        Produto p2 = new Produto();
        p1.setQuantidade(1L);
        p2.setQuantidade(2L);
        listaDeProdutos.add(p1);
        listaDeProdutos.add(p2);

        Mockito.when(produtoRepository.pesquisaPorCodigo(codigo)).thenReturn(listaDeProdutos);
        Assert.assertEquals(false, produtoRepository.pesquisaPorCodigo(codigo).isEmpty());
        Assert.assertEquals(2, produtoRepository.pesquisaPorCodigo(codigo).size());
        Assert.assertEquals(1L, produtoRepository.pesquisaPorCodigo(codigo).get(0).getQuantidade(), 0);
        Assert.assertEquals(2L, produtoRepository.pesquisaPorCodigo(codigo).get(1).getQuantidade(), 0);
    }

    @Test
    void pegarTodoProdutosPaginadosDeveRetornarListDeProdutoOK() throws Exception {
        Pageable pageable = PageRequest.of(1, 5, Direction.ASC);

        produtoService.pesquisaPaginada(pageable);  

    }

}