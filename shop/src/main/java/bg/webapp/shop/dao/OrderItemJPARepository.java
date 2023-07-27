package bg.webapp.shop.dao;

import bg.webapp.shop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemJPARepository extends JpaRepository<OrderItem,Integer> {
}
