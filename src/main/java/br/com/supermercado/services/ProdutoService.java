package br.com.supermercado.services;

import br.com.supermercado.dtos.DoacaoDto;
import br.com.supermercado.dtos.ProdutoDto;
import br.com.supermercado.exceptions.*;
import br.com.supermercado.models.TipoDoProduto;
import br.com.supermercado.util.DataUtilitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antlr.collections.List;
import br.com.supermercado.models.Produto;
import br.com.supermercado.repositories.ProdutoRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Optional<Produto> produtoBuscadoPeloID = produtoRepository.findById(id);
        return produtoBuscadoPeloID.get();

    }

    public Produto criarProduto(ProdutoDto produtoDto) throws Exception {
        verificarProdutoDto(produtoDto);
        Produto produto = criandoProdutoComDto(produtoDto);
        verificarProduto(produto);
        try {
            if (verificarAtributosProdutoNaoNulo(produto)) {
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
            throw new ProdutoDescricaoNulaException(
                    "A descrição do produto está nula! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (produto.getDescricao().length() <= 5) {
            throw new ProdutoDescricaInvalidaException(
                    "Descrição inválida! A descrição do produto deve ter no mínimo 5 caracteres.");
        }
        if (produto.getPrecoDeVenda() == null) {
            // Está gerando NullPointer e não chegando aqui!
            throw new ProdutoPrecoDeVendaNuloException("O produto deve ter um preço de venda.");
        }
        if (produto.getPrecoDeCompra() == null) {
            // Está gerando NullPointer e não chegando aqui!
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
            // Esta dando erro ao buscar tipo.
            throw new ProdutoTipoDoProdutoNuloException("Tipo do produto inválido");
        }
        if (produto.getPrecoDeVenda().subtract(produto.getPrecoDeCompra()).doubleValue() != produto.getLucroLiquido()
                .doubleValue()) {
            throw new ProdutoLucroInconsistenteException("Valor do lucro está inconsistente.");
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
        produto.setCodigo(produtoDto.getCodigo());
        produto.setDescricao(produtoDto.getDescricao());
        produto.setQuantidade(produtoDto.getQuantidade());
        produto.setPrecoDeCompra(produtoDto.getPrecoDeCompra());
        produto.setPrecoDeVenda(produtoDto.getPrecoDeVenda());
        produto.setLucroLiquido(produtoDto.getPrecoDeVenda().subtract(produtoDto.getPrecoDeCompra()));
        produto.setDataDeCriacao(DataUtilitario.getDataAtualComoString());

        TipoDoProduto tipoDoProdutoOptional = tipoDoProdutoService
                .pegarUmTipoDoProdutoPeloId(produtoDto.getIdTipoDoProduto());

        produto.setTipoDoProduto(tipoDoProdutoOptional);

        return produto;
    }

    public boolean verificarAtributosProdutoNaoNulo(Produto produto) {
        if (produto.getQuantidade() != null && produto.getPrecoDeVenda() != null && produto.getLucroLiquido() != null &&
                produto.getPrecoDeCompra() != null && produto.getCodigo() != null && produto.getDescricao() != null) {
            return true;
        } else
            return false;
    }

    public DoacaoDto pegarProdutosParaDoacao() {

        ArrayList<Produto> todosProdutos = (ArrayList<Produto>) produtoRepository.findAll();
        DoacaoDto doacao = new DoacaoDto();

        ArrayList<Produto> listaRetornoAteF = new ArrayList<>();
        ArrayList<Produto> listaRetornoAteO = new ArrayList<>();
        ArrayList<Produto> listaRetornoAteZ = new ArrayList<>();
        ArrayList<Produto> listaFiltrada = new ArrayList<>();

        char letraFiltrada = 'A';

        while (letraFiltrada != 'G') {
            listaFiltrada = filtrarProdutosPorLetraInicial(letraFiltrada, todosProdutos);
            listaFiltrada.forEach(p -> listaRetornoAteF.add(p));

            letraFiltrada++;
        }

        doacao.setListaDeProdutosAteF(listaRetornoAteF);

        while (letraFiltrada != 'P') {
            listaFiltrada = filtrarProdutosPorLetraInicial(letraFiltrada, todosProdutos);
            listaFiltrada.forEach(p -> listaRetornoAteO.add(p));

            letraFiltrada++;
        }

        doacao.setListaDeProdutosAteO(listaRetornoAteO);

        while (letraFiltrada != 'Z') {
            listaFiltrada = filtrarProdutosPorLetraInicial(letraFiltrada, todosProdutos);
            listaFiltrada.forEach(p -> listaRetornoAteZ.add(p));

            letraFiltrada++;
        }

        doacao.setListaDeProdutosAteZ(listaRetornoAteZ);

        return doacao;
    }

    public ArrayList<Produto> filtrarProdutosPorLetraInicial(char letraInicial, ArrayList<Produto> listaParaFiltrar) {

        ArrayList<Produto> listaParaRetornar = (ArrayList<Produto>) listaParaFiltrar.stream()
                .filter(p -> p.getDescricao().indexOf(letraInicial) == 0).collect(Collectors.toList());

        return listaParaRetornar;
    }

}
