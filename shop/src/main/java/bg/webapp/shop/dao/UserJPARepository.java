package bg.webapp.shop.dao;

import bg.webapp.shop.model.Product;
import bg.webapp.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserJPARepository extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u WHERE u.userEmail LIKE :userEmail")
    User findUserByEmail(@Param("userEmail") String userEmail);

}
