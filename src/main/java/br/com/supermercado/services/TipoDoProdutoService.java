package br.com.supermercado.services;

import br.com.supermercado.models.TipoDoProduto;
import br.com.supermercado.repositories.TipoDoProdutoServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TipoDoProdutoService {

    @Autowired
    private TipoDoProdutoServiceRepository tipoDoProdutoServiceRepository;

    public ArrayList<TipoDoProduto> pegarTodosTiposDosProdutos() {
        return (ArrayList<TipoDoProduto>) tipoDoProdutoServiceRepository.findAll();
    }

    public TipoDoProduto criarTipoDoProduto(TipoDoProduto tipo) {

         return tipoDoProdutoServiceRepository.save(tipo);
    }

    public TipoDoProduto pegarUmTipoDoProdutoPeloId(Long id) {
        Optional<TipoDoProduto> retorno = tipoDoProdutoServiceRepository.findById(id);
        return retorno.get();
    }
}
