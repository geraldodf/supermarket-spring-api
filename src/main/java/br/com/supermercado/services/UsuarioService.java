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


    public ArrayList<Usuario> pegarTodosUsuarios() throws Exception {
        try {
            return (ArrayList<Usuario>) usuarioRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Erro! tente novamente.");
        }
    }


    public Usuario pegarUmUsuario(Long id) throws Exception {
        try {
            Optional<Usuario> usuarioPeloId = usuarioRepository.findById(id);
            return usuarioPeloId.get();
        } catch (Exception e) {
            throw new Exception("Usuario inválido! verifique e tente novamente.");
        }
    }

    public void criarUsuario(Usuario usuario) throws Exception {
        verificarUsuario(usuario);
        if (usuario.getSenha() >= 999 && usuario.getCargo().length() >= 3 && usuario.getNome().length() >= 3) {
            try {
                usuarioRepository.save(usuario);
            } catch (Exception e) {
                throw new Exception("Não foi possivel criar esse usuario, verifique os atributos e tente novamente.");
            }
        }
    }

    public void atualizarUsuario(Long id, Usuario usuario) throws Exception {
        verificarUsuario(usuario);
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
            if (usuario.getNome().length() > 3 && usuario.getCargo().length() > 3 && usuario.getSenha() > 999) {
                Usuario usuarioParaAtualizar = usuarioOptional.get();
                usuarioParaAtualizar.setCargo(usuario.getCargo());
                usuarioParaAtualizar.setNome(usuario.getNome());
                usuarioParaAtualizar.setSenha(usuario.getSenha());
                usuarioRepository.save(usuarioParaAtualizar);
            }
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro ao atualizar o usuário! verifique os dados e tente novamente.");
        }
    }

    public void excuirUsuario(Long id) throws Exception {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Erro ao excluir o produto! verifique e tente novamente");
        }

    }

    public ArrayList<Usuario> pegarUsuarioPorNome(String nome) throws Exception {
        try {
            ArrayList<Usuario> usuarios = (ArrayList<Usuario>) usuarioRepository.pesquisaPorNome(nome);
            return usuarios;
        } catch (Exception e) {
            throw new Exception("Usuário inválido! verifique e tente novamente.");
        }

    }


    public void verificarUsuario(Usuario usuario) throws Exception {
        if (usuario.getCargo().length() <= 3) {
            throw new Exception("Usuario precisa possuir um cargo com mais de 3 caracteres! Verifique se o cargo foi indicado e tente novamente.");
        }
        if (usuario.getNome().length() <= 3) {
            throw new Exception("Usuario precisa possuir um nome com mais de 3 caracteres! Verifique se o nome foi indicado e tente novamente.");
        }
        if (usuario.getSenha() <= 999) {
            throw new Exception("Usuario precisa possuir uma senha com pelo menos 4 números ! Verifique se a senha foi indicada e tente novamente.");
        }
    }
}
