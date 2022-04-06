package com.kwok.service.UserService;

import com.kwok.pojo.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public interface UserService {

    User checkUser(String username,String password);

}
