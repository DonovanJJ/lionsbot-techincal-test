package technicaltest.api.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import technicaltest.api.UserRepository;
import technicaltest.api.user.User;
import technicaltest.api.exception.UsernameNotFoundException;
import technicaltest.api.request.LoginRequest;
import technicaltest.api.response.AuthResponse;

import java.util.Optional;

@Controller
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PreAuthorize("permitAll")
    @PostMapping(value="/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest)  {
        Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
        Optional<User> user = this.userRepository.findByUsername(loginRequest.getUsername());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.createToken(authentication, user.get().getUuid());
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }
}
