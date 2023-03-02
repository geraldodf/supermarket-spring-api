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
    private Long id;

    @Column(name = "id_sale")
    private Long idSale;

    @Column(name = "id_sale")
    private ArrayList<Long> idProduct;

    @Column(name = "quantity_product")
    private Long quantityProduct;

}
