package bg.webapp.shop.service;

import bg.webapp.shop.dao.OrderJPARepository;
import bg.webapp.shop.model.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrderService {
    @Autowired
    OrderJPARepository orderRepo;

    public void createOrder(OrderEntity orderEntity){
        orderRepo.save(orderEntity);
    }
    public List<OrderEntity> listOrdersByUser(Integer userId){
        return orderRepo.findByUserId(userId);
    }

}
