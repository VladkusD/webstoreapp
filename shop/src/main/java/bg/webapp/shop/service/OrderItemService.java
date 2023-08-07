package bg.webapp.shop.service;

import bg.webapp.shop.dao.OrderItemJPARepository;
import bg.webapp.shop.model.OrderItem;
import bg.webapp.shop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderItemService {

    @Autowired
    OrderItemJPARepository orderItemRepo;

    @Autowired
    ProductsService productsService;

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

    public List<OrderItem> getHistory(String userId){
        return orderItemRepo.orderHistory(userId);
    }

    public NotificationAlert addItemToCartById(Integer productId,Integer orderItemQuantity){
        Product product = productsService.findById(productId);
        OrderItem orderItem = new OrderItem(product, orderItemQuantity);
        orderItem.setProductName(product.getProductName());
        orderItem.setProductDesc(product.getProductDesc());
        orderItem.setProductPrice(product.getProductPrice());
        NotificationAlert notificationAlert = new NotificationAlert("Product: "+ orderItem.getProductName()
                +" successfully added to cart!");
        Map<OrderItem, Integer> cart = getCart();
        boolean itemFound = false;
        for (OrderItem item : cart.keySet()) {
            if (item.getProductId().equals(productId)) {
                cart.put(item, cart.get(item) + orderItemQuantity);
                itemFound = true;
                break;
            }
        }

        if (!itemFound) {
            cart.put(orderItem, orderItemQuantity);
        }
        return notificationAlert;
    }

    public void incrementItemQuantityInCart (Integer productId){
        Product product = productsService.findById(productId);
        String productName = product.getProductName();
        Map<OrderItem, Integer> cart = getCart();
        OrderItem orderItem = cart.keySet().stream()
                .filter(item -> item.getProductName().equals(productName))
                .findFirst().orElse(null);
        int newQuantity = cart.get(orderItem) + 1;
        cart.put(orderItem, newQuantity);
    }

    public void decrementItemQuantityInCart (Integer productId){
        Product product = productsService.findById(productId);
        String productName = product.getProductName();
        Map<OrderItem, Integer> cart = getCart();
        OrderItem orderItem = cart.keySet().stream()
                .filter(item -> item.getProductName().equals(productName))
                .findFirst().orElse(null);
        int newQuantity = cart.get(orderItem) - 1;
        if (newQuantity == 0) {
            cart.remove(orderItem);
        } else {
            cart.put(orderItem, newQuantity);
        }
    }

    public String getItemsInCartTotalSum(){
        Map<OrderItem, Integer> cart = getCart();
        double totalSum = cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getProductPrice() * entry.getValue())
                .sum();
        return String.format("%.2f", totalSum);
    }
     public List<OrderItem> getOrderHistoryList (String userId){
         List<OrderItem> history = getHistory(userId);
         Collections.sort(history);
         for (OrderItem orderItem : history) {
             Product product = productsService.findById(orderItem.getProductId());
             orderItem.setProductName(product.getProductName());
             orderItem.setProductDesc(product.getProductDesc());
             orderItem.setProductPrice(product.getProductPrice());
         }
         return history;
     }
}
