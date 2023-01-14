package br.com.supermarket.resources;

import br.com.supermarket.models.Role;
import br.com.supermarket.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("/cargos")
@RestController
public class CargoResource {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    public ArrayList<Role> pegarTodosCargos() {
        return cargoService.pegarTodosCargos();
    }

    @GetMapping("/{id}")
    public Role pegarCargoPeloId(@PathVariable("id") Long id) throws Exception {
        return cargoService.pegarCargoPeloId(id);
    }

    @GetMapping("/pesquisa-por-nome")
    public ArrayList<Role> pegarCargoPeloNome(@RequestParam(name = "nome") String nome) throws Exception {
        return cargoService.pegarCargoPeloNome(nome);
    }

    @PostMapping
    public Role criarCargo(@RequestBody Role role) throws Exception {
        return cargoService.criarCargo(role);
    }

    @PutMapping("/{id}")
    public Role atualizarCargo(@PathVariable("id") Long id, @RequestBody Role role) throws Exception {
        return cargoService.atualizarCargo(id, role);
    }

    @DeleteMapping("/{id}")
    public void excluirCargo(@PathVariable("id") Long id) throws Exception {
        cargoService.excluirCargo(id);
    }

}
