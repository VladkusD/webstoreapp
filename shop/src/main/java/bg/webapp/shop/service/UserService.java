package bg.webapp.shop.service;

import bg.webapp.shop.dao.UserJPARepository;
import bg.webapp.shop.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    UserJPARepository userRepo;

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

//    public String generatePassword(){
//        return
//    }

}
