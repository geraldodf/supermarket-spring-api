package br.com.supermercado.repositories;

import br.com.supermercado.models.Cargo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public interface CargoRepository extends CrudRepository<Cargo, Long> {

    @Query("SELECT c FROM Cargo c WHERE CONCAT(c.nome, '') LIKE %?1%")
    public ArrayList<Cargo> pesquisaPorNome(String nome);

}
