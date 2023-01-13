package br.com.supermarket.exceptions;

public class ProductSalePriceNullException extends RuntimeException {
    public ProductSalePriceNullException(String message) {
        super(message);
    }
}
