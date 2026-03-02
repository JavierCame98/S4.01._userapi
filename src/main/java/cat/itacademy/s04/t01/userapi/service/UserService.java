package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dto.UserDTO;
import cat.itacademy.s04.t01.userapi.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getUsers(String name);

    User createUser(UserDTO userDto);

    User getUserById(UUID id);
}
