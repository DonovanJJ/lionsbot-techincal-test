package technicaltest.api.Order;

import org.springframework.stereotype.Service;
import technicaltest.api.repositories.OrderRepository;
import technicaltest.api.user.UserService;
import technicaltest.api.exception.OrderNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private UserService userService;
    private OrderRepository orderRepository;
    public OrderService(UserService userService, OrderRepository orderRepository) {
        this.userService = userService;
        this.orderRepository = orderRepository;
    }

    public List<Order> findAllOrders() {
        return this.orderRepository.findAll();
    }

    public List<Order> findOrdersForCustomer(UUID uuid) {
        List<Order> orders = this.orderRepository.findOrdersByCustomer(uuid);
        return orders;
    }

    public void createOne(Order order) {
        this.orderRepository.save(order);
    }

    public Order deleteOne(UUID uuid) {
        Optional<Order> orderToDelete = this.orderRepository.findByUuid(uuid);
        if (orderToDelete.isEmpty()) {
            throw new OrderNotFoundException("Order with that uuid cannot be found!");
        }
        this.orderRepository.delete(orderToDelete.get());
        return orderToDelete.get();
    }

    public void updateOrder(UUID uuid, Map<String, Object> updates) throws OrderNotFoundException {
        Optional<Order> order = this.orderRepository.findByUuid(uuid);
        if (order.isEmpty()) {
            throw new OrderNotFoundException("Order is not found!");
        }
        Order orderToUpdate = order.get();
        for (Map.Entry<String, Object> entry: updates.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            switch (field) {
            case "orderDate":
                orderToUpdate.setOrderDate((Date) value);
                break;
            case "totalPrice":
                orderToUpdate.setTotalPrice((double) value);
                break;
            case "noOfItems":
                orderToUpdate.setNoOfItems((int) value);
                break;
            case "contact":
            default:
                System.out.println("Invalid field provided when updating order details!");
                break;
            }
        }
        this.orderRepository.save(orderToUpdate);
    }

}
