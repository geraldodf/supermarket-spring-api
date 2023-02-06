package br.com.supermarket.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @Column(name = "sale_date")
    private String saleDate;

    @Column(name = "sale_value")
    private BigDecimal saleValue;

    @ManyToMany
    @JoinTable(
            name = "sales_products",
            joinColumns = @JoinColumn(name = "sale_fk"),
            inverseJoinColumns = @JoinColumn(name = "product_fk")
    )
    private List<Product> productList;

    public Sale(Long id, String saleDate, BigDecimal saleValue, List<Product> productList) {
        this.id = id;
        this.saleDate = saleDate;
        this.saleValue = saleValue;
        this.productList = productList;
    }

    public Sale() {
        this.saleValue = new BigDecimal(0.00);
    }
}
