package br.com.supermarket.repositories;

import br.com.supermarket.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    @Query("SELECT p FROM User p WHERE CONCAT(p.name, '') LIKE %?1%")
    List<User> searchByName(String name);


}
