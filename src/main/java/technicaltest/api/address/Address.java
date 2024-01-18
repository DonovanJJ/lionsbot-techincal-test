package technicaltest.api.address;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import technicaltest.api.user.User;

import java.util.UUID;

@Entity
@Table(name="addresses")
public class Address {
    @Id
    @Column(name="address_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("address_id")
    private UUID uuid;
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String address1;
    @Size(max = 150)
    @Column(nullable = true, length = 150)
    private String address2;
    @Column(name = "postal_code", nullable = false)
    private int postalCode;
    @Size(max = 50)
    @Column(nullable = false, length=50)
    private String country;
    @OneToOne(mappedBy = "address")
    private User user;
    public Address(UUID uuid, String address1, String address2, int postalCode, String country) {
        this.uuid = uuid;
        this.address1 = address1;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.country = country;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
