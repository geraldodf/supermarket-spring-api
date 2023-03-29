package br.com.supermarket.resources;

import br.com.supermarket.dtos.UserDto;
import br.com.supermarket.models.User;
import br.com.supermarket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ArrayList<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/name")
    public ArrayList<User> getUserByName(@RequestParam(name = "name") String name) {
        return userService.getUsersByName(name);
    }

    @GetMapping("/page")
    public Page<User> pagedSearch(Pageable pageable) {
        return userService.pagedSearch(pageable);
    }

    @PostMapping
    public User createUser(@RequestBody UserDto userdto) {
        return userService.createUser(userdto);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        return userService.userUpdate(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

}
