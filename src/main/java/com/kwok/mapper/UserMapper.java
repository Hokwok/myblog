package com.kwok.mapper;

import com.kwok.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    User findByUsernameAndPassword(User user);

}
