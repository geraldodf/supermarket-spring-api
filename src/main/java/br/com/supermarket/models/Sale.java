package br.com.supermarket.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

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

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<ProductSale> productSaleList;

    public Sale() {
        this.saleValue = new BigDecimal(0.00);
        this.productSaleList = new ArrayList<ProductSale>();
    }

   
    public void autoVerify() throws Exception {
        if (this.getSaleValue() == null) {
            throw new Exception("You cannot create a sale that does not have a value.");
        }
        if (this.getSaleDate() == null) {
            throw new Exception("The Sale must have a valid date.");
        }
    }
}
