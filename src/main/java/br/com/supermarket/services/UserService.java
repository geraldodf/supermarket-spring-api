package br.com.supermarket.services;

import br.com.supermarket.models.User;
import br.com.supermarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public ArrayList<User> getAllUsers() throws Exception {
        return (ArrayList<User>) userRepository.findAll();
    }


    public User getUserById(Long id) throws Exception {
        return userRepository.findById(id).get();
    }

    public ArrayList<User> getUsersByName(String nome) throws Exception {
        return (ArrayList<User>) userRepository.searchByName(nome);
    }

    public User createUser(User user) throws Exception {
        verifyUser(user);
        return userRepository.save(user);
    }

    public User userUpdate(Long id, User user) throws Exception {
        verifyUser(user);
        Optional<User> optionalUser = userRepository.findById(id);
        User userUpdated = optionalUser.get();

        userUpdated.setName(user.getName());
        userUpdated.setSurname(user.getSurname());
        userUpdated.setPassword(user.getPassword());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setPhoneNumber(user.getPhoneNumber());
        userUpdated.setPhoneNumberReserve(user.getPhoneNumberReserve());

        return userRepository.save(userUpdated);
    }

    public void deleteUser(Long id) throws Exception {
        userRepository.deleteById(id);
    }


    public void verifyUser(User user) throws Exception {
        if (user.getName().length() <= 3) {
            throw new Exception("User must have a name with more than 3 characters! Check that the name is entered and try again.");
        }
        if (user.getPassword().length() > 999) {
            throw new Exception("User must have a password with at least 4 numbers! Check that the password has been entered and try again.");
        }
    }
}
