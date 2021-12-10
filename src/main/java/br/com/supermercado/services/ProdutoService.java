package br.com.supermercado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.supermercado.models.Produto;
import br.com.supermercado.repositories.ProdutoRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ArrayList<Produto> pegarTodosProdutos() {
        return (ArrayList<Produto>) produtoRepository.findAll();
    }

    public void criarProduto(Produto produto) {
        produtoRepository.save(produto);
    }

    public Produto pegarUmProduto(Long id) {
       Optional<Produto> produtoBuscadoPeloID = produtoRepository.findById(id);
       return produtoBuscadoPeloID.get();
    }
}
