package com.kwok.service.BlogService;

import com.kwok.NotFoundException;
import com.kwok.mapper.BlogMapper;
import com.kwok.mapper.BlogtagMapper;
import com.kwok.pojo.Blog;
import com.kwok.pojo.Blogtag;
import com.kwok.util.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("BlogService")
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    BlogtagMapper blogtagMapper;

    @Override
    public int getBlogCount() {
        return blogMapper.getBlogCount();
    }

    @Override
    public Blog getBlogById(int id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public List<Blog> getBlogList(Map<String, Integer> map) {
        return blogMapper.getBlogList(map);
    }

    // 搜索及翻页
    @Override
    public int getBlogSearchCount(Map map) {
        return blogMapper.getBlogSearchCount(map);
    }

    @Override
    public List<Blog> getSearchBlogList(Map map) {
        return blogMapper.getSearchBlogList(map);
    }

    @Transactional
    @Override
    public int saveBlog(String tag_id,Blog blog) {

        Blog lastBlog = blogMapper.getLastBlog();
        if (lastBlog!=null){
            int id = lastBlog.getId();
            blog.setId(id+1);
        }else {
            blog.setId(1);
        }

        blog.setCreate_time(new Date());
        blog.setUpdate_time(new Date());
        blog.setViews(0);
        String[] split = tag_id.split("\\,");
        for (String s : split) {
            Blogtag blogtag = new Blogtag();
            Blogtag lastBlogtag = blogtagMapper.getLastBlogtag();
            if (lastBlogtag!=null){
                int id = lastBlogtag.getId();
                blogtag.setId(id+1);
            }else {
                blogtag.setId(1);
            }
            blogtag.setTag_id(Integer.parseInt(s));
            blogtag.setBlog_id(blog.getId());
            blogtagMapper.saveBlogtag(blogtag);
        }
        int result = blogMapper.saveBlog(blog);
        return result;
    }

    @Override
    public String getTagIdByBlogId(int id) {
        List<Blogtag> blogtags = blogtagMapper.getTagIdByBlogId(id);
        String tag_ids = blogtagsToIds(blogtags);
        return tag_ids;
    }

    // 1,2,3(封装成方法)
    public String blogtagsToIds(List<Blogtag> blogtags){
        if (!blogtags.isEmpty()){
            StringBuffer ids = new StringBuffer();
            Boolean flag = false;
            for (Blogtag blogtag : blogtags){
                if (flag){
                    ids.append(",");
                }else {
                    flag = true;
                }
                ids.append(blogtag.getTag_id());
            }
            return ids.toString();
        }else {
            return null;
        }
    }

    @Transactional
    @Override
    public int updateBlog(String tag_id, Blog blog) {
        blog.setUpdate_time(new Date());
        // 删掉以前对应的blogtag数据，再重新增加
        blogtagMapper.deletePreBlogtag(blog.getId());
        String[] split = tag_id.split("\\,");
        for (String s : split) {
            Blogtag blogtag = new Blogtag();
            Blogtag lastBlogtag = blogtagMapper.getLastBlogtag();
            if (lastBlogtag!=null){
                int id = lastBlogtag.getId();
                blogtag.setId(id+1);
            }else {
                blogtag.setId(1);
            }
            blogtag.setTag_id(Integer.parseInt(s));
            blogtag.setBlog_id(blog.getId());
            blogtagMapper.saveBlogtag(blogtag);
        }
        int result = blogMapper.updateBlog(blog);
        return result;
    }

    @Transactional
    @Override
    public int updateBlogViews(int id) {
        Blog currentBlog = blogMapper.getBlogById(id);
        Integer views = currentBlog.getViews();
        Blog updateblog = new Blog();
        updateblog.setId(id);
        updateblog.setViews(views + 1);
        int i = blogMapper.updateBlogViews(updateblog);
        return i;
    }

    @Transactional
    @Override
    public int deleteBlog(int id) {
        int a = blogMapper.deleteBlog(id);
        int b = blogtagMapper.deletePreBlogtag(id);
        if (a!=0 && b!=0){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public List<Blog> listTopRecommendBlog() {
        return blogMapper.listTopRecommendBlog();
    }

    @Override
    public List<Blog> listHotBlog() {
        return blogMapper.listHotBlog();
    }

    @Override
    public int getBlogCountByTypeId(int id) {
        return blogMapper.getBlogCountByTypeId(id);
    }

    @Override
    public int getBlogCountByTagId(int id) {
        return blogMapper.getBlogCountByTagId(id);
    }

    @Override
    public List<Blog> getBlogListByTypeId(Map map) {
        return blogMapper.getBlogListByTypeId(map);
    }

    @Override
    public List<Blog> getBlogListByTagId(Map map) {
        return blogMapper.getBlogListByTagId(map);
    }

    @Override
    public Blog getConvertBlogById(int id) {
        Blog blog = blogMapper.getBlogById(id);
        if (blog == null){
            throw new NotFoundException("该博客不存在!");
        }
        String content = blog.getContent();
        String contentConvert = MarkdownUtils.markdownToHtmlExtensions(content);
        blog.setContent(contentConvert);
        return blog;
    }

    @Override
    public List<Blog> getPreBlogsById(int fid, int tid) {
        Map tempids = new HashMap();
        tempids.put("fid",fid);
        tempids.put("tid",tid);
        return blogMapper.getPreBlogsById(tempids);
    }

    @Override
    public List<Blog> getNextBlogsById(int lid, int tid) {
        Map tempids = new HashMap();
        tempids.put("lid",lid);
        tempids.put("tid",tid);
        return blogMapper.getNextBlogsById(tempids);
    }

    @Override
    public List<Blog> getPreBlogsByTagId(int fid, int tid) {
        Map tempids = new HashMap();
        tempids.put("fid",fid);
        tempids.put("tid",tid);
        return blogMapper.getPreBlogsByTagId(tempids);
    }

    @Override
    public List<Blog> getNextBlogsByTagId(int lid, int tid) {
        Map tempids = new HashMap();
        tempids.put("lid",lid);
        tempids.put("tid",tid);
        return blogMapper.getNextBlogsByTagId(tempids);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year,blogMapper.findBlogByYear(year));
        }
        return map;
    }

}
