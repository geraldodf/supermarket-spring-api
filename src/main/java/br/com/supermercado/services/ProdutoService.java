package br.com.supermercado.services;

import br.com.supermercado.Dto.ProdutoDto;
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

    public Produto pegarUmProduto(Long id) throws Exception {
        try {
            Optional<Produto> produtoBuscadoPeloID = produtoRepository.findById(id);
            return produtoBuscadoPeloID.get();
        } catch (Exception e) {
            throw new Exception("Produto inválido! tente novamente.");
        }
    }

    public void criarProduto(ProdutoDto produtoDto) throws Exception {
        Produto produto = criandoProdutoComDto(produtoDto);
        verificarProduto(produto);
        try {
            if (produto.getQuantidade() != null && produto.getPrecoDeCompra() != null && produto.getPrecoDeVenda() != null && produto.getCodigo() != null && produto.getDescricao().length() >= 5) {
                produto.setLucroLiquido(produto.getPrecoDeVenda().subtract(produto.getPrecoDeCompra()));
                produtoRepository.save(produto);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Produto está com algum atributo inválido! Tente novamente.");
        }
    }

    public void excluirProduto(Long id) throws Exception {
        if (produtoRepository.pesquisaPorCodigo(id) == null) {
            throw new Exception("Produto inexistente! verifique e tente novamente.");
        }
        produtoRepository.deleteById(id);
    }

    public void atualizarProduto(Long id, ProdutoDto produtoDto) throws Exception {
        if (id == null) {
            throw new Exception("Produto inexistente!");
        }
        Optional<Produto> resposta = produtoRepository.findById(id);
        Produto produtoAAtualizar = resposta.get();
        Produto produto = criandoProdutoComDto(produtoDto);
        verificarProduto(produto);

        try {
            if (produto.getQuantidade() != null && produto.getPrecoDeVenda() != null && produto.getLucroLiquido() != null &&
                produto.getPrecoDeCompra() != null && produto.getCodigo() != null && produto.getDescricao() != null) {
                produtoAAtualizar.setCodigo(produto.getCodigo());
                produtoAAtualizar.setDescricao(produto.getDescricao());
                produtoAAtualizar.setPrecoDeVenda(produto.getPrecoDeVenda());
                produtoAAtualizar.setPrecoDeCompra(produto.getPrecoDeCompra());
                produtoAAtualizar.setQuantidade(produto.getQuantidade());
                produtoAAtualizar.setLucroLiquido(produto.getPrecoDeVenda().subtract(produto.getPrecoDeCompra()));
                produtoRepository.save(produtoAAtualizar);

            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Produto está com algum atributo inválido! Tente novamente.");
        }
    }

    public ArrayList<Produto> pesquisaProdutoPorCodigo(Long codigo) throws Exception {
        if (produtoRepository.pesquisaPorCodigo(codigo) == null) {
            throw new Exception("Produto inexistente! verifique e tente novamente.");
        }
        return produtoRepository.pesquisaPorCodigo(codigo);
    }

    public ArrayList<Produto> pesquisaProdutoPorDescricao(String descricao) throws Exception {
        if (produtoRepository.pesquisaPorDescricao(descricao) == null) {
            throw new Exception("Produto inexistente! verifique e tente novamente.");
        }
        return produtoRepository.pesquisaPorDescricao(descricao);
    }

    public void verificarProduto(Produto produto) throws Exception {
        if (produto.getCodigo() == null) {
            throw new Exception("O produto deve ter um código! Verifique se o código foi informado e tente novamente.");
        }
        if (produto.getDescricao().length() <= 5) {
            throw new Exception("Descrição inválida! A descrição do produto deve ter no mínimo 5 caracteres, verifique e tente novamente.");
        }
        if (produto.getPrecoDeVenda() == null) {
            throw new Exception("O produto deve ter um preço de venda! Verifique se o preço foi informado e tente novamente.");
        }
        if (produto.getPrecoDeCompra() == null) {
            throw new Exception("O produto deve ter um preço de compra! Verifique se o preço foi informado e tente novamente.");
        }
        if (produto.getQuantidade() == null) {
            throw new Exception("Quantidade deve ser listada! Verifique se a quantidade foi informada e tente novamente.");
        }
        if (produto.getLucroLiquido() == null) {
            throw new Exception("Quantidade deve ser listada! Verifique se a quantidade foi informada e tente novamente.");
        }
    }

    public Produto criandoProdutoComDto(ProdutoDto produtoDto) {
        Produto produto = new Produto();
        produto.setCodigo(produtoDto.getCodigo());
        produto.setDescricao(produtoDto.getDescricao());
        produto.setQuantidade(produtoDto.getQuantidade());
        produto.setPrecoDeCompra(produtoDto.getPrecoDeCompra());
        produto.setPrecoDeVenda(produtoDto.getPrecoDeVenda());
        produto.setLucroLiquido(produtoDto.getPrecoDeCompra().subtract(produtoDto.getPrecoDeVenda()));
        return produto;
    }
}
