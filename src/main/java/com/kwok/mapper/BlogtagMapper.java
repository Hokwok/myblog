package com.kwok.mapper;

import com.kwok.pojo.Blogtag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// 这个注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface BlogtagMapper {

    Blogtag getLastBlogtag();

    // 增加保存blog
    int saveBlogtag(Blogtag blogtag);

    List<Blogtag> getTagIdByBlogId(int id);

    int deletePreBlogtag(int blogId);

}
