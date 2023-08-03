package bg.webapp.shop.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
            .inMemoryAuthentication()
                .withUser("aaa").password("{noop}aaa").roles("admin");


//
//        auth.jdbcAuthentication().dataSource(dataSource)
//
//                .usersByUsernameQuery("SELECT user_email, user_password, true FROM user WHERE user_email=?")
//                .authoritiesByUsernameQuery("SELECT user_id, system_role FROM user_right WHERE user_id=?");
//            .usersByUsernameQuery("SELECT user_first_name, user_password, true FROM user WHERE user_first_name=?")
//             .authoritiesByUsernameQuery("SELECT user_id,system_role FROM user_right WHERE user_id=?");

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .antMatchers("/login")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll(true)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .rememberMe()
                .tokenValiditySeconds(2419200)
                .key("privatekey")
                .and().exceptionHandling().accessDeniedPage("/unauthorized");
//                //.antMatchers("/").hasRole("ADMINISTRATOR")
//
//                .antMatchers("/").access("hasAuthority('ADMINISTRATOR')" +
//                        " or hasAuthority('EMPLOYEE')")
//                //.antMatchers("/newContact").hasRole("ADMINISTRATOR")
//                .antMatchers("/newContact").access("hasAuthority('ADMINISTRATOR')" +
//                        " or hasAuthority('EMPLOYEE')")
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll(true)
//                .and()
//                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//                .and()
//                .rememberMe()
//                .tokenValiditySeconds(2419200)
//                .key("privatekey")
//                .and().exceptionHandling().accessDeniedPage("/unauthorized");

    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256");
//    }
}



//package bg.webapp.shop.config.security;

//import bg.webapp.shop.dao.UserJPARepository;
////import bg.webapp.shop.service.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration  {
//
//    private final CustomUserDetailsService userDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    public SecurityConfiguration(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.userDetailsService = userDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
//    }
//
//
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .requestMatchers("/cart").denyAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/cart", true)
//                .and()
//                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}

//@Configuration
//@EnableWebSecurity
//
//public class SecurityConfiguration {
//
//    @Autowired
//    UserJPARepository userRepo;
//        @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .requestMatchers("/cart").denyAll()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/cart", true);
//
//        return http.build();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth
//            .inMemoryAuthentication()
//                .withUser("admin").password("{noop}admin");
//
//
//    }
//
//
//}

//
//        auth.jdbcAuthentication().dataSource(userRepo)
//                .usersByUsernameQuery("SELECT User_Name, Password, true FROM user WHERE User_Name=?")
//                .authoritiesByUsernameQuery("SELECT UserName, GroupName FROM user_rights_view WHERE UserName=?");

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .requestMatchers("/cart").denyAll() // Deny access to "/cart"
//                .anyRequest().permitAll() // Permit all other requests without authentication
//                .and()
//                .formLogin()
//                .loginPage("/login") // Set custom login page
//                .and()
//                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); // Configure CSRF token repository
//
//        return http.build();
//    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .requestMatchers("/cart").denyAll()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin().loginPage("/login")
//                .and()
//                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//
//        return http.build();
//    }