package bg.webapp.shop.dao;

import bg.webapp.shop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemJPARepository extends JpaRepository<OrderItem,Integer> {

//    @Query("SELECT oi.order_id,oi.product_id,oi.product_quantity,o.order_id,o.user_id FROM" +
//            " order_item oi INNER JOIN web_order o ON o.order_id WHERE o.user_id=:userId")
//    List<OrderItem> orderHistory (@Param("userId") Integer userId);
//@Query("SELECT oi.order_id, oi.product_id, oi.product_quantity, o.order_id, o.user_id " +
//        "FROM order_item oi INNER JOIN web_order o ON oi.order_id = o.order_id " +
//        "WHERE o.user_id = :userId")
//List<OrderItem> orderHistory(@Param("userId") Integer userId);


}
