package br.com.supermarket.exceptions;

public class ProductQuantityNullException extends RuntimeException {
    public ProductQuantityNullException(String message) {
        super(message);
    }
}
