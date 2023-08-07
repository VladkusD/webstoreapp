package bg.webapp.shop.service;

import bg.webapp.shop.dao.OrderJPARepository;
import bg.webapp.shop.dao.ProductJPARepository;
import bg.webapp.shop.model.OrderEntity;
import bg.webapp.shop.model.OrderItem;
import bg.webapp.shop.model.Product;
import bg.webapp.shop.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SessionScope
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
