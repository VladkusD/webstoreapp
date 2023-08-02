//package bg.webapp.shop.service;
//
//import bg.webapp.shop.dao.UserJPARepository;
//import bg.webapp.shop.model.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final UserJPARepository userRepo;
//
//    public CustomUserDetailsService(UserJPARepository userRepo) {
//        this.userRepo = userRepo;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepo.findUserByEmail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getUserEmail())
//                .password(user.getUserPassword())
//                .roles("USER") // Replace "USER" with the appropriate role(s) for the user
//                .build();
//    }
//}