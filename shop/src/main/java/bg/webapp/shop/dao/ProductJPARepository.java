package bg.webapp.shop.dao;

import bg.webapp.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJPARepository extends JpaRepository<Product,Integer> {
    List<Product> findByProductNameContainingOrProductDescContaining(String partialName, String partialDesc);

}
