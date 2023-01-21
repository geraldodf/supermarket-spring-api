package br.com.supermarket.models;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import br.com.supermarket.exceptions.*;

@SpringBootTest
class ProductTest {

    @Test
    void checkProductMustReturnOk() {

        Product product = createdProduct();

        product.autoVerify();

        Assertions.assertEquals(123L, product.getBarCode(), 0);
        Assertions.assertEquals("Today", product.getCreationDate());
        Assertions.assertEquals("Test Description", product.getDescription());
        Assertions.assertEquals(BigDecimal.valueOf(1.00), product.getNetProfit());
        Assertions.assertEquals(BigDecimal.valueOf(3.00), product.getPriceSale());
        Assertions.assertEquals(BigDecimal.valueOf(2.00), product.getPriceBuy());
        Assertions.assertEquals(10L, product.getQuantity(), 0);
        Assertions.assertNotNull(product.getProductType());
    }

    @Test
    void productCannotBeNull() {
        Product product = createdProduct();
        product.verifyProductAttributesNoNull();
    }

    @Test
    void shouldThrowProductDescriptionInvalidException() {
        Product product = createdProduct();
        product.setDescription("");
        Assert.assertThrows(ProductDescriptionInvalidException.class, product::autoVerify);
    }

    @Test
    void shouldThrowProductDescriptionInvalidExceptionWithFiveCharacters() {
        Product product = createdProduct();
        product.setDescription("*****");
        Assert.assertThrows(ProductDescriptionInvalidException.class, product::autoVerify);
    }

    @Test
    void shouldThrowProductDescriptionNullException() {
        Product product = createdProduct();
        product.setDescription(null);
        Assert.assertThrows(ProductDescriptionNullException.class, product::autoVerify);
    }

    @Test
    void shouldThrowProductNullBarcodeException() {
        Product product = createdProduct();
        product.setBarCode(null);
        Assert.assertThrows(ProductNullBarcodeException.class, product::autoVerify);
    }

    @Test
    void shouldThrowProductInvalidBarcodeException() {
        Product product = createdProduct();
        product.setBarCode(-213L);
        Assert.assertThrows(ProductInvalidBarcodeException.class, product::autoVerify);
    }

    @Test
    void shouldThrowProductDateOfCreationNullException() {
        Product product = createdProduct();
        product.setCreationDate(null);
        Assert.assertThrows(ProductDateOfCreationNullException.class, product::autoVerify);

    }

    @Test
    void shouldThrowProductProfitNullException() {
        Product product = createdProduct();
        product.setNetProfit(null);
        Assert.assertThrows(ProductProfitNullException.class, product::autoVerify);
    }

    @Test
    void shouldThrowProductProfitInconsistentException() {
        Product product = createdProduct();
        product.setNetProfit(BigDecimal.valueOf(5000));
        Assert.assertThrows(ProductProfitInconsistentException.class, product::autoVerify);
    }

    @Test
    void shouldThrowProductPurchasePriceNullException() {
        Product product = createdProduct();
        product.setPriceBuy(null);
        Assert.assertThrows(ProductPurchasePriceNullException.class, product::autoVerify);
    }

    @Test
    void shouldThrowProductSalePriceNullException() {
        Product product = createdProduct();
        product.setPriceSale(null);
        Assert.assertThrows(ProductSalePriceNullException.class, product::autoVerify);
    }

    @Test
    void shouldThrowProductQuantityNullException() {
        Product product = createdProduct();
        product.setQuantity(null);
        Assert.assertThrows(ProductQuantityNullException.class, product::autoVerify);
    }

    @Test
    void shouldNotGenerateProblemNegativeQuantity() {
        Product product = createdProduct();
        product.setQuantity(-200L);
    }

    Product createdProduct() {
        Product product = new Product();
        product.setBarCode(123L);
        product.setCreationDate("Today");
        product.setDescription("Test Description");
        product.setNetProfit(BigDecimal.valueOf(1.00));
        product.setPriceBuy(BigDecimal.valueOf(2.00));
        product.setPriceSale(BigDecimal.valueOf(3.00));
        product.setQuantity(10L);
        product.setProductType(new ProductType());
        return product;
    }
}