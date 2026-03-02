package cat.itacademy.s04.t01.userapi;


import cat.itacademy.s04.t01.userapi.controllers.UserController;
import cat.itacademy.s04.t01.userapi.dto.UserDTO;

import cat.itacademy.s04.t01.userapi.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserController userController;

    @BeforeEach
    void setUp() {
        userController.clearUsers();
    }

    @Test
    void getUsers_returnsEmptyListInitially() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createUser_returnsUserWithId() throws Exception {
        UserDTO userDto = new UserDTO("Javier", "javier@mail.com");

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Javier"));
    }

    @Test
    void getUserById_returnsCorrectUser() throws  Exception{
        UserDTO userDto = new UserDTO("Javier", "javier@mail.com");

        String responseBody = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        User createdUser = objectMapper.readValue(responseBody, User.class);
        String userID = createdUser.id().toString();

        mockMvc.perform(get("/users/" + userID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Javier"))
                .andExpect(jsonPath("$.id").value(userID));

        // Primer afegeix un usuari amb POST
        // Després GET /users/{id} i comprova que torni aquest usuari
    }

    @Test
    void getUserById_returnsNotFoundIfMissing() throws Exception {
        String randomID = UUID.randomUUID().toString();

        mockMvc.perform(get("/users" + randomID))
                .andExpect(status().isNotFound());
        // Simula GET /users/{id} amb un id aleatori
        // Espera 404
    }

    @Test
    void getUsers_withNameParam_returnsFilteredUsers() throws Exception {

        UserDTO user1 = new UserDTO("Jordi", "jordi@mail.com");
        UserDTO user2 = new UserDTO("Javier", "javier@mail.com");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user2)));

        mockMvc.perform(get("/users").param("name", "jo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Jordi"));

        // Afegeix dos usuaris amb POST
        // Fa GET /users?name=jo i comprova que només torni els que contenen "jo"
    }


}
