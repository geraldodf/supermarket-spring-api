package br.com.supermercado.services;

import br.com.supermercado.models.TipoProduto;
import br.com.supermercado.repositories.TipoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TipoProdutoService {

    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;

    public ArrayList<TipoProduto> pegarTodosTiposDosProdutos() {
        return (ArrayList<TipoProduto>) tipoProdutoRepository.findAll();
    }

    public TipoProduto criarTipoDoProduto(TipoProduto tipo) throws Exception {
        if (tipo.getNomeTipoProduto() == null || tipo.getNomeTipoProduto().length() < 5)
            throw new Exception("Tipo não pode ser criado sem um nome, ou com um nome com menos de 5 caracteres.");
        return tipoProdutoRepository.save(tipo);
    }

    public TipoProduto pegarUmTipoDoProdutoPeloId(Long id) {
        Optional<TipoProduto> retorno = tipoProdutoRepository.findById(id);
        return retorno.get();
    }

    public TipoProduto atualizarTipoDoProduto(Long id, TipoProduto tipo) throws Exception {
        if (id == null) throw new Exception("Não é possivel atualizar um tipo sem especificar quem irá atualizar");
        if (tipo.getNomeTipoProduto() == null) throw new Exception("Tipo não pode ser criado sem um nome.");


        Optional<TipoProduto> retornoOptional = null;
        try {
            retornoOptional = tipoProdutoRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Erro ao buscar tipo para ser atualizado!!");
        }

        TipoProduto retorno = null;
        try {
            retorno = retornoOptional.get();
        } catch (Exception e) {
            throw new Exception("Erro ao tratar tipo que irá ser atualizado ");
        }

        try {
            retorno.setNomeTipoProduto(tipo.getNomeTipoProduto());
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar o tipo!");
        }

        return tipoProdutoRepository.save(retorno);
    }

    public void excluirTipoDoProdutoPeloId(Long id) throws Exception {
        if (id == null)
            throw new Exception("Para excluir um tipo deve ser especificado qual tipo será excluida, essa resposta não está chegando!");
        tipoProdutoRepository.deleteById(id);
    }

    public ArrayList<TipoProduto> pegarTipoDoProdutoPorNome(String nome) {
        return tipoProdutoRepository.pesquisaPorNome(nome);
    }

    public ArrayList<TipoProduto> pegarTiposDoProdutoComSort(Sort sort) {
        return (ArrayList<TipoProduto>) tipoProdutoRepository.findAll(sort);
    }
}