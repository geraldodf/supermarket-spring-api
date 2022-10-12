package br.com.supermercado.exceptions;

public class ProdutoCodigoInvalidoException extends RuntimeException{
    public ProdutoCodigoInvalidoException(String message) {
        super(message);
    }
}
