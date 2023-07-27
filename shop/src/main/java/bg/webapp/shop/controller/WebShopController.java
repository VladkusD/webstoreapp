package bg.webapp.shop.controller;

import bg.webapp.shop.model.Product;
import bg.webapp.shop.service.OrderItemService;
import bg.webapp.shop.service.OrderService;
import bg.webapp.shop.service.ProductsService;
import bg.webapp.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

}
