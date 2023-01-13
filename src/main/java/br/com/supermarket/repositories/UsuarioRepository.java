package br.com.supermarket.repositories;

import br.com.supermarket.models.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query("SELECT p FROM Usuario p WHERE CONCAT(p.nome, '') LIKE %?1%")
    public List<Usuario> pesquisaPorNome(String nome);


}
