package br.com.supermarket.resources;

import br.com.supermarket.models.User;
import br.com.supermarket.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UsuarioService userService;

    @GetMapping
    public ArrayList<User> getAllUsers() throws Exception {
        return userService.pegarTodosUsuarios();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) throws Exception {
        return userService.pegarUmUsuario(id);
    }

    @GetMapping("/search")
    public ArrayList<User> getUserByName(@RequestParam(name = "name") String name) throws Exception {
        return userService.pegarUsuarioPorNome(name);
    }

    @PostMapping
    public void createUser(@RequestBody User user) throws Exception {
        userService.criarUsuario(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable("id") Long id, @RequestBody User user) throws Exception {
        userService.atualizarUsuario(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) throws Exception {
        userService.excuirUsuario(id);
    }

}
