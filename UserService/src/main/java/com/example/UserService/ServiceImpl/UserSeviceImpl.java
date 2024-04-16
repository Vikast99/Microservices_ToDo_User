package com.example.UserService.ServiceImpl;

import com.example.UserService.Entity.User;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
@Service
public class UserSeviceImpl implements UserService {
   @Autowired
   private  UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserSeviceImpl.class);


@Override
    public  String createUser(User user) {
        try {
            if (!Objects.equals(user.getEmail(), "") && !Objects.equals(user.getName(), "")) {
                if (user.getEmail() != null && user.getName() != null) {
                    Optional<User> existUser = userRepository.findByemail(user.getEmail());
                    if (existUser.isPresent()) {
                        logger.warn("User already exist");
                        return "User already exist";
                    }else {
                        userRepository.save(user);
                    }

                }else {
                    logger.warn("User can not be  null");
                    return "User can not be  null";
                }

            }else {
                    logger.warn("User can not be  Empty");
                    return "User can not be  Empty";
                }
        }catch (Exception e){
            logger.error(e.toString());
        }
        return "something went wrong";
    }

    @Override
    public String deleteUser(Long id) {
        logger.info("finding the id of the User for getUserById");
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            logger.error("User ID is  not present="+id);
            return "user is not present";
        } else {
            userRepository.deleteById(id);
            logger.info("Uesr with ID " + id + " deleted successfully.");
            return "user deleted sucessfully";
        }
    }
}
