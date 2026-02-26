package cat.itacademy.s04.t01.userapi.controllers;

import cat.itacademy.s04.t01.userapi.dto.UserDTO;
import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    private List<User> userList = new ArrayList<>();


    @GetMapping("/users")
    private List<User> getAllUsers(){
        return userList;
    }

    @PostMapping("/users")
    public User addUser (@RequestBody UserDTO userDTO){
        UUID id = UUID.randomUUID();
        User newUser = new User(id, userDTO.name(), userDTO.email());
        userList.add(newUser);
        return newUser;
    }

}
