package com.kwok.service.UserService;

import com.kwok.mapper.UserMapper;
import com.kwok.pojo.User;
import com.kwok.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        User user1 = new User();
        user1.setUsername(username);
        user1.setPassword(MD5Utils.code(password));

        User user = userMapper.findByUsernameAndPassword(user1);
        return user;
    }
}
