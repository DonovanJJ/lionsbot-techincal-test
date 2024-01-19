package technicaltest.api.request;

import jakarta.validation.constraints.NotNull;

/**
 * Represents a post request to create a new order.
 */
public class NewOrderRequest {
    @NotNull(message = "Total price cannot be null")
    private double totalPrice;
    @NotNull(message = "Number of items cannot be null")
    private int noOfItems;
    public NewOrderRequest(double totalPrice, int noOfItems) {
        this.totalPrice = totalPrice;
        this.noOfItems = noOfItems;
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
}
