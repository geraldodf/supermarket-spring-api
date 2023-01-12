package br.com.supermercado.services;

import br.com.supermercado.dtos.ProdutoDto;
import br.com.supermercado.exceptions.*;
import br.com.supermercado.models.TipoProduto;
import br.com.supermercado.util.DataUtilitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private TipoProdutoService tipoProdutoService;

    public ArrayList<Produto> pegarTodosProdutos() {
        return (ArrayList<Produto>) produtoRepository.findAll();
    }

    public Produto pegarUmProduto(Long id) throws Exception {

        Optional<Produto> produtoBuscadoPeloID = produtoRepository.findById(id);
        return produtoBuscadoPeloID.get();
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

    public Page<Produto> pesquisaPaginada(Pageable pageable) throws Exception {
        Page<Produto> page;
        try {
            page = produtoRepository.findAll(pageable);
        } catch (Exception e) {
            throw new Exception("Erro ao fazer consulta no banco de dados.");
        }
        return page;
    }

    public Page<Produto> pesquisaPorDescricaoPaginada(String descricao, Pageable pageable) {
        return produtoRepository.pesquisaPorDescricaoPaginada(descricao, pageable);
    }

    // public ArrayList<Produto> pesquisaPorTipoPaginada(String nomeTipo, Pageable pageable) {
    //     ArrayList<TipoProduto> listaRetornoDeTipos = tipoDoProdutoService.pegarTipoDoProdutoPorNome(nomeTipo);
    //     TipoProduto tipoDoProdutoParaBusca = listaRetornoDeTipos.get(0);
    //     return produtoRepository.pesquisaPorTipoPaginada(tipoDoProdutoParaBusca.getId(), pageable);
    // }
        //fixme


    public Produto criarProduto(ProdutoDto produtoDto) throws Exception {
        verificarProdutoDto(produtoDto);
        Produto produto = criandoProdutoComDto(produtoDto);
        produto.verificarProduto();
        try {
            if (produto.verificarAtributosProdutoNaoNulo()) {
                produto.setLucroLiquido(produto.getPrecoVenda().subtract(produto.getPrecoCompra()));
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
        produto.verificarProduto();

        try {
            if (produto.verificarAtributosProdutoNaoNulo()) {

                produtoAAtualizar.setCodigoBarras(produto.getCodigoBarras());
                produtoAAtualizar.setDescricao(produto.getDescricao());
                produtoAAtualizar.setPrecoVenda(produto.getPrecoVenda());
                produtoAAtualizar.setPrecoCompra(produto.getPrecoCompra());
                produtoAAtualizar.setQuantidade(produto.getQuantidade());
                produtoAAtualizar.setLucroLiquido(produto.getPrecoVenda().subtract(produto.getPrecoCompra()));
                produtoRepository.save(produtoAAtualizar);

            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception("Produto está com algum atributo inválido! Tente novamente.");
        }
    }

    public void verificarProdutoDto(ProdutoDto produtoDto) throws Exception {
        if (produtoDto.getCodigo() == null) {
            throw new ProdutoCodigoNuloException("O código está nulo.");
        }
        if (produtoDto.getCodigo() < 0) {
            throw new ProdutoCodigoInvalidoException("O código fornecido é inválido.");
        }
        if (produtoDto.getDescricao() == null) {
            throw new ProdutoDescricaoNulaException(

                    "A descrição do produto está nula! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (produtoDto.getDescricao().length() <= 5) {
            throw new ProdutoDescricaInvalidaException(

                    "Descrição inválida! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (produtoDto.getPrecoDeVenda() == null) {
            throw new ProdutoPrecoDeVendaNuloException("O produto deve ter um preço de venda.");
        }
        if (produtoDto.getPrecoDeCompra() == null) {
            throw new ProdutoPrecoDeCompraNuloException("O produto deve ter um preço de compra.");
        }
        if (produtoDto.getQuantidade() == null) {
            throw new ProdutoQuantidadeNulaException("Quantidade deve ser listada.");
        }
        if (produtoDto.getIdTipoDoProduto() == null) {
            throw new ProdutoTipoDoProdutoNuloException("Tipo do produto inválido");
        }
    }

    public Produto criandoProdutoComDto(ProdutoDto produtoDto) throws Exception {
        Produto produto = new Produto();
        produto.setCodigoBarras(produtoDto.getCodigo());
        produto.setDescricao(produtoDto.getDescricao());
        produto.setQuantidade(produtoDto.getQuantidade());
        produto.setPrecoCompra(produtoDto.getPrecoDeCompra());
        produto.setPrecoVenda(produtoDto.getPrecoDeVenda());
        produto.setLucroLiquido(produtoDto.getPrecoDeVenda().subtract(produtoDto.getPrecoDeCompra()));
        produto.setDataCriacao(DataUtilitario.getDataAtualComoString());

        TipoProduto tipoProdutoOptional = tipoProdutoService
                .pegarUmTipoDoProdutoPeloId(produtoDto.getIdTipoDoProduto());

        produto.setTipoProduto(tipoProdutoOptional);

        return produto;
    }

   
}
