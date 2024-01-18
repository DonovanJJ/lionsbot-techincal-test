package technicaltest.api.request;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderUpdateRequest {
    private Date orderDate;
    private double totalPrice;
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
