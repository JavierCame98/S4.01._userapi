package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dto.UserDTO;
import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers(String name) {
        return List.of();
    }

    @Override
    public User createUser(UserDTO userDto) {
        if(userRepository.existsByEmail(userDto.email()))
            throw new EmailAlreadyExistsException("This email already exists: " + userDto.email().toString());

        User newUser = new User(UUID.randomUUID(), userDto.name(), userDto.email());
        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(UUID id) {
        return null;
    }
}
