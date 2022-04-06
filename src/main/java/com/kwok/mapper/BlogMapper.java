package com.kwok.mapper;

import com.kwok.pojo.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

// 这个注解表示这是一个mybatis的mapper类
@Mapper
@Repository
public interface BlogMapper {

    int getBlogCount();

    Blog getBlogById(int id);

    // 查询分页
    List<Blog> getBlogList(Map<String,Integer> map);

    // 搜索及翻页
    int getBlogSearchCount(Map map);

    List<Blog> getSearchBlogList(Map map);

    Blog getLastBlog();

    // 增加保存blog
    int saveBlog(Blog blog);

    // 修改保存
    int updateBlog(Blog blog);

    // 更新views
    int updateBlogViews(Blog blog);

    // 删除blog
    int deleteBlog(int id);

    // index页面topBlog
    List<Blog> listTopRecommendBlog();

    // 热门文章
    List<Blog> listHotBlog();

    // 根据typeid查找blog数
    int getBlogCountByTypeId(int id);

    // 根据tagid查找blog数
    int getBlogCountByTagId(int id);

    // 根据条件分页查找bloglist
    List<Blog> getBlogListByTypeId(Map map);

    // 根据条件分页查找bloglist
    List<Blog> getBlogListByTagId(Map map);

    List<Blog> getPreBlogsById(Map map);

    List<Blog> getNextBlogsById(Map map);

    List<Blog> getPreBlogsByTagId(Map map);

    List<Blog> getNextBlogsByTagId(Map map);

    List<String> findGroupYear();

    List<Blog> findBlogByYear(String year);

}
