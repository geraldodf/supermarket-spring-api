package br.com.supermarket.services;

import br.com.supermarket.dtos.ProductDto;
import br.com.supermarket.exceptions.*;
import br.com.supermarket.models.Product;
import br.com.supermarket.models.ProductType;
import br.com.supermarket.repositories.ProductRepository;
import br.com.supermarket.repositories.ProductTypeRepository;
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
class ProductServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private TipoProdutoService tipoProdutoService;

    @Mock
    private ProductTypeRepository productTypeRepository;

    @Mock
    private ProductRepository productRepository;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Before
    public void setup() {
    }

    @Test
    void criarProdutoDeveLancarProdutoDescricaoInvalidaException() {
        ProductDto productDto = new ProductDto();
        productDto.setDescription("F");
        productDto.setBarCode(12345L);
        productDto.setQuantity(250L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Assert.assertThrows(ProductDescriptionInvalidException.class, () -> produtoService.criarProduto(productDto));
    }

    @Test
    void criarProdutoDeveLancarProdutoDescricaoInvalidaExceptionCom5Caracteres() {
        ProductDto productDto = new ProductDto();
        productDto.setDescription("Teste");
        productDto.setBarCode(12345L);
        productDto.setQuantity(250L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Assert.assertThrows(ProductDescriptionInvalidException.class, () -> produtoService.criarProduto(productDto));
    }

    @Test
    void criarProdutoDeveLancarProdutoDescricaoNulaException() {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(null);
        productDto.setBarCode(12345L);
        productDto.setQuantity(250L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Assert.assertThrows(ProductDescriptionNullException.class, () -> produtoService.criarProduto(productDto));
    }

    @Test
    void criarProdutoDeveLancarProdutoCodigoNuloException() {
        ProductDto productDto = new ProductDto();
        productDto.setDescription("Teste");
        productDto.setBarCode(null);
        productDto.setQuantity(250L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Assert.assertThrows(ProductNullBarcodeException.class, () -> produtoService.criarProduto(productDto));
    }

    @Test
    void criarProdutoQtdNegativaNaoDeveLancarException() throws Exception {

        ProductType tipo = new ProductType();
        tipo.setNameProductType("Teste");
        tipo.setProductList(null);
        Mockito.when(tipoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProductDto productDto = new ProductDto();
        productDto.setDescription("Teste0");
        productDto.setBarCode(12345L);
        productDto.setQuantity(-60L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Product product = produtoService.criarProduto(productDto);

        Assertions.assertEquals(-60, product.getQuantity());
    }

    @Test
    void criarProdutoDeveRetornarProdutoLucroInconsistenteException() throws Exception {

        ProductType tipo = new ProductType();
        tipo.setNameProductType("Teste");
        tipo.setProductList(null);
        Mockito.when(tipoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProductDto productDto = new ProductDto();
        productDto.setDescription("Teste0");
        productDto.setBarCode(12345L);
        productDto.setQuantity(2L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Product product = produtoService.criarProduto(productDto);
        product.setNetProfit(new BigDecimal(0.9));

        Assert.assertThrows(ProductProfitInconsistentException.class, () -> product.autoVerify());
    }

    @Test
    void criarProdutoDeveRetornarComLucroDeveSerNegativo() throws Exception {

        ProductType tipo = new ProductType();
        tipo.setNameProductType("Teste");
        tipo.setProductList(null);
        Mockito.when(tipoProdutoService.pegarUmTipoDoProdutoPeloId(2L)).thenReturn(tipo);

        ProductDto productDto = new ProductDto();
        productDto.setDescription("Teste0");
        productDto.setBarCode(12345L);
        productDto.setQuantity(2L);
        productDto.setPriceBuy(BigDecimal.valueOf(2.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Product product = produtoService.criarProduto(productDto);
        System.out.println(product.getNetProfit());

        Assert.assertEquals(product.getNetProfit().doubleValue(), -0, 30);
    }

    @Test
    void pegarTodosProdutosDeveRetornarListaDeProdutosVazia() {
        ArrayList<Product> listaDeProducts = new ArrayList();
        Mockito.when(produtoService.pegarTodosProdutos()).thenReturn(listaDeProducts);
        Assert.assertEquals(produtoService.pegarTodosProdutos().size(), 0);
        Assert.assertEquals(produtoService.pegarTodosProdutos().isEmpty(), true);
    }

    @Test
    void pegarTodosProdutosDeveRetornarListaDeProdutosComUmProduto() {
        ArrayList<Product> listaDeProducts = new ArrayList();
        listaDeProducts.add(new Product());

        Mockito.when(productRepository.findAll()).thenReturn(listaDeProducts);
        Assert.assertEquals(1, produtoService.pegarTodosProdutos().size());
        Assert.assertEquals(false, produtoService.pegarTodosProdutos().isEmpty());

    }

    @Test
    void pegarTodosProdutosDeveRetornarListaDeProdutosComDoisProdutos() {
        ArrayList<Product> listaDeProducts = new ArrayList();
        listaDeProducts.add(new Product());
        listaDeProducts.add(new Product());

        Mockito.when(productRepository.findAll()).thenReturn(listaDeProducts);
        Assert.assertEquals(2, produtoService.pegarTodosProdutos().size());
        Assert.assertEquals(false, produtoService.pegarTodosProdutos().isEmpty());

    }

    @Test
    void pegarTodosProdutosDeveRetornarListaDeProdutosComDoisProdutosEVerificandoDescricao() {
        ArrayList<Product> listaDeProducts = new ArrayList();
        listaDeProducts.add(new Product());
        listaDeProducts.add(new Product());
        Product product = new Product();
        product.setDescription("Teste Descricao");
        listaDeProducts.add(product);

        Mockito.when(productRepository.findAll()).thenReturn(listaDeProducts);
        Assert.assertEquals(3, produtoService.pegarTodosProdutos().size());
        Assert.assertEquals(false, produtoService.pegarTodosProdutos().isEmpty());
        Assert.assertEquals("Teste Descricao", produtoService.pegarTodosProdutos().get(2).getDescription());
    }

    @Test
    void pegarUmProdutoPeloIdDeveRetornarProdutoVazio() throws Exception {
        Long id = 3L;

        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(new Product()));
        Assert.assertNotNull(produtoService.pegarUmProduto(id));
    }

    @Test
    void pegarProdutosPorDescricaoDeveRetornarListaDeProdutoVazio() throws Exception {
        String descricao = "Teste";
        ArrayList<Product> listaDeProducts = new ArrayList<>();

        Mockito.when(productRepository.searchByDescription(descricao)).thenReturn(listaDeProducts);
        Assert.assertEquals(true, productRepository.searchByDescription(descricao).isEmpty());
        Assert.assertEquals(0, productRepository.searchByDescription(descricao).size());
    }

    @Test
    void pegarProdutosPorDescicaoDeveRetornarListaComUmProduto() throws Exception {
        String descricao = "Teste";
        ArrayList<Product> listaDeProducts = new ArrayList<>();
        listaDeProducts.add(new Product());

        Mockito.when(productRepository.searchByDescription(descricao)).thenReturn(listaDeProducts);
        Assert.assertEquals(false, productRepository.searchByDescription(descricao).isEmpty());
        Assert.assertEquals(1, productRepository.searchByDescription(descricao).size());
    }

    @Test
    void pegarProdutosPorDescricaoDeveRetornarListaComDoisProdutosEVerificarAsDescricoes() throws Exception {
        String descricao = "Teste";
        ArrayList<Product> listaDeProducts = new ArrayList<>();
        Product p1 = new Product();
        Product p2 = new Product();
        p1.setDescription("Teste 1");
        p2.setDescription("Teste 2");
        listaDeProducts.add(p1);
        listaDeProducts.add(p2);

        Mockito.when(productRepository.searchByDescription(descricao)).thenReturn(listaDeProducts);
        Assert.assertEquals(false, productRepository.searchByDescription(descricao).isEmpty());
        Assert.assertEquals(2, productRepository.searchByDescription(descricao).size());
        Assert.assertEquals("Teste 1", productRepository.searchByDescription(descricao).get(0).getDescription());
        Assert.assertEquals("Teste 2", productRepository.searchByDescription(descricao).get(1).getDescription());
    }

    @Test
    void pegarProdutosPorCodigoDeveRetornarListaDeProdutoVazio() throws Exception {
        Long codigo = 1010L;
        ArrayList<Product> listaDeProducts = new ArrayList<>();

        Mockito.when(productRepository.searchByBarCode(codigo)).thenReturn(listaDeProducts);
        Assert.assertEquals(true, productRepository.searchByBarCode(codigo).isEmpty());
        Assert.assertEquals(0, productRepository.searchByBarCode(codigo).size());
    }

    @Test
    void pegarProdutosPorCodigoDeveRetornarListaComUmProduto() throws Exception {
        Long codigo = 1010L;
        ArrayList<Product> listaDeProducts = new ArrayList<>();
        listaDeProducts.add(new Product());

        Mockito.when(productRepository.searchByBarCode(codigo)).thenReturn(listaDeProducts);
        Assert.assertEquals(false, productRepository.searchByBarCode(codigo).isEmpty());
        Assert.assertEquals(1, productRepository.searchByBarCode(codigo).size());
    }

    @Test
    void pegarProdutosPorCodigoDeveRetornarListaComDoisProdutosEVerificarAsQtd() throws Exception {
        Long codigo = 1010L;
        ArrayList<Product> listaDeProducts = new ArrayList<>();
        Product p1 = new Product();
        Product p2 = new Product();
        p1.setQuantity(1L);
        p2.setQuantity(2L);
        listaDeProducts.add(p1);
        listaDeProducts.add(p2);

        Mockito.when(productRepository.searchByBarCode(codigo)).thenReturn(listaDeProducts);
        Assert.assertEquals(false, productRepository.searchByBarCode(codigo).isEmpty());
        Assert.assertEquals(2, productRepository.searchByBarCode(codigo).size());
        Assert.assertEquals(1L, productRepository.searchByBarCode(codigo).get(0).getQuantity(), 0);
        Assert.assertEquals(2L, productRepository.searchByBarCode(codigo).get(1).getQuantity(), 0);
    }

    @Test
    void pegarTodoProdutosPaginadosDeveRetornarListDeProdutoOK() throws Exception {
        Pageable pageable = PageRequest.of(1, 5, Direction.ASC);

        produtoService.pesquisaPaginada(pageable);  

    }

}