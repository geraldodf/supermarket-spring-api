package br.com.supermercado.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.supermercado.models.Produto;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Long> {
}
