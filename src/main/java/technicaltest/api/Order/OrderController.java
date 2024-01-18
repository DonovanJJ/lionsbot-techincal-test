package technicaltest.api.Order;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import technicaltest.api.exception.OrderNotFoundException;
import technicaltest.api.role.Role;
import technicaltest.api.user.User;
import technicaltest.api.user.UserService;
import technicaltest.api.authentication.JwtUtil;
import technicaltest.api.request.NewOrderRequest;
import technicaltest.api.request.OrderUpdateRequest;

import java.net.URI;
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
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public List<Order> retrieveAllOrders() {
        return this.orderService.findAllOrders();
    }

    @GetMapping("/orders/{customer_id}")
    @PreAuthorize("hasAnyRole('" + Role.ADMIN + "', '" + Role.CUSTOMER + "')")
    public List<Order> retrieveOrdersForCustomer(@PathVariable UUID customer_id) {
        return this.orderService.findOrdersForCustomer(customer_id);
    }

    @PostMapping("/orders")
    public ResponseEntity createOrder(@RequestHeader(name="Authorization") String bearerToken,
                                @RequestBody NewOrderRequest request) {
        String token = bearerToken.substring(7, bearerToken.length());
        UUID uuid = this.jwtUtil.getUuidFromJwt(token);
        User user = this.userService.findOne(uuid);
        Order order = new Order(UUID.randomUUID(),
                new Date(),
                request.getTotalPrice(),
                request.getNoOfItems(),
                user);
        this.orderService.createOne(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}")
                .buildAndExpand(order.getUuid()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/orders/{order_id}")
    @PreAuthorize("hasAnyRole('" + Role.ADMIN + "', '" + Role.CUSTOMER + "')")
    public ResponseEntity<String> updateOrder(@PathVariable UUID order_id,
                                              @RequestBody OrderUpdateRequest request) {
            this.orderService.updateOrder(order_id, request.toMap());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully updated order");
    }

    @DeleteMapping("/orders/{order_id}")
    @PreAuthorize("hasRole('" + Role.ADMIN + "')")
    public ResponseEntity<Order> deleteOrder(@PathVariable UUID order_id) {
            Order orderDeleted = this.orderService.deleteOne(order_id);
            return ResponseEntity.status(HttpStatus.OK).body(orderDeleted);
    }
}
