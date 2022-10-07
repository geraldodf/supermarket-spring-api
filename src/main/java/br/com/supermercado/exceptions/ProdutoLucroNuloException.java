package br.com.supermercado.exceptions;

public class ProdutoLucroNuloException extends RuntimeException {
    public ProdutoLucroNuloException(String message) {
        super(message);
    }
}
