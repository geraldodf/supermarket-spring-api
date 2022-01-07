package br.com.supermercado.services;

import br.com.supermercado.models.Usuario;
import br.com.supermercado.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public ArrayList<Usuario> pegarTodosUsuarios() {
        return (ArrayList<Usuario>) usuarioRepository.findAll();
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
        if (usuario.getCargo().length() <= 3) {
            throw new Exception("Usuario precisa possuir um cargo com mais de 3 caracteres! Verifique se o cargo foi indicado e tente novamente.");
        }
        if (usuario.getNome().length() <= 3) {
            throw new Exception("Usuario precisa possuir um nome com mais de 3 caracteres! Verifique se o nome foi indicado e tente novamente.");
        }
        if (usuario.getSenha() <= 999) {
            throw new Exception("Usuario precisa possuir uma senha com pelo menos 4 números ! Verifique se a senha foi indicada e tente novamente.");
        }
        if (usuario.getSenha() >= 999 && usuario.getCargo().length() >= 3 && usuario.getNome().length() >= 3) {
            try {
                usuarioRepository.save(usuario);
            } catch (Exception e) {
                throw new Exception("Não foi possivel criar esse usuario, verifique os atributos e tente novamente.");
            }
        }
    }

        public void atualizarUsuario (Long id, Usuario usuario){
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
            Usuario usuarioParaAtualizar = usuarioOptional.get();
            usuarioParaAtualizar.setCargo(usuario.getCargo());
            usuarioParaAtualizar.setNome(usuario.getNome());
            usuarioParaAtualizar.setSenha(usuario.getSenha());
            usuarioRepository.save(usuarioParaAtualizar);
        }

        public void excuirUsuario (Long id){
            usuarioRepository.deleteById(id);
        }

        public ArrayList<Usuario> pegarUsuarioPorNome (String nome){
            List<Usuario> usuarios = usuarioRepository.pesquisaPorNome(nome);
            return (ArrayList<Usuario>) usuarios;
        }
    }
