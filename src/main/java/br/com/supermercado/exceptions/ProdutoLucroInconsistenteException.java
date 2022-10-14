package br.com.supermercado.exceptions;

public class ProdutoLucroInconsistenteException extends RuntimeException {

    public ProdutoLucroInconsistenteException(String message) {
        super(message);
    }
}
