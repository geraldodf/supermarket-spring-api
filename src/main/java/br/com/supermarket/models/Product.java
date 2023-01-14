package br.com.supermarket.models;

import lombok.Getter;
import lombok.Setter;
import br.com.supermarket.exceptions.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "produtos")
public class Product {

    @Id
    @Column(name = "produto_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "produto_descricao")
    private String description;

    @Column(name = "produto_preco_compra")
    private BigDecimal priceBuy;

    @Column(name = "produto_preco_venda")
    private BigDecimal priceSale;

    @Column(name = "produto_lucro_liquido")
    private BigDecimal netProfit;

    @Column(name = "produto_quantidade")
    private Long quantity;

    @Column(name = "produto_codigo_barras")
    private Long barCode;

    @Column(name = "produto_data_criacao")
    private String creationDate;

    @ManyToOne
    @JoinColumn(name = "tipo_produto_fk")
    private ProductType productType;

    @Override
    public String toString() {

        return ("Description: " + this.description + "\n"
                +
                "Price: " + this.priceSale + "\n" +
                "Quantity: " + this.quantity + "\n" +
                "Bar Code: " + this.barCode + "\n" +
                "Date: " + this.creationDate + "\n");
    }

    public void autoVerify() {
        if (this.barCode == null) {
            throw new ProductNullBarcodeException("The barcode is null.");
        }
        if (this.barCode < 0) {
            throw new ProductInvalidBarcodeException("The barcode is invalid! The barcode must be a number and cannot be negative.");
        }
        if (this.description == null) {
            throw new ProductDescriptionNullException(
                    "The product description is void! Product description must be at least 5 characters long.");
        }
        if (this.description.length() <= 5) {
            throw new ProductDescriptionInvalidException(
                    "Invalid description! Product description must be at least 5 characters long.");
        }
        if (this.priceSale == null) {
            throw new ProductSalePriceNullException("The product must have a selling price.");
        }
        if (this.priceBuy == null) {
            throw new ProductPurchasePriceNullException("The product must have a purchase price.");
        }
        if (this.quantity == null) {
            throw new ProductQuantityNullException("Quantity must be listed.");
        }
        if (this.netProfit == null) {
            throw new ProductProfitNullException("Net profit is zero, there was a problem.");
        }
        if (this.creationDate == null) {
            throw new ProductDateOfCreationNullException("Error generating creation date.");
        }
        if (this.productType == null) {
            throw new TypeProductNullException("Invalid product type.");
        }
        if (this.priceSale.subtract(this.priceBuy).doubleValue() != this.netProfit.doubleValue()) {
            throw new ProductProfitInconsistentException("Net income value is inconsistent.");
        }
    }

    public boolean verifyProductAttributesNoNull() {
        if (this.quantity != null && this.priceSale != null && this.netProfit != null &&
                this.priceBuy != null && this.barCode != null && this.description != null) {
            return true;
        } else
            return false;
    }

}
