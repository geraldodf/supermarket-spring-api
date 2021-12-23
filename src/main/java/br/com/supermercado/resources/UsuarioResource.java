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

    @PostMapping
    public void criarUsuario(@RequestBody Usuario usuario){
        usuarioService.criarUsuario(usuario);
    }

    @GetMapping
    public ArrayList<Usuario> pegarTodosUsuarios(){
        return usuarioService.pegarTodosUsuarios();
    }


}
