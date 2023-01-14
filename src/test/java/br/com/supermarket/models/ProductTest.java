package br.com.supermarket.models;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import br.com.supermarket.exceptions.*;
import br.com.supermarket.exceptions.ProductDescriptionInvalidException;

@SpringBootTest
class ProductTest {

    @Test
    void verificarProdutoDeveRetornarOk() throws Exception {

        Product product = produtoCriado();

        product.autoVerify();

        Assert.assertEquals(123L, product.getBarCode(), 0);
        Assert.assertEquals("Hoje", product.getCreationDate());
        Assert.assertEquals("Teste Descricao", product.getDescription());
        Assert.assertEquals(BigDecimal.valueOf(1.00), product.getNetProfit());
        Assert.assertEquals(BigDecimal.valueOf(3.00), product.getPriceSale());
        Assert.assertEquals(BigDecimal.valueOf(2.00), product.getPriceBuy());
        Assert.assertEquals(10L, product.getQuantity(), 0);
        Assert.assertNotNull(product.getProductType());
    }

    @Test
    void verificarProdutoNaoNuloDoProdutoCriadoNaoDeveLancarExcecao() {
        Product product = produtoCriado();
        product.verifyProductAttributesNoNull();
    }

    @Test
    void verificarProdutoDeveLancarProdutoDescricaoInvalidaException() {
        Product product = produtoCriado();
        product.setDescription("");
        Assert.assertThrows(ProductDescriptionInvalidException.class, () -> product.autoVerify());
    }

    @Test
    void verificarProdutoDeveLancarProdutoDescricaoInvalidaExceptionCom5Caracteres() {
        Product product = produtoCriado();
        product.setDescription("Teste");
        Assert.assertThrows(ProductDescriptionInvalidException.class, () -> product.autoVerify());
    }

    @Test
    void verificarProdutoDeveLancarProdutoDescricaoNulaException() {
        Product product = produtoCriado();
        product.setDescription(null);
        Assert.assertThrows(ProductDescriptionNullException.class, () -> product.autoVerify());
    }

    @Test
    void verificarProdutoDeveLancarProdutoCodigoNuloException() {
        Product product = produtoCriado();
        product.setBarCode(null);
        Assert.assertThrows(ProductNullBarcodeException.class, () -> product.autoVerify());
    }

    @Test
    void verificarProdutoDeveLancarProdutoCodigoInvalidoException() {
        Product product = produtoCriado();
        product.setBarCode(-213L);
        Assert.assertThrows(ProductInvalidBarcodeException.class, () -> product.autoVerify());
    }

    @Test
    void verificarProdutoDeveLancarProdutoDataDeCriacaoNulaException() {
        Product product = produtoCriado();
        product.setCreationDate(null);
        Assert.assertThrows(ProductDateOfCreationNullException.class, () -> product.autoVerify());

    }

    @Test
    void verificarProdutoDeveLancarProdutoLucroNuloException() {
        Product product = produtoCriado();
        product.setNetProfit(null);
        Assert.assertThrows(ProductProfitNullException.class, () -> product.autoVerify());
    }

    @Test
    void verificarProdutoDeveLancarProdutoLucroInconsistenteException() {
        Product product = produtoCriado();
        product.setNetProfit(BigDecimal.valueOf(5000));
        Assert.assertThrows(ProductProfitInconsistentException.class, () -> product.autoVerify());
    }

    @Test
    void verificarProdutoDeveLancarProdutoPrecoDeCompraNuloException() {
        Product product = produtoCriado();
        product.setPriceBuy(null);
        Assert.assertThrows(ProductPurchasePriceNullException.class, () -> product.autoVerify());
    }

    @Test
    void verificarProdutoDeveLancarProdutoPrecoDeVendaNuloException() {
        Product product = produtoCriado();
        product.setPriceSale(null);
        Assert.assertThrows(ProductSalePriceNullException.class, () -> product.autoVerify());
    }

    @Test
    void verificarProdutoDeveLancarProdutoQuantidadeNulaException() {
        Product product = produtoCriado();
        product.setQuantity(null);
        Assert.assertThrows(ProductQuantityNullException.class, () -> product.autoVerify());
    }

    @Test
    void verificarProdutoNaoDeveLancarProblemaComQuantidadeNegativa() {
        Product product = produtoCriado();
        product.setQuantity(-200L);
    }

    Product produtoCriado() {
        Product product = new Product();
        product.setBarCode(123L);
        product.setCreationDate("Hoje");
        product.setDescription("Teste Descricao");
        product.setNetProfit(BigDecimal.valueOf(1.00));
        product.setPriceBuy(BigDecimal.valueOf(2.00));
        product.setPriceSale(BigDecimal.valueOf(3.00));
        product.setQuantity(10L);
        product.setProductType(new ProductType());
        return product;
    }
}