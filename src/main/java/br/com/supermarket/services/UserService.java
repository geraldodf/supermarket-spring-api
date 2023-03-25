package br.com.supermarket.services;

import br.com.supermarket.dtos.UserDto;
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
    @Autowired
    private AddressService addressService;


    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) userRepository.findAll();
    }


    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public ArrayList<User> getUsersByName(String nome) {
        return (ArrayList<User>) userRepository.searchByName(nome);
    }

    public User createUser(UserDto userDto) {
        User user = createUserReceivingDto(userDto);
        return userRepository.save(user);
    }


    public User userUpdate(Long id, User user) {
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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User createUserReceivingDto(UserDto userDto) {
        User user = new User();

        userDto.getAddressesIds().forEach(id -> {
            user.getAddresses().add(addressService.getById(id));
        });
        user.setSurname(userDto.getSurname());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPhoneNumberReserve(userDto.getPhoneNumberReserve());

        return user;
    }
}
