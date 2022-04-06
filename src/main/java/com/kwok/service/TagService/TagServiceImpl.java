package com.kwok.service.TagService;

import com.kwok.mapper.TagMapper;
import com.kwok.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("TagService")
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public int getTagCount() {
        return tagMapper.getTagCount();
    }

    @Transactional
    @Override
    public int addTag(Tag tag) {
        return tagMapper.addTag(tag);
    }

    @Transactional
    @Override
    public int deleteTag(int id) {
        return tagMapper.deleteTag(id);
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }

    @Override
    public Tag getTagById(int id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public List<Tag> getTagList(Map<String, Integer> map) {
        return tagMapper.getTagList(map);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public Tag getLastTag() {
        return tagMapper.getLastTag();
    }

    @Override
    public List<Tag> listTag() {
        return tagMapper.listTag();
    }

    @Override
    public List<Tag> listTagAndCount() {
        List<Tag> tags = tagMapper.listTag();
        for (Tag tag : tags) {
            int tid = tag.getId();
            int num = tagMapper.tagCount(tid);
            tag.setNum(num);
        }
        return tags;
    }

    @Override
    public List<Tag> listTopTag() {
        return tagMapper.listTopTag();
    }

    @Override
    public List<Tag> getShowTags(ArrayList<Integer> tagIds) {
        return tagMapper.getShowTags(tagIds);
    }

    @Override
    public List<Tag> getTagsByBlogId(int id) {
        return tagMapper.getTagsByBlogId(id);
    }
}
