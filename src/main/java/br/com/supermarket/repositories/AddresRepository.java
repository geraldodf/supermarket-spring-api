package br.com.supermarket.repositories;

import br.com.supermarket.models.Address;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddresRepository extends PagingAndSortingRepository<Address, Long> {
}
