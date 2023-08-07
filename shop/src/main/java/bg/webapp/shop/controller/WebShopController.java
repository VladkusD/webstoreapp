package bg.webapp.shop.controller;

import bg.webapp.shop.exceptions.CrudValidationException;
import bg.webapp.shop.model.*;
import bg.webapp.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.io.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @Autowired
    EmailService emailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRightService userRightService;

//    @Autowired
//    GetOrderHistory getOrderHistory;


    @RequestMapping(value = "/")
    public ModelAndView listProducts(ModelAndView model, Principal principal) {
//        List<Product> listProducts = productsService.listAllProducts();
//        model.addObject("listProducts", listProducts);
        findPaginated(1,model,principal);
        model.addObject("principal",principal);
        //model.setViewName("homepage"); <- pre4i na paginationa ako se dobavq tuk
        // kade da turq tozi metod za koli4kata ....
        if (orderItemService.getCart().size()>0){
            model.addObject("cart", orderItemService.getCart());
        }
        if (principal != null){
//            User loggedUser = userService.getUserByFirstName(principal.getName());
            User loggedUser = userService.getRegisteredUserByEmail(principal.getName());
            System.out.println(loggedUser.getUserEmail());
            System.out.println(loggedUser.getUserId());
            System.out.println(loggedUser.getUserFirstName());
            model.addObject("userId", loggedUser.getUserId());
//            model.addObject("welcomename", loggedUser.getUserFirstName());
        } else{
            System.out.println("not logged user");
        }
        return model;
    }

    @GetMapping(value = "/page/{pageNo}")
    public ModelAndView findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                      ModelAndView model,Principal principal){
        int pageSize = 6;
        Page<Product> page = productsService.findPaginated(pageNo,pageSize);
        List<Product>  listProducts = page.getContent();
        model.addObject("currentPage", pageNo);
        model.addObject("totalPages", page.getTotalPages());
        model.addObject("totalItems", page.getTotalElements());
        model.addObject("listProducts", listProducts);
        model.addObject("principal",principal);
        model.setViewName("homepage");
        return model;
    };

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ModelAndView findContacts(@RequestParam("condition") String condition, ModelAndView model,Principal principal) {
        List<Product> listProducts = productsService.findBySearchTerm(condition, condition);
        model.addObject("listProducts", listProducts);
        model.addObject("condition", condition);
        model.addObject("principal",principal);
        model.setViewName("homepage");
        return model;
    }

    @RequestMapping(value = "/images/{productImage}", method = RequestMethod.GET)
    public @ResponseBody byte[] getImage(@PathVariable String productImage) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream("D:\\java_projects\\" +
                "springprojects\\webstoreapp\\images\\" + productImage));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }


//    ----------------------------- SUS HTTPServletRequest
//    --------------------- int contactId = Integer.parseInt(request.getParameter("id")); <-Za RequestParam
//    @RequestMapping(value = "/images/{productImage}", method = RequestMethod.GET)


    @RequestMapping(value = "/addProductToCart/{productId}", method = RequestMethod.POST)
    public ModelAndView addProductToCart(@PathVariable Integer productId,
                                         @RequestParam("orderItemQuantity") Integer orderItemQuantity,
                                         ModelAndView model,Principal principal) {
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
        NotificationAlert notificationAlert = new NotificationAlert("Product: "+ orderItem.getProductName()
                +" successfully added to cart!");
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
        model.addObject("principal",principal);
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

    @RequestMapping(value = "/cart" , method=RequestMethod.GET)
    public ModelAndView showCart(ModelAndView model, Principal principal) {
        Map<OrderItem, Integer> cart = orderItemService.getCart();

        double totalSum = cart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getProductPrice() * entry.getValue())
                .sum();
        String formattedTotalSum = String.format("%.2f", totalSum);
        model.addObject("cart", orderItemService.getCart());
        model.addObject("totalSum", formattedTotalSum);
        model.addObject("principal",principal);
        model.setViewName("cart");
        return model;
    }

    @RequestMapping(value = "/checkout")
    public ModelAndView checkout(ModelAndView model,Principal principal) {

        model.addObject("principal",principal);
        model.setViewName("checkout");
        return model;
    }
    // @RequestParam quantity <- moga da prihvana quantity kato requestparam ako go definiram
    // v href-a s (,quantity=${instance.value})
    @RequestMapping(value="/completeOrder" , method= RequestMethod.POST)
    public ModelAndView completeOrder(@RequestParam("email") String email,
                                      @RequestParam("name") String name,
                                      @RequestParam("userAddress") String userAddress,
                                      @RequestParam("phoneNumber") String phoneNumber,
                                      @RequestParam(name = "registeracc", required = false) boolean registerAcc,
                                      ModelAndView model,Principal principal){
        User user = new User();
        String randomPassword = PasswordGenerator.generatePassword();
        LocalDate currentDate = LocalDate.now();
        if (principal == null){
            String[] nameSplit = name.split("\\s+");
            if (nameSplit.length==1){
                    throw new CrudValidationException("Enter two names");
            }
            user.setUserFirstName(nameSplit[0]);
            user.setUserLastName(nameSplit[1]);
            user.setUserEmail(email);
            user.setUserAddress(userAddress);
            user.setUserPhone(phoneNumber);
            if(registerAcc){
                user.setUserRight(1);
            }else{
                user.setUserRight(0);
            }
            user.setUserPassword(passwordEncoder.encode(randomPassword));
            userService.createUser(user);

            User createdUser = userService.getRegisteredUserByEmail(email);
            if (registerAcc){
                UserRight userRight = new UserRight(createdUser.getUserId(),1);
                userRightService.createUserRight(userRight);
            }else{
                UserRight userRight = new UserRight(createdUser.getUserId(),0);
                userRightService.createUserRight(userRight);
            }
        } else {
            user = userService.getRegisteredUserByEmail(email);
        }

        OrderEntity order = new OrderEntity();
        order.setOrderStatus("Pending");
        order.setUserID(user.getUserId());


        orderService.createOrder(order);
        StringBuilder orderItemsEmail = new StringBuilder();
        Integer orderQuantity;
        double orderTotal=0.0;
        Map<OrderItem, Integer> cart = orderItemService.getCart();

        for (OrderItem item : cart.keySet()){
            item.setOrderID(order.getOrderID());
            orderItemService.createItem(item);
            orderQuantity = item.getProductQuantity();
            orderTotal+=orderQuantity*item.getProductPrice();
            orderItemsEmail.append("\n"+item.toString()+": "+orderQuantity);
        }
        if (registerAcc){
            orderItemsEmail.append("\n Your password is : "+randomPassword);
        }
        orderItemsEmail.append("\n Total amount of the order: "+orderTotal);
        orderItemsEmail.append("\n Date of the Order"+ currentDate);
        emailService.sendSimpleMail(user.getUserEmail(),orderItemsEmail);
        cart.clear();
        List<Product> listProducts = productsService.listAllProducts();
        model.addObject("listProducts", listProducts);
        model.addObject("principal",principal);
        model.setViewName("homepage");
        return model;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(ModelAndView model) {
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/orderhistory")
    public ModelAndView orderHistory(@RequestParam("userId") String userId, ModelAndView model,Principal principal){
        List<OrderItem> history = orderItemService.getHistory(userId);
        Collections.sort(history);
        for (OrderItem orderItem : history) {
            System.out.println(orderItem.getProductId());
            System.out.println(orderItem.getOrderItemId());
            System.out.println(orderItem.getProductQuantity());
            Product product = productsService.findById(orderItem.getProductId());
            orderItem.setProductName(product.getProductName());
            orderItem.setProductDesc(product.getProductDesc());
            orderItem.setProductPrice(product.getProductPrice());
        }
        model.addObject("orderhistory",history);
        model.addObject("principal",principal);
        model.setViewName("orderhistory");
        return model;
    }

}
