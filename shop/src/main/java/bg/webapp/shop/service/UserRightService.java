package bg.webapp.shop.service;


import bg.webapp.shop.dao.UserRightJPARepository;
import bg.webapp.shop.model.UserRight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRightService {

    @Autowired
    UserRightJPARepository userRightRepo;


    public void createUserRight(UserRight userRight){
        userRightRepo.save(userRight);
    }

}
