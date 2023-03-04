package br.com.supermarket.models;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.*;

import ch.qos.logback.core.subst.Token.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_sales")
public class ProductSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_sales_id")
    private Long id;

    @Column(name = "sale_fk")
    private Long idSale;

    @Column(name = "product_fk")
    private Long idProduct;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "quantity_product")
    private Long quantityProduct;

}
