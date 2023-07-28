package bg.webapp.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name="web_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Integer orderId;

    @Column(name="user_id")
    private Integer userId;

    @Column(name="status_status")
    private String userStatus;
    OrderEntity(){};
    public OrderEntity(Integer orderID, Integer userId, String userStatus) {
        this.orderId = orderID;
        this.userId = userId;
        this.userStatus = userStatus;
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

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
}
