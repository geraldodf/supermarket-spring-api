package br.com.supermarket.services;

import br.com.supermarket.models.ProductType;
import br.com.supermarket.repositories.TipoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TipoProdutoService {

    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;

    public ArrayList<ProductType> pegarTodosTiposDosProdutos() {
        return (ArrayList<ProductType>) tipoProdutoRepository.findAll();
    }

    public ProductType criarTipoDoProduto(ProductType tipo) throws Exception {
        if (tipo.getNameProductType() == null || tipo.getNameProductType().length() < 5)
            throw new Exception("Tipo não pode ser criado sem um nome, ou com um nome com menos de 5 caracteres.");
        return tipoProdutoRepository.save(tipo);
    }

    public ProductType pegarUmTipoDoProdutoPeloId(Long id) {
        Optional<ProductType> retorno = tipoProdutoRepository.findById(id);
        return retorno.get();
    }

    public ProductType atualizarTipoDoProduto(Long id, ProductType tipo) throws Exception {
        if (id == null) throw new Exception("Não é possivel atualizar um tipo sem especificar quem irá atualizar");
        if (tipo.getNameProductType() == null) throw new Exception("Tipo não pode ser criado sem um nome.");


        Optional<ProductType> retornoOptional = null;
        try {
            retornoOptional = tipoProdutoRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Erro ao buscar tipo para ser atualizado!!");
        }

        ProductType retorno = null;
        try {
            retorno = retornoOptional.get();
        } catch (Exception e) {
            throw new Exception("Erro ao tratar tipo que irá ser atualizado ");
        }

        try {
            retorno.setNameProductType(tipo.getNameProductType());
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

    public ArrayList<ProductType> pegarTipoDoProdutoPorNome(String nome) {
        return tipoProdutoRepository.pesquisaPorNome(nome);
    }

    public ArrayList<ProductType> pegarTiposDoProdutoComSort(Sort sort) {
        return (ArrayList<ProductType>) tipoProdutoRepository.findAll(sort);
    }
}