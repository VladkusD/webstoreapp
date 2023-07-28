package bg.webapp.shop.controller;

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

    @RequestMapping(value = "/addProductToCart", method = RequestMethod.GET)
    public ModelAndView addContactToShoppingCart(HttpServletRequest request, ModelAndView model) {
        int productId = Integer.parseInt(request.getParameter("product_id"));
        Product product = productsService.findById(productId);
        List<Product> productList = productsService.findAll();
        model.addObject("productList", productList);
        model.setViewName("homepage");
        orderService.getCart().put(product, orderService.getCart().getOrDefault(product, 0) + 1);
        model.addObject("cart", orderService.getCart());

        return model;
    }



}
