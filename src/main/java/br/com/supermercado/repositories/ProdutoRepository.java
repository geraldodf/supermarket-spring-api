package br.com.supermercado.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.supermercado.models.Produto;
import java.util.ArrayList;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {

    @Query("SELECT p FROM Produto p WHERE CONCAT(p.descricao, '') LIKE %?1%")
    public ArrayList<Produto> pesquisaPorDescricao(String descricao);

    @Query("SELECT p FROM Produto p WHERE CONCAT(p.codigo, '') LIKE %?1%")
    public ArrayList<Produto> pesquisaPorCodigo(Long codigo);

}
