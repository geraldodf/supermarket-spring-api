package supermercado.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import supermercado.api.models.Produto;
import supermercado.api.repositories.ProdutoRepository;

import java.util.ArrayList;
import java.util.Optional;


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
