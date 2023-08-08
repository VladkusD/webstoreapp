package bg.webapp.shop.service;

import bg.webapp.shop.dao.UserJPARepository;
import bg.webapp.shop.exceptions.CrudValidationException;
import bg.webapp.shop.model.User;
import bg.webapp.shop.model.UserRight;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;


@Service
public class UserService {


    @Autowired
    UserJPARepository userRepo;


    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    UserRightService userRightService;

    @Transactional(Transactional.TxType.REQUIRED)
    public void createUser (User user){

        userRepo.save(user);
    }

    public List<User> listAllUsers(){
        return userRepo.findAll();
    }

    public void deleteUser(User user){
        userRepo.delete(user);
    }

    public User getRegisteredUserByEmail(String email){
        return userRepo.findUserByEmail(email);
    }

    public User getUserByFirstName(String name){
        return userRepo.findUserByFirstName(name);
    }

    public User getGuestUserOrReturnRegisteredUser (Principal principal, boolean registerAcc, String email ,
                                                    String name, String userAddress, String phoneNumber, String
                                                    randomPassword){
        User user = new User();
        if (principal == null) {
            String[] nameSplit = name.split("\\s+");
            if (nameSplit.length == 1) {
                throw new CrudValidationException("Enter two names");
            }
            user.setUserFirstName(nameSplit[0]);
            user.setUserLastName(nameSplit[1]);
            user.setUserEmail(email);
            user.setUserAddress(userAddress);
            user.setUserPhone(phoneNumber);
            if (registerAcc) {
                checkIfUserIsAlreadyRegistered(user.getUserEmail());
                user.setUserRight(1);
            } else {
                user.setUserRight(0);
            }
            user.setUserPassword(passwordEncoder.encode(randomPassword));
            createUser(user);

            User createdUser = getRegisteredUserByEmail(email);
            if (registerAcc) {
                UserRight userRight = new UserRight(createdUser.getUserId(), 1);
                userRightService.createUserRight(userRight);
            } else {
                UserRight userRight = new UserRight(createdUser.getUserId(), 0);
                userRightService.createUserRight(userRight);
            }
        } else {
            user = getRegisteredUserByEmail(email);
        }
        return user;
    }

    public void checkIfUserIsAlreadyRegistered(String email){
        if (getRegisteredUserByEmail(email)!=null){
            throw new CrudValidationException("User already registered");
        }
    }




}
