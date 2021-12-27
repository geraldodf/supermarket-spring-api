package br.com.supermercado.services;

import br.com.supermercado.models.Usuario;
import br.com.supermercado.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void criarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public ArrayList<Usuario> pegarTodosUsuarios() {
        return (ArrayList<Usuario>) usuarioRepository.findAll();
    }

    public Usuario pegarUmUsuario(Long id) {
        Optional<Usuario> usuarioPeloId = usuarioRepository.findById(id);
        return usuarioPeloId.get();
    }
}
