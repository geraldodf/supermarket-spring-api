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
            joinColumns = @JoinColumn(name = "salefk"),
            inverseJoinColumns = @JoinColumn(name = "productfk")
    )
    private List<Product> productList;

}
