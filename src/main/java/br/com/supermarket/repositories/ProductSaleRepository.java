package br.com.supermarket.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import br.com.supermarket.models.ProductSale;

@Repository
public interface ProductSaleRepository extends CrudRepository<ProductSale, Long> {

}
