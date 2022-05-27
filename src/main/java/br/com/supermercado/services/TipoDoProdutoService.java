package br.com.supermercado.services;

import br.com.supermercado.repositories.TipoDoProdutoServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoDoProdutoService {

    @Autowired
    private TipoDoProdutoServiceRepository tipoDoProdutoServiceRepository;

    public void pegarTodosTiposDosProdutos() {
        tipoDoProdutoServiceRepository.findAll();
    }
}
