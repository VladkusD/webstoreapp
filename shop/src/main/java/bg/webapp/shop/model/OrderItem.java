package bg.webapp.shop.model;

//import jakarta.persistence.*;

import javax.persistence.*;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    @Transient
    private String productName;

    @Transient
    private String productDesc;

    @Transient
    private Double productPrice;


    public OrderItem(){};
    public OrderItem(Integer orderItemId, Integer orderID, Integer productId, Integer productQuantity) {
        this.orderItemId = orderItemId;
        this.orderID = orderID;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
    public OrderItem(Product product,Integer productQuantity) {
        this.productId = product.getProductId();
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
