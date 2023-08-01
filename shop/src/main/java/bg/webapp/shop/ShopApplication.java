//package bg.webapp.shop;
//
//import bg.webapp.shop.config.mvc.MvcConfig;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRegistration;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;
//
//@SpringBootApplication
//public class ShopApplication implements WebApplicationInitializer {
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
//		appContext.register(MvcConfig.class);
//
//		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
//				"SpringDispatcher", new DispatcherServlet(appContext));
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");
//
//	}
//}
package bg.webapp.shop;
//import bg.webapp.shop.config.mvc.MvcConfig;
//import bg.webapp.shop.config.security.SecurityConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import(MvcConfig.class)
//@Import(SecurityConfiguration.class)
public class ShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}
}
