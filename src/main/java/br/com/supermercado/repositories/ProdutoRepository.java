package br.com.supermercado.repositories;

import br.com.supermercado.models.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface ProdutoRepository extends PagingAndSortingRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE CONCAT(p.descricao, '') LIKE %?1%")
    public ArrayList<Produto> pesquisaPorDescricao(String descricao);

    @Query("SELECT p FROM Produto p WHERE CONCAT(p.codigo, '') LIKE %?1%")
    public ArrayList<Produto> pesquisaPorCodigo(Long codigo);

    List<Produto> findAllByDescricao(String descricao);

    @Query("SELECT p FROM Produto p WHERE CONCAT(p.descricao, '') LIKE %?1%")
    Page<Produto> pesquisaPorDescricaoPaginada(String descricao, Pageable pageable);

    // @Query("SELECT * FROM Produto p WHERE p.getTipoDoProduto().getId() = :id")
    // ArrayList<Produto> pesquisaPorTipoPaginada(Long id, Pageable pageable);

    

}
