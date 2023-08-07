package bg.webapp.shop.controller;

import bg.webapp.shop.model.*;
import bg.webapp.shop.service.*;
import bg.webapp.shop.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.security.Principal;
import java.util.List;

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
    PasswordGenerator passwordGenerator;

    @Autowired
    UserRightService userRightService;

    @RequestMapping(value = "/")
    public ModelAndView listProducts(ModelAndView model, Principal principal) {

        findPaginated(1, model, principal);
        model.addObject("principal", principal);
        //model.setViewName("homepage"); <- pre4i na paginationa ako se dobavq tuk
        if (orderItemService.getCart().size() > 0) {
            model.addObject("cart", orderItemService.getCart());
        }
        if (principal != null) {

            User loggedUser = userService.getRegisteredUserByEmail(principal.getName());
            System.out.println(loggedUser.getUserEmail());
            System.out.println(loggedUser.getUserId());
            System.out.println(loggedUser.getUserFirstName());
            model.addObject("userId", loggedUser.getUserId());

        } else {
            System.out.println("not logged user");
        }
        return model;
    }

    @GetMapping(value = "/page/{pageNo}")
    public ModelAndView findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                      ModelAndView model, Principal principal) {
        int pageSize = 6;
        Page<Product> page = productsService.findPaginated(pageNo, pageSize);
        List<Product> listProducts = page.getContent();
        model.addObject("currentPage", pageNo);
        model.addObject("totalPages", page.getTotalPages());
        model.addObject("totalItems", page.getTotalElements());
        model.addObject("listProducts", listProducts);
        model.addObject("principal", principal);
        model.setViewName("homepage");
        return model;
    }

    ;

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public ModelAndView findContacts(@RequestParam("condition") String condition, ModelAndView model, Principal principal) {
        List<Product> listProducts = productsService.findBySearchTerm(condition, condition);
        model.addObject("listProducts", listProducts);
        model.addObject("condition", condition);
        model.addObject("principal", principal);
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

    @RequestMapping(value = "/addProductToCart/{productId}", method = RequestMethod.POST)
    public ModelAndView addProductToCart(@PathVariable Integer productId,
                                         @RequestParam("orderItemQuantity") Integer orderItemQuantity,
                                         ModelAndView model, Principal principal) {
        List<Product> listProducts = productsService.listAllProducts();
        model.addObject("listProducts", listProducts);
        NotificationAlert itemAddedToCart = orderItemService.addItemToCartById(productId, orderItemQuantity);
        model.addObject("cart", orderItemService.getCart());
        model.addObject("notification", itemAddedToCart);
        model.addObject("principal", principal);
        model.setViewName("homepage");

        return model;
    }

    @GetMapping("/cart/decrement/{productId}")

    public String decrementQuantity(@PathVariable Integer productId) {
        orderItemService.decrementItemQuantityInCart(productId);
        return "redirect:/cart";
    }

    @GetMapping("/cart/increment/{productId}")
    public String incrementQuantity(@PathVariable Integer productId) {
        orderItemService.incrementItemQuantityInCart(productId);
        return "redirect:/cart";
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public ModelAndView showCart(ModelAndView model, Principal principal) {
        String totalSumInCart = orderItemService.getItemsInCartTotalSum();
        model.addObject("cart", orderItemService.getCart());
        model.addObject("totalSum", totalSumInCart);
        model.addObject("principal", principal);
        model.setViewName("cart");
        return model;
    }

    @RequestMapping(value = "/checkout")
    public ModelAndView checkout(ModelAndView model, Principal principal) {

        model.addObject("principal", principal);
        model.setViewName("checkout");
        return model;
    }

    @RequestMapping(value = "/completeOrder", method = RequestMethod.POST)
    public ModelAndView completeOrder(@RequestParam("email") String email,
                                      @RequestParam("name") String name,
                                      @RequestParam("userAddress") String userAddress,
                                      @RequestParam("phoneNumber") String phoneNumber,
                                      @RequestParam(name = "registeracc", required = false) boolean registerAcc,
                                      ModelAndView model, Principal principal) {

        String randomPassword = PasswordGenerator.generatePassword();
        User user = userService.getGuestUserOrReturnRegisteredUser(principal,registerAcc,email,
                                                                       name,userAddress,phoneNumber,randomPassword);

        StringBuilder orderItemsEmail = orderService.createNewOrder(user,registerAcc,randomPassword);
        emailService.sendSimpleMail(user.getUserEmail(), orderItemsEmail);
        List<Product> listProducts = productsService.listAllProducts();
        model.addObject("listProducts", listProducts);
        model.addObject("principal", principal);
        model.setViewName("homepage");
        return model;
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(ModelAndView model) {
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/orderhistory")
    public ModelAndView orderHistory(@RequestParam("userId") String userId, ModelAndView model, Principal principal) {
        List<OrderItem> history = orderItemService.getOrderHistoryList(userId);
        model.addObject("orderhistory", history);
        model.addObject("principal", principal);
        model.setViewName("orderhistory");
        return model;
    }

}
