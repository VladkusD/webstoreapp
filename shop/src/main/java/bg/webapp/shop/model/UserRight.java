package bg.webapp.shop.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user_right")
public class UserRight implements Serializable {

    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name="system_role")
    private Integer systemRole;



    public UserRight(){

    }

    public UserRight(Integer userId,Integer systemRole) {
        this.userId = userId;
        this.systemRole = systemRole;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(Integer systemRole) {
        this.systemRole = systemRole;
    }
}
