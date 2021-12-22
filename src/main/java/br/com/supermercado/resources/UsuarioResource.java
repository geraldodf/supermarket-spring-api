package br.com.supermercado.resources;

import br.com.supermercado.models.Usuario;
import br.com.supermercado.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public void criarUsuario(@RequestBody Usuario usuario){
        usuarioService.criarUsuario(usuario);
    }
}
