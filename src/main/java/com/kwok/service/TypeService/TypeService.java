package com.kwok.service.TypeService;

import com.kwok.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Repository
public interface TypeService {

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

    // blog中选择框动态获取
    List<Type> listType();

    // index页面topType
    List<Type> listTopType();

}
