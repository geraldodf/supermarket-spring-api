package br.com.supermarket.exceptions;

public class ProductDescriptionInvalidException extends RuntimeException {
    public ProductDescriptionInvalidException(String message) {
        super(message);
    }
}
