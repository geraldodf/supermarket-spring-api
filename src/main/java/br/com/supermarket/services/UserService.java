package br.com.supermarket.services;

import br.com.supermarket.dtos.UserDto;
import br.com.supermarket.models.User;
import br.com.supermarket.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public Page<User> pagedSearch(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User createUser(UserDto userDto) {
        User user = createUserReceivingDto(userDto);
        return userRepository.save(user);
    }

    public User userUpdate(Long id, UserDto userDto) {
        User userUpdated = userRepository.findById(id).get();


        userDto.getAddressesIds().forEach(idAddress -> {
            userUpdated.getAddresses().add(addressService.getById(idAddress));
        });

        userUpdated.setName(userDto.getName());
        userUpdated.setSurname(userDto.getSurname());
        userUpdated.setPassword(userDto.getPassword());
        userUpdated.setEmail(userDto.getEmail());
        userUpdated.setPhoneNumber(userDto.getPhoneNumber());
        userUpdated.setPhoneNumberReserve(userDto.getPhoneNumberReserve());

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
