package br.com.supermarket.services;

import br.com.supermarket.models.User;
import br.com.supermarket.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public ArrayList<User> pegarTodosUsuarios() throws Exception {
        try {
            return (ArrayList<User>) usuarioRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Erro! tente novamente.");
        }
    }


    public User pegarUmUsuario(Long id) throws Exception {
        try {
            Optional<User> usuarioPeloId = usuarioRepository.findById(id);
            return usuarioPeloId.get();
        } catch (Exception e) {
            throw new Exception("User inválido! verifique e tente novamente.");
        }
    }

    public void criarUsuario(User user) throws Exception {
        verificarUsuario(user);
        if (user.getPassword().length() > 5 && user.getName().length() >= 3) {
            try {
                usuarioRepository.save(user);
            } catch (Exception e) {
                throw new Exception("Não foi possivel criar esse user, verifique os atributos e tente novamente.");
            }
        }
    }

    public void atualizarUsuario(Long id, User user) throws Exception {
        verificarUsuario(user);
        try {
            Optional<User> usuarioOptional = usuarioRepository.findById(id);
            if (user.getName().length() > 3 && user.getPassword().length() > 5) {
                User userParaAtualizar = usuarioOptional.get();
                userParaAtualizar.setName(user.getName());
                userParaAtualizar.setPassword(user.getPassword());
                usuarioRepository.save(userParaAtualizar);
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

    public ArrayList<User> pegarUsuarioPorNome(String nome) throws Exception {
        try {
            ArrayList<User> users = (ArrayList<User>) usuarioRepository.pesquisaPorNome(nome);
            return users;
        } catch (Exception e) {
            throw new Exception("Usuário inválido! verifique e tente novamente.");
        }

    }


    public void verificarUsuario(User user) throws Exception {
        if (user.getName().length() <= 3) {
            throw new Exception("User precisa possuir um nome com mais de 3 caracteres! Verifique se o nome foi indicado e tente novamente.");
        }
        if (user.getPassword().length() > 999) {
            throw new Exception("User precisa possuir uma senha com pelo menos 4 números ! Verifique se a senha foi indicada e tente novamente.");
        }
    }
}
