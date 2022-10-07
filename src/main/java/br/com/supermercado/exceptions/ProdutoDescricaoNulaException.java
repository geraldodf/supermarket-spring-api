package br.com.supermercado.exceptions;

public class ProdutoDescricaoNulaException extends RuntimeException {
    public ProdutoDescricaoNulaException(String mensagem) {
        super(mensagem);
    }
}
