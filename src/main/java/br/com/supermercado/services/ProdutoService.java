package br.com.supermercado.services;

import br.com.supermercado.dtos.ProdutoDto;
import br.com.supermercado.exceptions.*;
import br.com.supermercado.models.TipoDoProduto;
import br.com.supermercado.util.DataUtilitario;
import com.sun.xml.bind.v2.TODO;
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
    @Autowired
    private TipoDoProdutoService tipoDoProdutoService;


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

    public Produto criarProduto(ProdutoDto produtoDto) throws Exception {
        Produto produto = criandoProdutoComDto(produtoDto);
        verificarProduto(produto);
        try {
            if (verificarAtributosProdutoNaoNulo(produto)
            ) {
                produto.setLucroLiquido(produto.getPrecoDeVenda().subtract(produto.getPrecoDeCompra()));
                produtoRepository.save(produto);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Produto está com algum atributo inválido! Tente novamente.");
        }
        return produto;
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
            if (verificarAtributosProdutoNaoNulo(produto)) {

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
            throw new ProdutoCodigoNuloException("O código está nulo.");
        }
        if (produto.getDescricao() == null) {
            throw new ProdutoDescricaoNulaException("A descrição do produto está nula! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (produto.getDescricao().length() <= 5) {
            throw new ProdutoDescricaInvalidaException("Descrição inválida! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (produto.getPrecoDeVenda() == null) {
            //Está gerando NullPointer e não chegando aqui!
            throw new ProdutoPrecoDeVendaNuloException("O produto deve ter um preço de venda.");
        }
        if (produto.getPrecoDeCompra() == null) {
            //Está gerando NullPointer e não chegando aqui!
            throw new ProdutoPrecoDeCompraNuloException("O produto deve ter um preço de compra.");
        }
        if (produto.getQuantidade() == null) {
            throw new ProdutoQuantidadeNulaException("Quantidade deve ser listada.");
        }
        if (produto.getLucroLiquido() == null) {
            throw new ProdutoLucroNuloException("Quantidade deve ser listada.");
        }
        if (produto.getDataDeCriacao() == null) {
            throw new ProdutoDataDeCriacaoNulaException("Erro ao gerar a Data.");
        }
        if (produto.getTipoDoProduto() == null) {
            //Esta dando erro ao buscar tipo.
            throw new ProdutoTipoDoProdutoNuloException("Tipo do produto inválido");
        }
    }

    public Produto criandoProdutoComDto(ProdutoDto produtoDto) {

        Produto produto = new Produto();
        produto.setCodigo(produtoDto.getCodigo());
        produto.setDescricao(produtoDto.getDescricao());
        produto.setQuantidade(produtoDto.getQuantidade());
        produto.setPrecoDeCompra(produtoDto.getPrecoDeCompra());
        produto.setPrecoDeVenda(produtoDto.getPrecoDeVenda());
        produto.setLucroLiquido(produtoDto.getPrecoDeVenda().subtract(produtoDto.getPrecoDeCompra()));
        produto.setDataDeCriacao(DataUtilitario.getDataAtualComoString());

        TipoDoProduto tipoDoProdutoOptional = tipoDoProdutoService.pegarUmTipoDoProdutoPeloId(produtoDto.getIdTipoDoProduto());

        produto.setTipoDoProduto(tipoDoProdutoOptional);

        return produto;
    }

    public boolean verificarAtributosProdutoNaoNulo(Produto produto) {
        if (produto.getQuantidade() != null && produto.getPrecoDeVenda() != null && produto.getLucroLiquido() != null &&
                produto.getPrecoDeCompra() != null && produto.getCodigo() != null && produto.getDescricao() != null) {
            return true;
        } else return false;
    }


}
