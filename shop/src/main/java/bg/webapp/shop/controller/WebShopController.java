package bg.webapp.shop.controller;

import bg.webapp.shop.model.OrderEntity;
import bg.webapp.shop.model.OrderItem;
import bg.webapp.shop.model.Product;
import bg.webapp.shop.model.User;
import bg.webapp.shop.service.*;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.io.*;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class WebShopController {

    @Autowired
    UserService userService;
    @Autowired
    ProductsService productsService;

    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;


    @RequestMapping(value = "/")
    public ModelAndView listProducts(ModelAndView model) {
        List<Product> listProducts = productsService.listAllProducts();
        model.addObject("listProducts", listProducts);
        model.setViewName("homepage");
        if (orderItemService.getCart().size()>0){
            model.addObject("cart", orderItemService.getCart());
        }

        return model;
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ModelAndView findContacts(@RequestParam("condition") String condition, ModelAndView model) {
        List<Product> listProducts = productsService.findBySearchTerm(condition, condition);
        model.addObject("listProducts", listProducts);
        model.addObject("condition", condition);
        model.setViewName("homepage");
        return model;
    }

    @RequestMapping(value = "/images/{productImage}", method = RequestMethod.GET)
    public @ResponseBody byte[] getImage(@PathVariable String productImage) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream("D:\\java_projects\\springprojects\\webstoreapp\\images\\" + productImage));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }

    @RequestMapping(value = "/addProductToCart/{productId}", method = RequestMethod.POST)
    public ModelAndView addProductToCart(@PathVariable Integer productId,
                                         @RequestParam("orderItemQuantity") Integer orderItemQuantity,
                                         ModelAndView model) {
        Product product = productsService.findById(productId);
//  System.out.println(orderItemQuantity);
// no need to add when using REDIRECT !!!!!!
        //  return "redirect:/cart";
        List<Product> listProducts = productsService.listAllProducts();
        model.addObject("listProducts", listProducts);
        OrderItem orderItem = new OrderItem(product, orderItemQuantity);

        orderItem.setProductName(product.getProductName());
        orderItem.setProductDesc(product.getProductDesc());
        orderItem.setProductPrice(product.getProductPrice());
        NotificationAlert notificationAlert = new NotificationAlert("Product: "+ orderItem.getProductName() +" successfully added to cart!");
        Map<OrderItem, Integer> cart = orderItemService.getCart();
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
        model.addObject("cart", orderItemService.getCart());
        model.addObject("notification",notificationAlert);
        model.setViewName("homepage");

        return model;
    }
    @GetMapping("/cart/decrement/{productId}")
   // @RequestParam quantity <- moga da prihvana quantity kato requestparam ako go definiram
    // v href-a s (,quantity=${instance.value})
    public String decrementQuantity(@PathVariable Integer productId) {
        Product product = productsService.findById(productId);
        String productName = product.getProductName();
        Map<OrderItem, Integer> cart = orderItemService.getCart();
        OrderItem orderItem = cart.keySet().stream()
                .filter(item -> item.getProductName().equals(productName))
                .findFirst().orElse(null);
        int newQuantity = cart.get(orderItem) - 1;
        if (newQuantity == 0) {
            cart.remove(orderItem);
        } else {
            cart.put(orderItem, newQuantity);
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/increment/{productId}")
    public String incrementQuantity(@PathVariable Integer productId) {
        Product product = productsService.findById(productId);
        String productName = product.getProductName();
        Map<OrderItem, Integer> cart = orderItemService.getCart();
        OrderItem orderItem = cart.keySet().stream()
                .filter(item -> item.getProductName().equals(productName))
                .findFirst().orElse(null);
        int newQuantity = cart.get(orderItem) + 1;
        cart.put(orderItem, newQuantity);

        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart")
    public ModelAndView showCart(ModelAndView model) {
        Map<OrderItem, Integer> cart = orderItemService.getCart();

        double totalSum = cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getProductPrice() * entry.getValue())
                .sum();
        String formattedTotalSum = String.format("%.2f", totalSum);
        model.addObject("cart", orderItemService.getCart());
        model.addObject("totalSum", formattedTotalSum);
        model.setViewName("cart");
        return model;
    }

    @RequestMapping(value = "/checkout")
    public ModelAndView checkout(ModelAndView model) {


        model.setViewName("checkout");
        //emailService.sendSimpleMail();
        return model;
    }
    // @RequestParam quantity <- moga da prihvana quantity kato requestparam ako go definiram
    // v href-a s (,quantity=${instance.value})
    @RequestMapping(value="/completeOrder" , method= RequestMethod.POST)
    public ModelAndView completeOrder(@RequestParam("email") String email,
                                      @RequestParam("name") String name,
                                      @RequestParam("userAddress") String userAddress,
                                      @RequestParam("phoneNumber") String phoneNumber,
                                      ModelAndView model){
        String[] nameSplit = name.split("\\s+");
        User user = new User();
        user.setUserFirstName(nameSplit[0]);
        user.setUserLastName(nameSplit[1]);
        user.setUserEmail(email);
        user.setUserAddress(userAddress);
        user.setUserPhone(phoneNumber);
        user.setUserStatus(0);
        user.setUserPassword("blqblqblq");
        userService.createUser(user);

        OrderEntity order = new OrderEntity();
        order.setOrderStatus("Pending");
        order.setUserID(user.getUserId());


        orderService.createOrder(order);
        Map<OrderItem, Integer> cart = orderItemService.getCart();
        for (OrderItem item : cart.keySet()){
            item.setOrderID(order.getOrderID());
            orderItemService.createItem(item);
        }
        cart.clear();
        model.setViewName("homepage");
        return model;
    }
//    @RequestMapping(value="/completeOrder" , method= RequestMethod.POST)
//    public ModelAndView completeOrder(ModelAndView model){
//
//        User user = new User();
//        user.setUserFirstName("ivan");
//        user.setUserLastName("ivanov");
//        user.setUserEmail("ivanko@mail.bg");
//        user.setUserPhone("123123124324234");
//        userService.createUser(user);
//
//        OrderEntity order = new OrderEntity();
//        order.setOrderStatus("Pending");
//        order.setUserID(user.getUserId());
//
//        orderService.createOrder(order);
//        Map<OrderItem, Integer> cart = orderItemService.getCart();
//        for (OrderItem item : cart.keySet()){
//            orderItemService.createItem(item);
//        }
//
//        cart.clear();
//        List<Product> listProducts = productsService.listAllProducts();
//        model.addObject("listProducts", listProducts);
//
//        model.setViewName("homepage");
//        return model;
//    }

}
