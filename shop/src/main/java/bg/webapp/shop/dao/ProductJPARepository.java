package bg.webapp.shop.dao;

import bg.webapp.shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ProductJPARepository extends JpaRepository<Product,Integer> {
    List<Product> findByProductNameContainingOrProductDescContaining(String partialName, String partialDesc);

    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:productName% OR p.productDesc LIKE %:productDesc%")
    List<Product> findBySearchTerm(@Param("productName") String productName,@Param("productDesc") String productDesc);


    //List<Product> findByProductName(String productName);
    List<Product> findAll();

}
