package bg.webapp.shop.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

//        auth
//            .inMemoryAuthentication()
//                .withUser("aaa").password("{noop}aaa").roles("0");



        auth.jdbcAuthentication().dataSource(dataSource)

  .usersByUsernameQuery("SELECT user_email, user_password, true FROM user WHERE user_email=?")
    .authoritiesByUsernameQuery("SELECT ur.user_id, ur.system_role FROM user_right ur INNER JOIN user u ON ur.user_id WHERE u.user_email=?");
/*     .usersByUsernameQuery("SELECT user_first_name, user_password, true FROM user WHERE user_first_name=?")
    .authoritiesByUsernameQuery("SELECT ur.user_id, ur.system_role FROM user_right ur INNER JOIN user u ON ur.user_id WHERE u.user_first_name=?");*/

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
//                .logoutSuccessUrl("/login?logout").deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.password.MessageDigestPasswordEncoder("SHA-256");
    }
}