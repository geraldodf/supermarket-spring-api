package br.com.supermercado.services;

import br.com.supermercado.dtos.ProdutoDto;
import br.com.supermercado.exceptions.*;
import br.com.supermercado.models.Produto;
import br.com.supermercado.models.TipoProduto;
import br.com.supermercado.repositories.ProdutoRepository;
import br.com.supermercado.repositories.TipoProdutoRepository;
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
    void criarProdutoDeveLancarProdutoDescricaoInvalidaExceptionCom5Caracteres() {
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Teste");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(250L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Assert.assertThrows(ProdutoDescricaInvalidaException.class, () -> produtoService.criarProduto(produtoDto));
    }

    @Test
    void criarProdutoDeveLancarProdutoDescricaoNulaException() {
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
    void criarProdutoDeveLancarProdutoCodigoNuloException() {
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
    void criarProdutoQtdNegativaNaoDeveLancarException() throws Exception {

        TipoProduto tipo = new TipoProduto();
        tipo.setNomeTipoProduto("Teste");
        tipo.setListaProdutos(null);
        Mockito.when(tipoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

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
    void criarProdutoDeveRetornarProdutoLucroInconsistenteException() throws Exception {

        TipoProduto tipo = new TipoProduto();
        tipo.setNomeTipoProduto("Teste");
        tipo.setListaProdutos(null);
        Mockito.when(tipoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Teste0");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(2L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(1.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Produto produto = produtoService.criarProduto(produtoDto);
        produto.setLucroLiquido(new BigDecimal(0.9));

        Assert.assertThrows(ProdutoLucroInconsistenteException.class, () -> produto.verificarProduto());
    }

    @Test
    void criarProdutoDeveRetornarComLucroDeveSerNegativo() throws Exception {

        TipoProduto tipo = new TipoProduto();
        tipo.setNomeTipoProduto("Teste");
        tipo.setListaProdutos(null);
        Mockito.when(tipoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setDescricao("Teste0");
        produtoDto.setCodigo(12345L);
        produtoDto.setQuantidade(2L);
        produtoDto.setPrecoDeCompra(BigDecimal.valueOf(2.79));
        produtoDto.setPrecoDeVenda(BigDecimal.valueOf(2.49));
        produtoDto.setIdTipoDoProduto(2L);

        Produto produto = produtoService.criarProduto(produtoDto);
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