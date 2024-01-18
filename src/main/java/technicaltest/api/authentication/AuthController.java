package technicaltest.api.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import technicaltest.api.user.User;
import technicaltest.api.request.LoginRequest;
import technicaltest.api.response.AuthResponse;
import technicaltest.api.user.UserService;

@Controller
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping(value="/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest)  {
        Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));
        User user = this.userService.findOneByUsername(loginRequest.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.createToken(authentication, user.getUuid());
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }
}
