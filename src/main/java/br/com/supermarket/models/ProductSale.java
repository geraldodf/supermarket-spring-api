package br.com.supermarket.models;

import java.math.BigDecimal;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_fk")
    @JsonIgnore
    private Sale sale;

    @Column(name = "product_fk")
    private Long idProduct;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "quantity_product")
    private Long quantityProduct;

}
