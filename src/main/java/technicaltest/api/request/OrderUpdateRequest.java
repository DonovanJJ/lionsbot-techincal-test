package technicaltest.api.request;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a request to update an existing order.
 */
public class OrderUpdateRequest {
    private Date orderDate;
    @NotNull(message = "Total price cannot be null")
    private double totalPrice;
    @NotNull(message = "Number of items cannot be null")
    private int noOfItems;
    public OrderUpdateRequest(Date orderDate, double totalPrice, int noOfItems) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.noOfItems = noOfItems;
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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("orderDate", this.orderDate);
        map.put("totalPrice", this.totalPrice);
        map.put("noOfItems", this.noOfItems);
        return map;
    }
}
