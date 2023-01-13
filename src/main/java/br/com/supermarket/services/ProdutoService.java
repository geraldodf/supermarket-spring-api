package br.com.supermarket.services;

import br.com.supermarket.dtos.ProductDto;
import br.com.supermarket.exceptions.*;
import br.com.supermarket.models.TipoProduto;
import br.com.supermarket.util.DataUtilitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.supermarket.models.Produto;
import br.com.supermarket.repositories.ProdutoRepository;
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


    public Produto criarProduto(ProductDto productDto) throws Exception {
        verificarProdutoDto(productDto);
        Produto produto = criandoProdutoComDto(productDto);
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

    public void atualizarProduto(Long id, ProductDto productDto) throws Exception {
        if (id == null) {
            throw new Exception("Produto inexistente!");
        }
        Optional<Produto> resposta = produtoRepository.findById(id);
        Produto produtoAAtualizar = resposta.get();
        Produto produto = criandoProdutoComDto(productDto);
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

    public void verificarProdutoDto(ProductDto productDto) throws Exception {
        if (productDto.getCodigo() == null) {
            throw new ProductNullBarcodeException("O código está nulo.");
        }
        if (productDto.getCodigo() < 0) {
            throw new ProductInvalidBarcodeException("O código fornecido é inválido.");
        }
        if (productDto.getDescricao() == null) {
            throw new ProductDescriptionNullException(

                    "A descrição do produto está nula! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (productDto.getDescricao().length() <= 5) {
            throw new ProductDescriptionInvalidException(

                    "Descrição inválida! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (productDto.getPrecoDeVenda() == null) {
            throw new ProductSalePriceNullException("O produto deve ter um preço de venda.");
        }
        if (productDto.getPrecoDeCompra() == null) {
            throw new ProductPurchasePriceNullException("O produto deve ter um preço de compra.");
        }
        if (productDto.getQuantidade() == null) {
            throw new ProductQuantityNullException("Quantidade deve ser listada.");
        }
        if (productDto.getIdTipoDoProduto() == null) {
            throw new TypeProductNullException("Tipo do produto inválido");
        }
    }

    public Produto criandoProdutoComDto(ProductDto productDto) throws Exception {
        Produto produto = new Produto();
        produto.setCodigoBarras(productDto.getCodigo());
        produto.setDescricao(productDto.getDescricao());
        produto.setQuantidade(productDto.getQuantidade());
        produto.setPrecoCompra(productDto.getPrecoDeCompra());
        produto.setPrecoVenda(productDto.getPrecoDeVenda());
        produto.setLucroLiquido(productDto.getPrecoDeVenda().subtract(productDto.getPrecoDeCompra()));
        produto.setDataCriacao(DataUtilitario.getDataAtualComoString());

        TipoProduto tipoProdutoOptional = tipoProdutoService
                .pegarUmTipoDoProdutoPeloId(productDto.getIdTipoDoProduto());

        produto.setTipoProduto(tipoProdutoOptional);

        return produto;
    }

   
}
