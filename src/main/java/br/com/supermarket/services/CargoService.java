package br.com.supermarket.services;

import br.com.supermarket.models.Cargo;
import br.com.supermarket.repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public ArrayList<Cargo> pegarTodosCargos() {
        return (ArrayList<Cargo>) cargoRepository.findAll();
    }

    public Cargo pegarCargoPeloId(Long id) throws Exception {

        if (id == null)
            throw new Exception("O id do cargo não pode ser nulo! Id não foi recebido.");

        Optional<Cargo> cargoOptional = null;
        try {
            cargoOptional = cargoRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Erro ao buscar cargo por ID");
        }
        return cargoOptional.get();
    }

    public ArrayList<Cargo> pegarCargoPeloNome(String nome) throws Exception {
        if (nome == null || nome == "") {
            throw new Exception("Deve ser especificado o nome ou alguma inicial!");
        }
        try {
            cargoRepository.pesquisaPorNome(nome);
        } catch (Exception e) {
            throw new Exception("Erro ao pesquisar cargo por nome!");
        }
        return cargoRepository.pesquisaPorNome(nome);
    }

    public Cargo criarCargo(Cargo cargo) throws Exception {
        if (cargo.getNome() == null || cargo.getNome().length() <= 3)
            throw new Exception("O nome do cargo deve ser especificado e ter no mínimo 3 caracteres");
        try {
            cargoRepository.save(cargo);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar cargo no banco de dados");
        }
        return cargo;
    }

    public Cargo atualizarCargo(Long id, Cargo cargo) throws Exception {
        Optional<Cargo> cargoOptional = null;
        try {
            cargoOptional = cargoRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Erro ao pesquisar produto para ser alterado!");
        }

        Cargo cargoParaAtualizar = cargoOptional.get();

        if (cargo.getNome() == null || cargo.getNome().length() <= 3)
            throw new Exception("O nome do cargo deve ser especificado e ter no mínimo 3 caracteres");

        cargoParaAtualizar.setNome(cargo.getNome());

        try {
            cargoRepository.save(cargoParaAtualizar);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o novo cargo no banco de dados!");
        }

        return cargoParaAtualizar;
    }

    public void excluirCargo(Long id) throws Exception {

        try {
            cargoRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Produto não foi excluido! Verifique a credencial e tente novamente!");
        }
    }
}
