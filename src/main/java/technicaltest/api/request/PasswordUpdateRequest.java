package technicaltest.api.request;

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
