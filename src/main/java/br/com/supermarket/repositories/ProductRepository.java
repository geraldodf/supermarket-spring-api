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
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE CONCAT(p.description, '') LIKE %?1%")
    public ArrayList<Product> searchByDescription(String description);

    @Query("SELECT p FROM Product p WHERE CONCAT(p.barCode, '') LIKE %?1%")
    public ArrayList<Product> searchByBarCode(Long barCode);

    List<Product> findAllByDescription(String description);

    @Query("SELECT p FROM Product p WHERE CONCAT(p.description, '') LIKE %?1%")
    Page<Product> searchByDescriptionPaged(String description, Pageable pageable);

    // @Query("SELECT * FROM Product p WHERE p.tipoDoProduto.id = :id")
    // ArrayList<Product> pesquisaPorTipoPaginada(Long id, Pageable pageable);

    

}
