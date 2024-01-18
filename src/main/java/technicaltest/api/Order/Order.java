package technicaltest.api.Order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import technicaltest.api.user.User;
import technicaltest.api.shipment.Shipment;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("order_id")
    private UUID uuid;
    @Column(name="order_date", nullable = false)
    private Date orderDate;
    @Column(name="total_price", nullable = false)
    private double totalPrice;
    @Column(name="no_of_items", nullable = false)
    private int noOfItems;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="user_id")
    private User user;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "shipment_id", referencedColumnName = "shipment_id")
    private Shipment shipment;
    public Order() {

    }
    public Order(UUID uuid, Date orderDate, double totalPrice, int noOfItems, User user) {
        this.uuid = uuid;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.noOfItems = noOfItems;
        this.user = user;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(int noOfItems) {
        this.noOfItems = noOfItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
