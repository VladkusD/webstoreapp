package bg.webapp.shop.service;

import bg.webapp.shop.dao.OrderJPARepository;
import bg.webapp.shop.model.OrderEntity;

import bg.webapp.shop.model.OrderItem;
import bg.webapp.shop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Service
@SessionScope
public class OrderService {

    @Autowired
    OrderJPARepository orderRepo;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    public void createOrder(OrderEntity orderEntity){
        orderRepo.save(orderEntity);
    }
    public List<OrderEntity> listOrdersByUser(Integer userId){
        return orderRepo.findByUserId(userId);
    }

    public StringBuilder createNewOrder (User user,boolean registerAcc,String randomPassword){
        OrderEntity order = new OrderEntity();
        LocalDate currentDate = LocalDate.now();
        order.setOrderStatus("Pending");
        order.setUserID(user.getUserId());
        orderService.createOrder(order);
        StringBuilder orderItemsEmail = new StringBuilder();
        Integer orderQuantity;
        double orderTotal = 0.0;
        Map<OrderItem, Integer> cart = orderItemService.getCart();

        for (OrderItem item : cart.keySet()) {
            item.setOrderID(order.getOrderID());
            orderItemService.createItem(item);
            orderQuantity = item.getProductQuantity();
            orderTotal += orderQuantity * item.getProductPrice();
            orderItemsEmail.append("\n" + item.toString() + ": " + orderQuantity);
        }
        if (registerAcc) {
            orderItemsEmail.append("\n Your password is : " + randomPassword);
        }
        orderItemsEmail.append("\n Total amount of the order: " + orderTotal);
        orderItemsEmail.append("\n Date of the Order" + currentDate);
        cart.clear();
        return orderItemsEmail;
    }

}
