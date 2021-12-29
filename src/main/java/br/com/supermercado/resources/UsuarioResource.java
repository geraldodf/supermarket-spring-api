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
    public ArrayList<Usuario> pegarTodosUsuarios(){
        return usuarioService.pegarTodosUsuarios();
    }
    
    @GetMapping("/{id}")
    public Usuario pegarUmUsuario(@PathVariable ("id") Long id){
        return usuarioService.pegarUmUsuario(id);
    }

    @GetMapping("/search")
    public ArrayList<Usuario> pegarUsuarioPorNome(@RequestParam (name = "search") String nome){
        return usuarioService.pegarUsuarioPorNome(nome);
    }

    @PostMapping
    public void criarUsuario(@RequestBody Usuario usuario){
        usuarioService.criarUsuario(usuario);
    }

    @PutMapping("/{id}")
    public void atualizarUsuario(@PathVariable ("id") Long id,@RequestBody Usuario usuario){
        usuarioService.atualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void excuirUsuario(@PathVariable ("id") Long id){
        usuarioService.excuirUsuario(id);
    }



}
