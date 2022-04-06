package com.kwok.service.TagService;

import com.kwok.pojo.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Repository
public interface TagService {

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

    // tags页面展示tags及数量
    List<Tag> listTagAndCount();

    // index页面topTag
    List<Tag> listTopTag();

    // 博客展示获取tag
    List<Tag> getShowTags(ArrayList<Integer> tagIds);

    // 根据blogid查询tags
    List<Tag> getTagsByBlogId(int id);

}
