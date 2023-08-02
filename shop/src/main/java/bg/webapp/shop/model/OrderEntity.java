package bg.webapp.shop.model;

//import jakarta.persistence.*;

import javax.persistence.*;

@Entity
@Table(name="web_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Integer orderId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="order_status")
    private String orderStatus;
    public OrderEntity(){};
    public OrderEntity(Integer orderID, Integer userId, String orderStatus) {
        this.orderId = orderID;
        this.userId = userId;
        this.orderStatus = orderStatus;
    }

    public Integer getOrderID() {
        return orderId;
    }

    public void setOrderID(Integer orderID) {
        this.orderId = orderId;
    }

    public Integer getUserID() {
        return userId;
    }

    public void setUserID(Integer userID) {
        this.userId = userID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
