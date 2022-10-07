package br.com.supermercado.exceptions;

public class ProdutoPrecoDeVendaNuloException extends RuntimeException {
    public ProdutoPrecoDeVendaNuloException(String message) {
        super(message);
    }
}
