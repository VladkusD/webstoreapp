package bg.webapp.shop.service;

import bg.webapp.shop.dao.ProductJPARepository;
import bg.webapp.shop.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductsService {

    @Autowired
    ProductJPARepository productRepo;

    @Transactional(Transactional.TxType.REQUIRED)
    public void saveOrUpdateProduct(Product product){
        productRepo.save(product);
    }

    public void deleteProduct(Product product){
        productRepo.delete(product);
    }
    public List<Product> listAllProducts(){
       return productRepo.findAll();
    }

    public List<Product> searchProduct(String productName, String productDesc){
        return productRepo.findByProductNameContainingOrProductDescContaining(productName,productDesc);
    }
}
