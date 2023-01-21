package br.com.supermarket.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaleDto {
    private List<Long> productId;
}
