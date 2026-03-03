package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.dto.UserDTO;
import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.exceptions.UserNotFoundException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers(String name) {
        if (name == null || name.isEmpty()){
            return userRepository.findAll();
        }
        return userRepository.searchByName(name);
    }

    @Override
    public User createUser(UserDTO userDto) {
        if(userRepository.existsByEmail(userDto.email()))
            throw new EmailAlreadyExistsException("This email already exists: " + userDto.email());

        User newUser = new User(UUID.randomUUID(), userDto.name(), userDto.email());
        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("This id dosen't exists" + id.toString()));
    }
}
