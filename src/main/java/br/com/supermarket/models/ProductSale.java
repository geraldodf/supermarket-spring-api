package br.com.supermarket.models;

import java.util.ArrayList;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_sales")
public class ProductSale {

    @Id
    @GeneratedValue
    @Column(name = "product_sales_id")
    private Long id;

    @Column(name = "sale_fk")
    private Long idSale;

    @Column(name = "product_fk")
    private Long idProduct;

    @Column(name = "quantity_product")
    private Long quantityProduct;

}
