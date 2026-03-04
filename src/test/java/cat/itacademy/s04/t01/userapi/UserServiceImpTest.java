package cat.itacademy.s04.t01.userapi;

import cat.itacademy.s04.t01.userapi.dto.UserDTO;
import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.model.User;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import cat.itacademy.s04.t01.userapi.service.UserService;
import cat.itacademy.s04.t01.userapi.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldThrowExceptionWhenEmailExists(){
       String existingEmail = "javi123@javier.com";
       UserDTO userDto = new UserDTO("Javi", existingEmail);

       Mockito.when(userRepository.existsByEmail(existingEmail)).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> {userService.createUser(userDto);});
        Mockito.verify(userRepository, Mockito.never()).save(any(User.class));
        Mockito.verify(userRepository, Mockito.times(1)).existsByEmail(existingEmail);

    }

    @Test
    void createUser_shouldGenerateIdAndSaveUser(){
        UserDTO userDto = new UserDTO("Javi","javi123@gmail.com");

        Mockito.when(userRepository.existsByEmail(userDto.email())).thenReturn(false);

        Mockito.when(userRepository.save(any(User.class))).thenAnswer(invocation -> {return invocation.getArgument(0);});

        User createdUser = userService.createUser(userDto);

        assertNotNull(createdUser);
        assertNotNull(createdUser.id());

        assertEquals(userDto.name(), createdUser.name());
        assertEquals(userDto.email(), createdUser.email());

        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));


    }
}
