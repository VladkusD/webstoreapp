package bg.webapp.shop.model;

import jakarta.persistence.*;

@Entity
@Table(name="order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id")
    private Integer orderItemId;

    @Column(name="order_id")
    private Integer orderID;

    @Column(name="product_id")
    private Integer productId;

    @Column(name="product_quantity")
    private Integer productQuantity;
    public OrderItem(Integer orderItemId, Integer orderID, Integer productId, Integer productQuantity) {
        this.orderItemId = orderItemId;
        this.orderID = orderID;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
    public Integer getOrderItemId() {
        return orderItemId;
    }
    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }
    public Integer getOrderID() {
        return orderID;
    }
    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public Integer getProductQuantity() {
        return productQuantity;
    }
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}