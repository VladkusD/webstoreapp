package bg.webapp.shop.dao;

import bg.webapp.shop.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<OrderEntity,Integer> {
     List<OrderEntity> findByUserId(Integer userId);


}
