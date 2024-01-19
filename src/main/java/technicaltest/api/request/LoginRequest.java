package technicaltest.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * This class models the data inside a user login post request.
 */
public class LoginRequest {
    @Size(max = 50)
    @NotBlank(message = "username cannot be blank")
    private String username;
    @Size(max = 255)
    @NotBlank(message = "password cannot be blank")
    private String password;
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
