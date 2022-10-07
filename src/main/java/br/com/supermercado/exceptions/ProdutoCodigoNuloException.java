package br.com.supermercado.exceptions;

public class ProdutoCodigoNuloException extends RuntimeException {
    public ProdutoCodigoNuloException(String mensagem) {
        super(mensagem);
    }
}
