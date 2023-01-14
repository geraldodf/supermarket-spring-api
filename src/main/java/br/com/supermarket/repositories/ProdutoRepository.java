package br.com.supermarket.repositories;

import br.com.supermarket.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProdutoRepository extends PagingAndSortingRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE CONCAT(p.description, '') LIKE %?1%")
    public ArrayList<Product> pesquisaPorDescricao(String descricao);

    @Query("SELECT p FROM Product p WHERE CONCAT(p.barCode, '') LIKE %?1%")
    public ArrayList<Product> pesquisaPorCodigo(Long codigo);

    List<Product> findAllByDescricao(String descricao);

    @Query("SELECT p FROM Product p WHERE CONCAT(p.description, '') LIKE %?1%")
    Page<Product> pesquisaPorDescricaoPaginada(String descricao, Pageable pageable);

    // @Query("SELECT * FROM Product p WHERE p.tipoDoProduto.id = :id")
    // ArrayList<Product> pesquisaPorTipoPaginada(Long id, Pageable pageable);

    

}
