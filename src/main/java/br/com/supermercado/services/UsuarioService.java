package br.com.supermercado.services;

import br.com.supermercado.models.Usuario;
import br.com.supermercado.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void criarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
