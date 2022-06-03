package br.com.supermercado.repositories;

import br.com.supermercado.models.TipoDoProduto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TipoDoProdutoRepository extends CrudRepository<TipoDoProduto, Long> {

    @Query("SELECT t FROM TipoDoProduto t WHERE CONCAT(t.nomeTipoDoProduto, '') LIKE %?1%")
    public ArrayList<TipoDoProduto> pesquisaPorNome(String nome);

}
