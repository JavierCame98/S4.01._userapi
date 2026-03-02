package cat.itacademy.s04.t01.userapi.repository;

import cat.itacademy.s04.t01.userapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<UUID, User> dataBase = new HashMap<>();

    @Override
    public User save(User user) {
        dataBase.put(user.id(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(dataBase.values());
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(dataBase.get(id));
    }

    @Override
    public List<User> searchByName(String name) {
        return dataBase.values().stream()
                .filter(user -> user.name().toLowerCase().contains(name))
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return dataBase.values().stream()
                .anyMatch(user -> user.email().equalsIgnoreCase(email));
    }
}

