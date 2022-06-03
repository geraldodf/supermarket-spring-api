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

    public TipoDoProduto criarTipoDoProduto(TipoDoProduto tipo) throws Exception {
        if (tipo.getNomeTipoDoProduto() == null) throw new Exception("Tipo não pode ser criado sem um nome.");
        return tipoDoProdutoRepository.save(tipo);
    }

    public TipoDoProduto pegarUmTipoDoProdutoPeloId(Long id) {
        Optional<TipoDoProduto> retorno = tipoDoProdutoRepository.findById(id);
        return retorno.get();
    }

    public TipoDoProduto atualizarTipoDoProduto(Long id, TipoDoProduto tipo) throws Exception {
        if(id == null) throw new Exception("Não é possivel atualizar um tipo sem especificar quem irá atualizar");
        if (tipo.getNomeTipoDoProduto() == null) throw new Exception("Tipo não pode ser criado sem um nome.");


        Optional<TipoDoProduto> retornoOptional = null;
        try {
            retornoOptional = tipoDoProdutoRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Erro ao buscar tipo para ser atualizado!!");
        }

        TipoDoProduto retorno = null;
        try {
            retorno = retornoOptional.get();
        } catch (Exception e) {
            throw new Exception("Erro ao tratar tipo que irá ser atualizado ");
        }

        try {
            retorno.setNomeTipoDoProduto(tipo.getNomeTipoDoProduto());
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar o tipo!");
        }

        return tipoDoProdutoRepository.save(retorno);
    }

    public void excluirTipoDoProdutoPeloId(Long id) {
        tipoDoProdutoRepository.deleteById(id);
    }
}