package br.com.supermercado.exceptions;

public class ProdutoPrecoDeCompraNuloException extends RuntimeException {
    public ProdutoPrecoDeCompraNuloException(String message) {
        super(message);
    }
}
