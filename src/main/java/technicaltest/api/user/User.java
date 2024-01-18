package technicaltest.api.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import technicaltest.api.Order.Order;
import technicaltest.api.address.Address;
import technicaltest.api.role.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("user_id")
    private UUID uuid;
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String username;
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String password;
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String email;
    @Size(max = 15)
    @Column(nullable = false, length = 15)
    private String contact;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;
    @JsonManagedReference
    @OneToMany(mappedBy="user", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(UUID uuid, String username, String password, String email, String contact) {
        this.uuid = uuid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.contact = contact;
    }

    public UUID getUuid() {
        return this.uuid;
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

    public String getEmail() {
        return email;
    }
    public List<Order> getOrders() {
        return orders;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

