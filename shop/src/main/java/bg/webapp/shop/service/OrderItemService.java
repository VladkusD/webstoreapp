package bg.webapp.shop.service;

import bg.webapp.shop.dao.OrderItemJPARepository;
import bg.webapp.shop.model.OrderItem;
import bg.webapp.shop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderItemService {

    @Autowired
    OrderItemJPARepository orderItemRepo;

    public void createItem(OrderItem item){
        orderItemRepo.save(item);
    }
    public void deleteItem(OrderItem item){
        orderItemRepo.delete(item);
    }
    private Map<OrderItem, Integer> cart = new HashMap<>();

    public Map<OrderItem, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<OrderItem, Integer> cart) {
        this.cart = cart;
    }

//    public List<OrderItem> getHistory(Integer userId){
//        List<OrderItem> orderItems = orderItemRepo.orderHistory(userId);
//        return orderItems;
//    }


}
