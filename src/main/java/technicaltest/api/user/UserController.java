package technicaltest.api.user;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import technicaltest.api.request.PasswordUpdateRequest;
import technicaltest.api.request.SignUpRequest;
import technicaltest.api.role.Role;
import technicaltest.api.role.RoleService;

import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;
    private RoleService roleService;
    private PasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder encoder, RoleService roleService) {
        this.userService = userService;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    @GetMapping("/customers")
    public List<User> retrieveAllCustomer() {
        return this.userService.findAll();
    }

    @GetMapping("/customers/{customer_id}")
    public ResponseEntity<User> retrieveCustomer(@PathVariable UUID customer_id) {
        User user = this.userService.findOne(customer_id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping("/customers")
    public ResponseEntity<User> createCustomer(@RequestBody SignUpRequest request) {
        User user = new User(UUID.randomUUID(),
                request.getUsername(),
                encoder.encode(request.getPassword()),
                request.getEmail(),
                request.getContact());
        // Create Role for the customer
        Set<Role> roles = new HashSet<>();
        Role role = this.roleService.findOneByName(Role.CUSTOMER);
        roles.add(role);
        user.setRoles(roles);
        this.userService.createOne(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}")
                .buildAndExpand(user.getUuid()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/admin")
    public ResponseEntity<User> createAdmin(@RequestBody SignUpRequest request) {
        User user = new User(UUID.randomUUID(),
                request.getUsername(),
                encoder.encode(request.getPassword()),
                request.getEmail(),
                request.getContact());
        // Create Role for the admin
        Set<Role> roles = new HashSet<>();
        Role role = this.roleService.findOneByName(Role.ADMIN);
        roles.add(role);
        user.setRoles(roles);
        this.userService.createOne(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}")
                .buildAndExpand(user.getUuid()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/customers/{uuid}")
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResponseEntity<User> deleteCustomer(@PathVariable UUID uuid) {
        User userDeleted = this.userService.deleteOne(uuid);
        return ResponseEntity.status(HttpStatus.OK).body(userDeleted);
    }

    @PutMapping("/changepassword/{customer_id}")
    public ResponseEntity<String> changeCustomerPassword(@PathVariable UUID customer_id, @RequestBody PasswordUpdateRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("password", request.getPassword());
        this.userService.updateUser(customer_id, map);
        return ResponseEntity.noContent().build();
    }
}
