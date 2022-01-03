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

    public Produto pegarUmProduto(Long id) {
        Optional<Produto> produtoBuscadoPeloID = produtoRepository.findById(id);
        return produtoBuscadoPeloID.get();
    }

    public void criarProduto(Produto produto) throws Exception {
        if (produto.getCodigo() == null) {
            throw new Exception("O produto deve ter um código! Verifique se o código foi informado e tente novamente.");
        }
        if (produto.getDescricao().length() <= 5) {
            throw new Exception("Descrição inválida! A descrição do produto deve ter no mínimo 5 caracteres, verifique e tente novamente.");
        }
        if (produto.getPreco() == null) {
            throw new Exception("O produto deve ter um preço! Verifique se o preço foi informado e tente novamente.");
        }
        if (produto.getQuantidade() == null) {
            throw new Exception("Quantidade deve ser listada! Verifique se a quantidade foi informada e tente novamente.");
        }
        try {
            if (produto.getQuantidade() != null && produto.getPreco() != null && produto.getCodigo() != null && produto.getDescricao() != null) {
                produtoRepository.save(produto);
            } else {
                throw new Exception();
            }


        } catch (Exception e) {
            throw new Exception("Produto está com algum atributo inválido! Tente novamente.");
        }
    }

    public void excluirProduto(Long id) throws Exception {
        if (produtoRepository.pesquisaPorCodigo(id) == null){
            throw new Exception("Produto inexistente! verifique e tente novamente.");
        }
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

    public ArrayList<Produto> pesquisaProdutoPorCodigo(Long codigo) throws Exception {
        if (produtoRepository.pesquisaPorCodigo(codigo) == null){
            throw new Exception("Produto inexistente! verifique e tente novamente.");
        }
        return produtoRepository.pesquisaPorCodigo(codigo);
    }

    public ArrayList<Produto> pesquisaProdutoPorDescricao(String descricao) throws Exception {
        if (produtoRepository.pesquisaPorDescricao(descricao) == null){
            throw new Exception("Produto inexistente! verifique e tente novamente.");
        }
        return produtoRepository.pesquisaPorDescricao(descricao);
    }
}
