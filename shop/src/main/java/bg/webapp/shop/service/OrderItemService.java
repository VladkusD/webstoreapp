package bg.webapp.shop.service;

import bg.webapp.shop.dao.OrderItemJPARepository;
import bg.webapp.shop.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    OrderItemJPARepository orderItemRepo;

    public void insertItemOrUpdate(OrderItem item){
        orderItemRepo.save(item);
    }
    public void deleteItem(OrderItem item){
        orderItemRepo.delete(item);
    }

}
