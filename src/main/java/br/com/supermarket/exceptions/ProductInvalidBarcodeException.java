package br.com.supermarket.exceptions;

public class ProductInvalidBarcodeException extends RuntimeException{
    public ProductInvalidBarcodeException(String message) {
        super(message);
    }
}
