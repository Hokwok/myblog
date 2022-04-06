package com.kwok.mapper;

import com.kwok.pojo.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

// 这个注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface TypeMapper {

    int getTypeCount();

    int addType(Type type);

    int deleteType(int id);

    int updateType(Type type);

    Type getTypeById(int id);

    // 查询分页
    List<Type> getTypeList(Map<String,Integer> map);

    // 验证是否已有该类名
    Type getTypeByName(String name);

    Type getLastType();

    List<Type> listType();

    List<Type> listTopType();

}
