package technicaltest.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Represents a request to create a new user.
 */
public class SignUpRequest {
    @Size(max = 50)
    @NotBlank(message = "Username provided cannot be blank or null")
    private String username;
    @Size(max = 255)
    @NotBlank(message = "Password provided cannot be blank or null")
    private String password;
    @Size(max = 100)
    @Email(message = "Invalid email format!")
    @NotBlank(message = "email provided cannot be blank or null")
    private String email;
    @Size(max = 15)
    @NotBlank(message = "Contact provided cannot be blank or null")
    private String contact;
    public SignUpRequest(String username, String password, String email, String contact) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.contact = contact;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
