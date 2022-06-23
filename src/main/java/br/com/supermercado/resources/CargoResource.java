package br.com.supermercado.resources;

import br.com.supermercado.models.Cargo;
import br.com.supermercado.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/cargos")
@RestController
public class CargoResource {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    public ArrayList<Cargo> pegarTodosCargos() {
        return cargoService.pegarTodosCargos();
    }

    @GetMapping("/{id}")
    public Cargo pegarCargoPeloId(@PathVariable("id") Long id) throws Exception {
        return cargoService.pegarCargoPeloId(id);
    }

    @GetMapping("/pesquisa-por-nome")
    public ArrayList<Cargo> pegarCargoPeloNome(@RequestParam(name = "nome") String nome) throws Exception {
        return cargoService.pegarCargoPeloNome(nome);
    }

    @PostMapping
    public Cargo criarCargo(@RequestBody Cargo cargo) throws Exception {
        return cargoService.criarCargo(cargo);
    }

    @PutMapping("/{id}")
    public Cargo atualizarCargo(@PathVariable("id") Long id, @RequestBody Cargo cargo) throws Exception {
        return cargoService.atualizarCargo(id, cargo);
    }

    @DeleteMapping("/{id}")
    public void excluirCargo(@PathVariable("id") Long id) throws Exception {
        cargoService.excluirCargo(id);
    }

}
