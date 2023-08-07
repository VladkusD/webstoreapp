package bg.webapp.shop.exceptions;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CrudValidationException.class, RuntimeException.class})
    public ModelAndView erroneousInput(CrudValidationException ex,Principal principal){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("checkout");
        modelAndView.addObject("principal",principal);
        modelAndView.addObject("notification", ex.getMessage());
        return modelAndView;
    }
}
