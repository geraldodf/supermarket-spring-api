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
        try {
            return (ArrayList<User>) userRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Error! Try again later, please.");
        }
    }


    public User getUserById(Long id) throws Exception {
        try {
            Optional<User> user = userRepository.findById(id);
            return user.get();
        } catch (Exception e) {
            throw new Exception("Invalid user. Verify and try again, please!.");
        }
    }

    public void createUser(User user) throws Exception {
        verifyUser(user);
        if (user.getPassword().length() > 5 && user.getName().length() >= 3) {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                throw new Exception("It was not possible to create this user, check the attributes and try again.");
            }
        }
    }

    public void userUpdate(Long id, User user) throws Exception {
        verifyUser(user);
        try {
            Optional<User> optionalUser = userRepository.findById(id);
            if (user.getName().length() > 3 && user.getPassword().length() > 5) {
                User userUpdated = optionalUser.get();
                userUpdated.setName(user.getName());
                userUpdated.setPassword(user.getPassword());
                userRepository.save(userUpdated);
            }
        } catch (Exception e) {
            throw new Exception("An error occurred while updating the user! check the data and try again.");
        }
    }

    public void deleteUser(Long id) throws Exception {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Error deleting product! check and try again.");
        }

    }

    public ArrayList<User> getUsersByName(String nome) throws Exception {
        try {
            return (ArrayList<User>) userRepository.searchByName(nome);
        } catch (Exception e) {
            throw new Exception("Invalid user! check and try again.");
        }

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
