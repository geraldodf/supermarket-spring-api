package br.com.supermarket.resources;

import br.com.supermarket.dtos.UserDto;
import br.com.supermarket.models.User;
import br.com.supermarket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService userService;

    @GetMapping
    public ArrayList<User> getAllUsers() throws Exception {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) throws Exception {
        return userService.getUserById(id);
    }

    @GetMapping("/name'")
    public ArrayList<User> getUserByName(@RequestParam(name = "name") String name) throws Exception {
        return userService.getUsersByName(name);
    }

    @PostMapping
    public User createUser(@RequestBody UserDto userdto) throws Exception {
        return userService.createUser(userdto);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user) throws Exception {
        return userService.userUpdate(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) throws Exception {
        userService.deleteUser(id);
    }

}
