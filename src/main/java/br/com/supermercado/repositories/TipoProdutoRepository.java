package br.com.supermercado.repositories;

import br.com.supermercado.models.TipoProduto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TipoProdutoRepository extends PagingAndSortingRepository<TipoProduto, Long> {

    @Query("SELECT t FROM TipoProduto t WHERE CONCAT(t.nomeTipoProduto, '') LIKE %?1%")
    public ArrayList<TipoProduto> pesquisaPorNome(String nome);

}
