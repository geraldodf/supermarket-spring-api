package br.com.supermarket.repositories;

import br.com.supermarket.models.Sale;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends CrudRepository<Sale, Long> {
}
