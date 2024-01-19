package technicaltest.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents a request to update an existing user's password.
 */
public class PasswordUpdateRequest {
    @Size(max = 255)
    @NotBlank(message = "Password provided cannot be blank or null")
    private String password;
    public PasswordUpdateRequest() {

    }

    public PasswordUpdateRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
