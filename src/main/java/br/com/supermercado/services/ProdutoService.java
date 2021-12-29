package br.com.supermercado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.supermercado.models.Produto;
import br.com.supermercado.repositories.ProdutoRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ArrayList<Produto> pegarTodosProdutos() {
        return (ArrayList<Produto>) produtoRepository.findAll();
    }

    public Produto pegarUmProduto(Long id) {
        Optional<Produto> produtoBuscadoPeloID = produtoRepository.findById(id);
        return produtoBuscadoPeloID.get();
    }

    public void criarProduto(Produto produto) {
        produtoRepository.save(produto);
    }


    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public void atualizarProduto(Long id, Produto produto) {
        Optional<Produto> resposta = produtoRepository.findById(id);
        Produto novoProduto = resposta.get();
        novoProduto.setCodigo(produto.getCodigo());
        novoProduto.setDescricao(produto.getDescricao());
        novoProduto.setPreco(produto.getPreco());
        novoProduto.setQuantidade(produto.getQuantidade());
        produtoRepository.save(novoProduto);
    }

    public ArrayList<Produto> pesquisaProdutoPorCodigo(Long codigo) {
        return  produtoRepository.pesquisaPorCodigo(codigo);
    }

    public ArrayList<Produto> pesquisaProdutoPorDescricao(String descricao) {
      return produtoRepository.pesquisaPorDescricao(descricao);
    }
}
