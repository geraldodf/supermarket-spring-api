package br.com.supermarket.repositories;

import br.com.supermarket.models.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("SELECT c FROM Role c WHERE CONCAT(c.name, '') LIKE %?1%")
    ArrayList<Role> searchByName(String name);

}
