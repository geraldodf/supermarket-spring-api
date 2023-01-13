package br.com.supermarket.exceptions;

public class ProductNullBarcodeException extends RuntimeException {
    public ProductNullBarcodeException(String mensagem) {
        super(mensagem);
    }
}
