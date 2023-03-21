package br.com.supermarket.repositories;

import br.com.supermarket.models.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddresRepository extends CrudRepository<Address, Long> {
}
