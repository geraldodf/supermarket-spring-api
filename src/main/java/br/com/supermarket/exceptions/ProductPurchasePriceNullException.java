package br.com.supermarket.exceptions;

public class ProductPurchasePriceNullException extends RuntimeException {
    public ProductPurchasePriceNullException(String message) {
        super(message);
    }
}
