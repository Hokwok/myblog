package com.kwok.mapper;

import com.kwok.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 这个注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface TagMapper {

    int getTagCount();

    int addTag(Tag tag);

    int deleteTag(int id);

    int updateTag(Tag tag);

    Tag getTagById(int id);

    // 查询分页
    List<Tag> getTagList(Map<String,Integer> map);

    // 验证是否已有该类名
    Tag getTagByName(String name);

    Tag getLastTag();

    // blog中选择框动态获取
    List<Tag> listTag();

    // 获取tag的blog数目
    int tagCount(int tid);

    // index页面topTag
    List<Tag> listTopTag();

    // 博客展示获取tag
    List<Tag> getShowTags(List<Integer> tagIds);

    // 根据blogid查询tags
    List<Tag> getTagsByBlogId(int id);

}
