package technicaltest.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import technicaltest.api.RoleRepository;
import technicaltest.api.request.PasswordUpdateRequest;
import technicaltest.api.request.SignUpRequest;
import technicaltest.api.role.Role;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private PasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.userService = userService;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }

    @PreAuthorize("")
    @GetMapping("/customers")
    public List<User> retrieveAllCustomer() {
        return this.userService.findAll();
    }

    @GetMapping("/customers/{customer_id}")
    public User retrieveCustomer(@PathVariable UUID customer_id) {
        return this.userService.findOne(customer_id);
    }

    @PostMapping("/customers")
    public void createCustomer(@RequestBody SignUpRequest request) {
        User user = new User(UUID.randomUUID(),
                request.getUsername(),
                encoder.encode(request.getPassword()),
                request.getEmail(),
                request.getContact());
        // Create Role for the customer
        Set<Role> roles = new HashSet<>();
        Optional<Role> role = this.roleRepository.findByName(Role.CUSTOMER);
        if (role.isEmpty()) {
            return;
        }
        roles.add(role.get());
        user.setRoles(roles);
        this.userService.createOne(user);
    }

    @PostMapping("/admin")
    public void createAdmin(@RequestBody SignUpRequest request) {
        User user = new User(UUID.randomUUID(),
                request.getUsername(),
                encoder.encode(request.getPassword()),
                request.getEmail(),
                request.getContact());
        // Create Role for the admin
        Set<Role> roles = new HashSet<>();
        Optional<Role> role = this.roleRepository.findByName(Role.ADMIN);
        if (role.isEmpty()) {
            return;
        }
        roles.add(role.get());
        user.setRoles(roles);
        this.userService.createOne(user);
    }

    @DeleteMapping("/customers/{uuid}")
    public User deleteCustomer(@PathVariable UUID uuid) {
        return this.userService.deleteOne(uuid);
    }

    @PutMapping("/changepassword/{customer_id}")
    public void changeCustomerPassword(@PathVariable UUID customer_id, @RequestBody PasswordUpdateRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("password", request.getPassword());
        this.userService.updateUser(customer_id, map);
    }
}
