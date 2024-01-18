package technicaltest.api.role;

import org.springframework.stereotype.Service;
import technicaltest.api.exception.RoleNotFoundException;
import technicaltest.api.repositories.RoleRepository;

import java.util.Optional;

@Service
public class RoleService {
    private RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role findOneByName(String name) {
        Optional<Role> role = this.roleRepository.findByName(name);
        if (role.isEmpty()) {
            throw new RoleNotFoundException(name);
        }
        return role.get();
    }
}
