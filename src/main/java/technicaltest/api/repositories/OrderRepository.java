package technicaltest.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import technicaltest.api.Order.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByUuid(UUID uuid);
    @Query("SELECT o from Order o WHERE o.user.uuid = :uuid")
    List<Order> findOrdersByCustomer(UUID uuid);
}
