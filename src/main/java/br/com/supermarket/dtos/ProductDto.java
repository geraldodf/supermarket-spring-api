package br.com.supermarket.dtos;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {
    private String description;
    private BigDecimal priceBuy;
    private BigDecimal priceSale;
    private Long quantity;
    private Long barCode;
    private Long idProductType;
}
