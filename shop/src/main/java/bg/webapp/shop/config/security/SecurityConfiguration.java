//package bg.webapp.shop.config.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    DataSource dataSource;
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT user_first_name, user_last_name, user_password, true " +
//                        "FROM `user` " +
//                        "WHERE CONCAT(user_first_name, ' ', user_last_name) = ?")
//                .authoritiesByUsernameQuery("SELECT user_id FROM user_right WHERE user_id=?")
//                .passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/").access("hasAuthority('ADMINISTRATOR') or hasAuthority('EMPLOYEE')")
//                .antMatchers("/newContact").access("hasAuthority('ADMINISTRATOR') or hasAuthority('EMPLOYEE')")
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll(true)
//                .and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//                .and()
//                .rememberMe()
//                .rememberMeParameter("remember")
//                .rememberMeCookieName("rememberMeFruitShopCookie")
//                .tokenValiditySeconds(2419200)
//                .key("privatekey")
//                .and().exceptionHandling().accessDeniedPage("/unauthorized");
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256");
//    }
//
//}

