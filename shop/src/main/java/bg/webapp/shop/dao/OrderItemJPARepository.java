package bg.webapp.shop.dao;

import bg.webapp.shop.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemJPARepository extends JpaRepository<OrderItem, Integer> {

//    @Query("SELECT oi.order_id,oi.product_id,oi.product_quantity,o.order_id,o.user_id FROM" +
//            " order_item oi INNER JOIN web_order o ON o.order_id WHERE o.user_id=:userId")
//    List<OrderItem> orderHistory (@Param("userId") Integer userId);
//@Query(value = "SELECT oi.order_id, oi.product_id, oi.product_quantity, o.order_id, o.user_id " +
//        "FROM order_item oi INNER JOIN web_order o ON oi.order_id = o.order_id " +
//        "WHERE o.user_id = :userId", nativeQuery = true)
//List<OrderItem> orderHistory(@Param("userId") Integer userId);


//    @Query(value = "SELECT oi.order_id as orderId, oi.product_id as productId, oi.product_quantity as productQuantity, o.order_id as order.orderId, o.user_id as order.user.userId " +
//            "FROM order_item oi INNER JOIN web_order o ON oi.order_id = o.order_id " +
//            "WHERE o.user_id = :userId", nativeQuery = true)
//    List<OrderItem> orderHistory(@Param("userId") Integer userId);

//    @Query(value = "SELECT oi.order_id AS orderId, oi.product_id AS productId, oi.product_quantity AS productQuantity, o.order_id AS orderId, o.user_id AS userId " +
//            "FROM order_item oi INNER JOIN web_order o ON oi.order_id = o.order_id " +
//            "WHERE o.user_id = :userId", nativeQuery = true)
//    List<OrderItem> orderHistory(@Param("userId") Integer userId);

//    @Query(value = "SELECT oi.order_id , oi.product_id, " +
//            "oi.product_quantity , o.order_id , o.user_id , " +
//            "oi.order_item_id FROM order_item oi INNER JOIN web_order o ON oi.order_id = o.order_id " +
//            "WHERE o.user_id = :userId", nativeQuery = true)
//    List<OrderItem> orderHistory(@Param("userId") String userId);

    @Query(value ="SELECT oi.order_id, oi.product_id, oi.product_quantity, o.order_id, o.user_id, oi.order_item_id," +
            " p.product_id AS product_id, p.product_desc, p.product_name " +
            "FROM order_item oi " +
            "INNER JOIN web_order o ON oi.order_id = o.order_id " +
            "INNER JOIN products p ON oi.product_id = p.product_id " +
            "WHERE o.user_id = :userId", nativeQuery = true)
    List<OrderItem> orderHistory(@Param("userId") String userId);



//    @Query("SELECT oi.order, oi.product_id, oi.productQuantity, o.id, o.user.id, oi.id " +
//            "FROM OrderItem oi JOIN oi.order o " +
//            "WHERE o.user.id = :userId")
//    List<OrderItem> orderHistory(@Param("userId") Integer userId);








}
