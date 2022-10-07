package br.com.supermercado.exceptions;

public class ProdutoQuantidadeNulaException extends RuntimeException {
    public ProdutoQuantidadeNulaException(String message) {
        super(message);
    }
}
