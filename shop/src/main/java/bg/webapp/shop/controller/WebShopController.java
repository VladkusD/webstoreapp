package bg.webapp.shop.controller;

import bg.webapp.shop.model.OrderItem;
import bg.webapp.shop.model.Product;
import bg.webapp.shop.service.OrderItemService;
import bg.webapp.shop.service.OrderService;
import bg.webapp.shop.service.ProductsService;
import bg.webapp.shop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    OrderService orderService;
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
        List<Product> listProducts = productsService.findBySearchTerm(condition,condition);
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
        System.out.println(orderItemQuantity);
// no need to add when using REDIRECT !!!!!!
//        List<Product> listProducts = productsService.listAllProducts();
//        model.addObject("listProducts", listProducts);
        OrderItem orderItem = new OrderItem(product,orderItemQuantity);

        // Now you have the productId and orderItemQuantity, and you can process adding to the cart as needed.
        orderService.getCart().put(orderItem, orderItemQuantity);
        model.addObject("cart", orderService.getCart());
        model.setViewName("redirect:/");

        return model;
    }




}
