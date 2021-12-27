package br.com.supermercado.services;

import br.com.supermercado.models.Usuario;
import br.com.supermercado.repositories.UsuarioRepository;
import br.com.supermercado.resources.UsuarioResource;
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

    public void atualizarUsuario(Long id, Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        Usuario usuarioParaAtualizar = usuarioOptional.get();
        usuario.setCargo(usuarioParaAtualizar.getCargo());
        usuario.setNome(usuarioParaAtualizar.getNome());
        usuario.setSenha(usuarioParaAtualizar.getSenha());
        usuarioRepository.save(usuario);
    }
}
