package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dto.UserDTO;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private List<User> userList = new ArrayList<>();


    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userList;
    }

    @PostMapping("/users")
    public User addUser (@RequestBody UserDTO userDTO){
        UUID id = UUID.randomUUID();
        User newUser = new User(id, userDTO.name(), userDTO.email());
        userList.add(newUser);
        return newUser;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable UUID id){
        return userList.stream()
                .filter(user -> user.id().equals(id))
                .findFirst()
                .orElseThrow( () -> new UserNotFoundException("User not found by id: " + id));
    }
}
