package br.com.supermercado.exceptions;

public class ProdutoDescricaInvalidaException extends RuntimeException {
    public ProdutoDescricaInvalidaException(String message) {
        super(message);
    }
}
