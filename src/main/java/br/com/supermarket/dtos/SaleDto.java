package br.com.supermarket.dtos;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.ArrayList;
import br.com.supermarket.models.Product;

@Getter
@Setter
public class SaleDto {
    private ArrayList<Product> productList;
    private BigDecimal saleValue;
}
