package br.com.supermarket.resources;

import br.com.supermarket.models.User;
import br.com.supermarket.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ArrayList<User> pegarTodosUsuarios() throws Exception {
        return usuarioService.pegarTodosUsuarios();
    }

    @GetMapping("/{id}")
    public User pegarUmUsuario(@PathVariable("id") Long id) throws Exception {
        return usuarioService.pegarUmUsuario(id);
    }

    @GetMapping("/search")
    public ArrayList<User> pegarUsuarioPorNome(@RequestParam(name = "search") String nome) throws Exception {
        return usuarioService.pegarUsuarioPorNome(nome);
    }

    @PostMapping
    public void criarUsuario(@RequestBody User user) throws Exception {
        usuarioService.criarUsuario(user);
    }

    @PutMapping("/{id}")
    public void atualizarUsuario(@PathVariable("id") Long id, @RequestBody User user) throws Exception {
        usuarioService.atualizarUsuario(id, user);
    }

    @DeleteMapping("/{id}")
    public void excuirUsuario(@PathVariable("id") Long id) throws Exception {
        usuarioService.excuirUsuario(id);
    }

}
