package br.com.supermarket.repositories;

import br.com.supermarket.models.ProductType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TipoProdutoRepository extends PagingAndSortingRepository<ProductType, Long> {

    @Query("SELECT t FROM ProductType t WHERE CONCAT(t.nameProductType, '') LIKE %?1%")
    public ArrayList<ProductType> pesquisaPorNome(String nome);

}
