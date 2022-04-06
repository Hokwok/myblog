package com.kwok.service.BlogService;

import com.kwok.pojo.Blog;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Repository
public interface BlogService {
    int getBlogCount();

    Blog getBlogById(int id);

    // 查询分页
    List<Blog> getBlogList(Map<String,Integer> map);

    // 搜索及翻页
    int getBlogSearchCount(Map map);

    List<Blog> getSearchBlogList(Map map);

    // 新增博客
    int saveBlog(String tag_id,Blog blog);

    String getTagIdByBlogId(int id);

    // 修改博客
    int updateBlog(String tag_id,Blog blog);

    // 更新博客浏览次数
    int updateBlogViews(int id);

    // 删除博客
    int deleteBlog(int id);

    // index页面topBlog
    List<Blog> listTopRecommendBlog();

    // 热门文章
    List<Blog> listHotBlog();

    // 根据typeid查找blog数
    int getBlogCountByTypeId(int id);

    // 根据tagid查找blog数
    int getBlogCountByTagId(int id);

    // 根据typeid查找bloglist
    List<Blog> getBlogListByTypeId(Map map);

    // 根据tagid查找bloglist
    List<Blog> getBlogListByTagId(Map map);

    // 获取并转换markdown
    Blog getConvertBlogById(int id);

    List<Blog> getPreBlogsById(int fid, int tid);

    List<Blog> getNextBlogsById(int lid, int tid);

    List<Blog> getPreBlogsByTagId(int fid, int tid);

    List<Blog> getNextBlogsByTagId(int lid, int tid);

    // 归档页面
    Map<String,List<Blog>> archiveBlog();

}
