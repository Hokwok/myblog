package com.kwok.service.TypeService;

import com.kwok.mapper.TypeMapper;
import com.kwok.pojo.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("TypeService")
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeMapper typeMapper;

    @Override
    public int getTypeCount() {
        return typeMapper.getTypeCount();
    }

    @Transactional
    @Override
    public int addType(Type type) {
        return typeMapper.addType(type);
    }

    @Transactional
    @Override
    public int deleteType(int id) {
        return typeMapper.deleteType(id);
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Override
    public Type getTypeById(int id) {
        return typeMapper.getTypeById(id);
    }

    @Override
    public List<Type> getTypeList(Map<String, Integer> map) {
        return typeMapper.getTypeList(map);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    public Type getLastType() {
        return typeMapper.getLastType();
    }

    @Override
    public List<Type> listType() {
        return typeMapper.listType();
    }

    @Override
    public List<Type> listTopType() {
        return typeMapper.listTopType();
    }

}
