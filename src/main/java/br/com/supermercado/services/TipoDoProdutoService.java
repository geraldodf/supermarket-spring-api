package br.com.supermercado.services;

import br.com.supermercado.models.TipoDoProduto;
import br.com.supermercado.repositories.TipoDoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TipoDoProdutoService {

    @Autowired
    private TipoDoProdutoRepository tipoDoProdutoRepository;

    public ArrayList<TipoDoProduto> pegarTodosTiposDosProdutos() {
        return (ArrayList<TipoDoProduto>) tipoDoProdutoRepository.findAll();
    }

    public TipoDoProduto criarTipoDoProduto(TipoDoProduto tipo) {

        return tipoDoProdutoRepository.save(tipo);
    }

    public TipoDoProduto pegarUmTipoDoProdutoPeloId(Long id) {
        Optional<TipoDoProduto> retorno = tipoDoProdutoRepository.findById(id);
        return retorno.get();
    }

    public TipoDoProduto atualizarTipoDoProduto(Long id, TipoDoProduto tipo) {
        Optional<TipoDoProduto> retornoOptional = tipoDoProdutoRepository.findById(id);
        TipoDoProduto retorno = retornoOptional.get();
        retorno.setNomeTipoDoProduto(tipo.getNomeTipoDoProduto());
        return tipoDoProdutoRepository.save(retorno);
    }

    public void excluirTipoDoProdutoPeloId(Long id) {
        tipoDoProdutoRepository.deleteById(id);
    }
}
