package br.com.supermarket.services;

import br.com.supermarket.dtos.ProductDto;
import br.com.supermarket.exceptions.ProductDescriptionInvalidException;
import br.com.supermarket.exceptions.ProductDescriptionNullException;
import br.com.supermarket.exceptions.ProductNullBarcodeException;
import br.com.supermarket.exceptions.ProductProfitInconsistentException;
import br.com.supermarket.models.Product;
import br.com.supermarket.models.ProductType;
import br.com.supermarket.repositories.ProductRepository;
import org.junit.Assert;
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
import java.util.Optional;

@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductTypeService productTypeService;
    @Mock
    private ProductRepository productRepository;
    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Test
    void createProductMustLaunchProductDescriptionInvalidException() {
        ProductDto productDto = new ProductDto();
        productDto.setDescription("F");
        productDto.setBarCode(12345L);
        productDto.setQuantity(250L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Assert.assertThrows(ProductDescriptionInvalidException.class, () -> productService.createProduct(productDto));
    }

    @Test
    void createProductMustLaunchProductDescriptionInvalidExceptionWithFiveCharacters() {
        ProductDto productDto = new ProductDto();
        productDto.setDescription("*****");
        productDto.setBarCode(12345L);
        productDto.setQuantity(250L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Assert.assertThrows(ProductDescriptionInvalidException.class, () -> productService.createProduct(productDto));
    }

    @Test
    void createProductMustLaunchProductDescriptionNullException() {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(null);
        productDto.setBarCode(12345L);
        productDto.setQuantity(250L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Assert.assertThrows(ProductDescriptionNullException.class, () -> productService.createProduct(productDto));
    }

    @Test
    void createProductShouldThrowProductNullBarcodeException() {
        ProductDto productDto = new ProductDto();
        productDto.setDescription("******");
        productDto.setBarCode(null);
        productDto.setQuantity(250L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Assert.assertThrows(ProductNullBarcodeException.class, () -> productService.createProduct(productDto));
    }

    @Test
    void createProductNegativeQuantityShouldNotThrowException() throws Exception {

        ProductType type = new ProductType();
        type.setNameProductType("******");
        type.setProductList(null);
        Mockito.when(productTypeService.getProductTypeById(2L)).thenReturn(type);

        ProductDto productDto = new ProductDto();
        productDto.setDescription("******");
        productDto.setBarCode(12345L);
        productDto.setQuantity(-60L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Product product = productService.createProduct(productDto);

        Assertions.assertEquals(-60, product.getQuantity());
    }

    @Test
    void createProductShouldReturnProductProfitInconsistentException() throws Exception {

        ProductType type = new ProductType();
        type.setNameProductType("******");
        type.setProductList(null);
        Mockito.when(productTypeService.getProductTypeById(2L)).thenReturn(type);

        ProductDto productDto = new ProductDto();
        productDto.setDescription("******");
        productDto.setBarCode(12345L);
        productDto.setQuantity(2L);
        productDto.setPriceBuy(BigDecimal.valueOf(1.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Product product = productService.createProduct(productDto);
        product.setNetProfit(new BigDecimal("0.9"));

        Assert.assertThrows(ProductProfitInconsistentException.class, product::autoVerify);
    }

    @Test
    void createProductWithNegativeProfitShouldNotGenerateError() throws Exception {

        ProductType type = new ProductType();
        type.setNameProductType("******");
        type.setProductList(null);
        Mockito.when(productTypeService.getProductTypeById(2L)).thenReturn(type);

        ProductDto productDto = new ProductDto();
        productDto.setDescription("******");
        productDto.setBarCode(12345L);
        productDto.setQuantity(2L);
        productDto.setPriceBuy(BigDecimal.valueOf(2.79));
        productDto.setPriceSale(BigDecimal.valueOf(2.49));
        productDto.setIdProductType(2L);

        Product product = productService.createProduct(productDto);
        System.out.println(product.getNetProfit());

        Assertions.assertEquals(product.getNetProfit().doubleValue(), -0.3, 0);
    }

    @Test
    void getAllProductsShouldReturnEmptyProductsList() {
        ArrayList<Product> productsList = new ArrayList<>();
        Mockito.when(productService.getAllProducts()).thenReturn(productsList);
        Assertions.assertEquals(productService.getAllProducts().size(), 0);
        Assertions.assertTrue(productService.getAllProducts().isEmpty());
    }

    @Test
    void getAllProductsShouldReturnListOfProductsWithOneProduct() {
        ArrayList<Product> ProductsList = new ArrayList<>();
        ProductsList.add(new Product());

        Mockito.when(productRepository.findAll()).thenReturn(ProductsList);
        Assertions.assertEquals(1, productService.getAllProducts().size());
        Assertions.assertFalse(productService.getAllProducts().isEmpty());
    }

    @Test
    void getAllProductsShouldReturnListOfProductsWithTwoProducts() {
        ArrayList<Product> productsList = new ArrayList<>();
        productsList.add(new Product());
        productsList.add(new Product());

        Mockito.when(productRepository.findAll()).thenReturn(productsList);
        Assertions.assertEquals(2, productService.getAllProducts().size());
        Assertions. assertFalse(productService.getAllProducts().isEmpty());

    }

    @Test
    void getAllProductsShouldReturnListOfProductsWithTwoProductsAndCheckingDescription() {
        ArrayList<Product> productsList = new ArrayList<>();
        productsList.add(new Product());
        productsList.add(new Product());
        Product product = new Product();
        product.setDescription("Description test");
        productsList.add(product);

        Mockito.when(productRepository.findAll()).thenReturn(productsList);
        Assertions.assertEquals(3, productService.getAllProducts().size());
        Assertions.assertFalse(productService.getAllProducts().isEmpty());
        Assertions.assertEquals("Description test", productService.getAllProducts().get(2).getDescription());
    }

    @Test
    void getProductByIdShouldReturnEmptyProduct() throws Exception {
        Long id = 3L;
        Mockito.when(productRepository.findById(id)).thenReturn(Optional.of(new Product()));
        Assertions.assertNotNull(productService.getProductById(id));
    }

    @Test
    void getProductsByDescriptionShouldReturnEmptyProductList() {
        String description = "Test";
        ArrayList<Product> productsList = new ArrayList<>();

        Mockito.when(productRepository.searchByDescription(description)).thenReturn(productsList);
        Assertions.assertTrue(productRepository.searchByDescription(description).isEmpty());
        Assertions.assertEquals(0, productRepository.searchByDescription(description).size());
    }

    @Test
    void getProductsByDescriptionShouldReturnListWithOneProduct() {
        String description = "Test";
        ArrayList<Product> productsList = new ArrayList<>();
        productsList.add(new Product());

        Mockito.when(productRepository.searchByDescription(description)).thenReturn(productsList);
        Assertions.assertFalse(productRepository.searchByDescription(description).isEmpty());
        Assertions.assertEquals(1, productRepository.searchByDescription(description).size());
    }

    @Test
    void getProductsByDescriptionShouldReturnListWithTwoProductsAndCheckDescriptions() {
        String description = "******";
        ArrayList<Product> productsList = new ArrayList<>();
        Product p1 = new Product();
        Product p2 = new Product();
        p1.setDescription("Test one");
        p2.setDescription("Test two");
        productsList.add(p1);
        productsList.add(p2);

        Mockito.when(productRepository.searchByDescription(description)).thenReturn(productsList);
        Assertions.assertFalse(productRepository.searchByDescription(description).isEmpty());
        Assertions.assertEquals(2, productRepository.searchByDescription(description).size());
        Assertions.assertEquals("Test one", productRepository.searchByDescription(description).get(0).getDescription());
        Assertions.assertEquals("Test two", productRepository.searchByDescription(description).get(1).getDescription());
    }

    @Test
    void getProductsByCodeShouldReturnEmptyProductList() {
        Long barCode = 1010L;
        ArrayList<Product> productsList = new ArrayList<>();

        Mockito.when(productRepository.searchByBarCode(barCode)).thenReturn(productsList);
        Assertions.assertTrue(productRepository.searchByBarCode(barCode).isEmpty());
        Assertions.assertEquals(0, productRepository.searchByBarCode(barCode).size());
    }

    @Test
    void getProductsByCodeShouldReturnListWithOneProduct()  {
        Long barCode = 1010L;
        ArrayList<Product> productsList = new ArrayList<>();
        productsList.add(new Product());

        Mockito.when(productRepository.searchByBarCode(barCode)).thenReturn(productsList);
        Assertions.assertFalse(productRepository.searchByBarCode(barCode).isEmpty());
        Assertions.assertEquals(1, productRepository.searchByBarCode(barCode).size());
    }

    @Test
    void getProductsByCodeShouldReturnListWithTwoProductsAndCheckQuantities() {
        Long barCode = 1010L;
        ArrayList<Product> productsList = new ArrayList<>();
        Product p1 = new Product();
        Product p2 = new Product();
        p1.setQuantity(1L);
        p2.setQuantity(2L);
        productsList.add(p1);
        productsList.add(p2);

        Mockito.when(productRepository.searchByBarCode(barCode)).thenReturn(productsList);
        Assertions.assertFalse(productRepository.searchByBarCode(barCode).isEmpty());
        Assertions.assertEquals(2, productRepository.searchByBarCode(barCode).size());
        Assertions.assertEquals(1L, productRepository.searchByBarCode(barCode).get(0).getQuantity(), 0);
        Assertions.assertEquals(2L, productRepository.searchByBarCode(barCode).get(1).getQuantity(), 0);
    }

}