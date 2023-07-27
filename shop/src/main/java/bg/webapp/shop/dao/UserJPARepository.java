package bg.webapp.shop.dao;

import bg.webapp.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<User,Integer> {
}
