package br.com.supermercado.services;

import br.com.supermercado.models.Cargo;
import br.com.supermercado.repositories.CargoRepository;
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

    public Cargo pegarCargoPeloId(Long id) {
        Optional<Cargo> cargoOptional =  cargoRepository.findById(id);
        return cargoOptional.get();
    }

    public Cargo criarCargo(Cargo cargo) {
        return cargoRepository.save(cargo);
    }


    public Cargo atualizarCargo(Long id, Cargo cargo) {
        Optional<Cargo> cargoOptional = cargoRepository.findById(id);
        Cargo cargoParaAtualizar = cargoOptional.get();
        cargoParaAtualizar.setNome(cargo.getNome());
        return cargoRepository.save(cargoParaAtualizar);
    }


    public void excluirCargo(Long id) {
        cargoRepository.deleteById(id);
    }

    public ArrayList<Cargo> pegarCargoPeloNome(String nome) {
        return cargoRepository.pesquisaPorNome(nome);
    }
}
