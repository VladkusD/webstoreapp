package bg.webapp.shop.controller;

import bg.webapp.shop.model.OrderItem;
import bg.webapp.shop.model.Product;
import bg.webapp.shop.service.OrderItemService;
import bg.webapp.shop.service.ProductsService;
import bg.webapp.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.io.*;
import java.security.Principal;
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

    @RequestMapping(value = "/")
    public ModelAndView listProducts(ModelAndView model) {
        List<Product> listProducts = productsService.listAllProducts();
        model.addObject("listProducts", listProducts);
        model.setViewName("homepage");
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
//        List<Product> listProducts = productsService.listAllProducts();
//        model.addObject("listProducts", listProducts);
        OrderItem orderItem = new OrderItem(product, orderItemQuantity);
        orderItem.setProductName(product.getProductName());
        orderItem.setProductDesc(product.getProductDesc());
        orderItem.setProductPrice(product.getProductPrice());
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
        model.setViewName("redirect:/");

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
        //emailService.sendSimpleMail();
        return model;
    }


}
