package bg.webapp.shop.model;

//import jakarta.persistence.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name="user_first_name")
    private String userFirstName;

    @Column(name="user_last_name")
    private String userLastName;

    @Column(name="user_phone")
    private String userPhone;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="user_address")
    private String userAddress;

    @Column(name="user_right")
    private Integer userRight;

    @Column(name="user_password")
    private String userPassword;
    public User(){};
    public User(String userFirstName, String userLastName, String userPhone, String userEmail, String userAddress, Integer userRight, String userPassword) {

        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.userRight = userRight;
        this.userPassword = userPassword;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Integer getUserRight() {
        return userRight;
    }

    public void setUserRight(Integer userRight) {
        this.userRight = userRight;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
