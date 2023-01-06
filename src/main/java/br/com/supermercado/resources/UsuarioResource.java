package br.com.supermercado.resources;

import br.com.supermercado.models.Usuario;
import br.com.supermercado.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ArrayList<Usuario> pegarTodosUsuarios() throws Exception {
        return usuarioService.pegarTodosUsuarios();
    }

    @GetMapping("/{id}")
    public Usuario pegarUmUsuario(@PathVariable("id") Long id) throws Exception {
        return usuarioService.pegarUmUsuario(id);
    }

    @GetMapping("/search")
    public ArrayList<Usuario> pegarUsuarioPorNome(@RequestParam(name = "search") String nome) throws Exception {
        return usuarioService.pegarUsuarioPorNome(nome);
    }

    @PostMapping
    public void criarUsuario(@RequestBody Usuario usuario) throws Exception {
        usuarioService.criarUsuario(usuario);
    }

    @PutMapping("/{id}")
    public void atualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) throws Exception {
        usuarioService.atualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void excuirUsuario(@PathVariable("id") Long id) throws Exception {
        usuarioService.excuirUsuario(id);
    }

}
