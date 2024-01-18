package technicaltest.api.request;

/**
 * Represents a request to update an existing user's password.
 */
public class PasswordUpdateRequest {
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
