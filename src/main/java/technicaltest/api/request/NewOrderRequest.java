package technicaltest.api.request;

/**
 * Represents a post request to create a new order.
 */
public class NewOrderRequest {
    private double totalPrice;
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
