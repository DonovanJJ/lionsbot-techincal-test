package technicaltest.api.Order;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import technicaltest.api.user.User;
import technicaltest.api.user.UserService;
import technicaltest.api.jwt.JwtUtil;
import technicaltest.api.request.NewOrderRequest;
import technicaltest.api.request.OrderUpdateRequest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {
    private OrderService orderService;
    private JwtUtil jwtUtil;
    private UserService userService;
    public OrderController(OrderService orderService, JwtUtil jwtUtil, UserService userService) {
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @GetMapping("/orders")
    public List<Order> retrieveAllOrders() {
        return this.orderService.findAllOrders();
    }

    @GetMapping("/orders/{customer_id}")
    public List<Order> retrieveOrdersForCustomer(@PathVariable UUID customer_id) {
        return this.orderService.findOrdersForCustomer(customer_id);
    }
    @GetMapping("/login/orders")
    public List<Order> retrieveOrder() {
        return this.orderService.findAllOrders();
    }

    @PostMapping("/orders")
    public void createOrder(@RequestHeader(name="Authorization") String bearerToken, @RequestBody NewOrderRequest request) {
        String token = bearerToken.substring(7, bearerToken.length());
        UUID uuid = this.jwtUtil.getUuidFromJwt(token);
        User user = this.userService.findOne(uuid);
        Order order = new Order(UUID.randomUUID(),
                new Date(),
                request.getTotalPrice(),
                request.getNoOfItems(),
                user);
        this.orderService.createOne(order);
    }

    @PutMapping("/orders/{order_id}")
    public void updateOrder(@PathVariable UUID order_id, @RequestBody OrderUpdateRequest request) {
        this.orderService.updateOrder(order_id, request.toMap());
    }

    @DeleteMapping("/orders/{order_id}")
    public Order deleteOrder(@PathVariable UUID order_id) {
        return this.orderService.deleteOne(order_id);
    }
}
