package br.com.supermarket.resources;

import br.com.supermarket.models.Role;
import br.com.supermarket.services.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RequestMapping("/roules")
@RestController
public class RouleResource {

    @Autowired
    private CargoService rouleService;

    @GetMapping
    public ArrayList<Role> getAllRoules() {
        return rouleService.pegarTodosCargos();
    }

    @GetMapping("/{id}")
    public Role getRouleById(@PathVariable("id") Long id) throws Exception {
        return rouleService.pegarCargoPeloId(id);
    }

    @GetMapping("/name")
    public ArrayList<Role> getRouleByName(@RequestParam(name = "name") String name) throws Exception {
        return rouleService.pegarCargoPeloNome(name);
    }

    @PostMapping
    public Role createRoule(@RequestBody Role role) throws Exception {
        return rouleService.criarCargo(role);
    }

    @PutMapping("/{id}")
    public Role updateRoule(@PathVariable("id") Long id, @RequestBody Role role) throws Exception {
        return rouleService.atualizarCargo(id, role);
    }

    @DeleteMapping("/{id}")
    public void deleteRoleById(@PathVariable("id") Long id) throws Exception {
        rouleService.excluirCargo(id);
    }

}
