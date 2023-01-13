package br.com.supermarket.exceptions;

public class ProductDescriptionNullException extends RuntimeException {
    public ProductDescriptionNullException(String mensagem) {
        super(mensagem);
    }
}
