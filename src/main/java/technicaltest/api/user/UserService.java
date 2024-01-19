package technicaltest.api.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import technicaltest.api.exception.UsernameAlreadyExistException;
import technicaltest.api.exception.UsernameNotFoundException;
import technicaltest.api.repositories.UserRepository;
import technicaltest.api.exception.UserNotFoundException;
import technicaltest.api.role.Role;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllCustomers() {
        return this.userRepository.findUsersByRoleName(Role.CUSTOMER);
    }

    public User findOne(UUID uuid) throws UserNotFoundException {
        Optional<User> user = this.userRepository.findByUuid(uuid);
        if (user.isEmpty()) {
            throw new UserNotFoundException("No user of that id: " + uuid + " is found");
        }
        return user.get();
    }

    public User findOneByUsername(String username) throws UserNotFoundException {
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    public void createOne(User user) throws UsernameAlreadyExistException {
        Optional<User> existingUser = this.userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new UsernameAlreadyExistException("Username already taken");
        }
        this.userRepository.save(user);
    }

    @Transactional
    public User deleteOne(UUID uuid) throws UserNotFoundException {
        Optional<User> userToDelete = this.userRepository.findByUuid(uuid);
        if (userToDelete.isEmpty()) {
            throw new UserNotFoundException("No user of that uuid: " + uuid + " is found");
        }
        this.userRepository.deleteByUuid(uuid); // Check for error from this deletion
        return userToDelete.get();
    }

    public void updateUser(UUID uuid, Map<String, Object> updates) throws UserNotFoundException {
        Optional<User> user = this.userRepository.findByUuid(uuid);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User is not found!");
        }
        User userToUpdate = user.get();
        for (Map.Entry<String, Object> entry: updates.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            switch (field) {
                case "username":
                    userToUpdate.setUsername((String) value);
                    break;
                case "password":
                    String newPassword = this.passwordEncoder.encode((String) value);
                    userToUpdate.setPassword(newPassword);
                    break;
                case "email":
                    userToUpdate.setEmail((String) value);
                    break;
                case "contact":
                    userToUpdate.setContact((String) value);
                    break;
                default:
                    System.out.println("Invalid field provided when updating user details!");
                    break;
            }
        }
        this.userRepository.save(userToUpdate);
    }
}
