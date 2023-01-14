package br.com.supermarket.services;

import br.com.supermarket.models.Role;
import br.com.supermarket.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    private RoleRepository roleRepository;

    public ArrayList<Role> pegarTodosCargos() {
        return (ArrayList<Role>) roleRepository.findAll();
    }

    public Role pegarCargoPeloId(Long id) throws Exception {

        if (id == null)
            throw new Exception("O id do cargo não pode ser nulo! Id não foi recebido.");

        Optional<Role> cargoOptional = null;
        try {
            cargoOptional = roleRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Erro ao buscar cargo por ID");
        }
        return cargoOptional.get();
    }

    public ArrayList<Role> pegarCargoPeloNome(String nome) throws Exception {
        if (nome == null || nome == "") {
            throw new Exception("Deve ser especificado o nome ou alguma inicial!");
        }
        try {
            roleRepository.searchByName(nome);
        } catch (Exception e) {
            throw new Exception("Erro ao pesquisar cargo por nome!");
        }
        return roleRepository.searchByName(nome);
    }

    public Role criarCargo(Role role) throws Exception {
        if (role.getName() == null || role.getName().length() <= 3)
            throw new Exception("O nome do role deve ser especificado e ter no mínimo 3 caracteres");
        try {
            roleRepository.save(role);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar role no banco de dados");
        }
        return role;
    }

    public Role atualizarCargo(Long id, Role role) throws Exception {
        Optional<Role> cargoOptional = null;
        try {
            cargoOptional = roleRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Erro ao pesquisar produto para ser alterado!");
        }

        Role roleParaAtualizar = cargoOptional.get();

        if (role.getName() == null || role.getName().length() <= 3)
            throw new Exception("O nome do role deve ser especificado e ter no mínimo 3 caracteres");

        roleParaAtualizar.setName(role.getName());

        try {
            roleRepository.save(roleParaAtualizar);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o novo role no banco de dados!");
        }

        return roleParaAtualizar;
    }

    public void excluirCargo(Long id) throws Exception {

        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Product não foi excluido! Verifique a credencial e tente novamente!");
        }
    }
}
