package bg.webapp.shop.service;

import bg.webapp.shop.dao.ProductJPARepository;
import bg.webapp.shop.model.Product;
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductsService {

    @Autowired
    ProductJPARepository productRepo;

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveOrUpdateProduct(Product product) {
        productRepo.save(product);
    }

    public void deleteProduct(Product product) {
        productRepo.delete(product);
    }

    public List<Product> listAllProducts() {
        return productRepo.findAll();
    }

    @Query("SELECT p FROM Product p WHERE p.productName LIKE :condition OR p.productDesc LIKE :condition")
    public List<Product> findBySearchTerm(String productName, String productDesc) {
        return productRepo.findBySearchTerm(productName, productDesc);
    }
    public Product findById(int productId) {
        return productRepo.findById(productId).orElse(null);
    }

//    public Product findById(int productId){
//        return productRepo.getReferenceById(productId);
//    }


    //    public List<Product> findByProductName(String condition) {
//        return productRepo.findAll();
//    }
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    public List<Product> searchProduct(String productName, String productDesc) {
        return productRepo.findByProductNameContainingOrProductDescContaining(productName, productDesc);
    }
}
