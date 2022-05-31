package br.com.supermercado.repositories;

import br.com.supermercado.models.TipoDoProduto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDoProdutoRepository extends CrudRepository<TipoDoProduto, Long> {



}
