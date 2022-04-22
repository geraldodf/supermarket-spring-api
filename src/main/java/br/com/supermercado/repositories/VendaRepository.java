package br.com.supermercado.repositories;

import br.com.supermercado.models.Venda;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends CrudRepository<Venda, Long> {
}
