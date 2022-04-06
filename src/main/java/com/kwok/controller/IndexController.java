package com.kwok.controller;

import com.kwok.pojo.Blog;
import com.kwok.pojo.Tag;
import com.kwok.pojo.Type;
import com.kwok.service.BlogService.BlogService;
import com.kwok.service.TagService.TagService;
import com.kwok.service.TypeService.TypeService;
import com.kwok.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {


    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    @GetMapping("/")
    public String enter(Model model){
        List<Blog> hotblogList = blogService.listHotBlog();
        if (hotblogList.size()>3){
            model.addAttribute("hotblogList",hotblogList.subList(0,3));
        }else {
            model.addAttribute("hotblogList",hotblogList);
        }
        return "enter";
    }

    @GetMapping("/index")
    public String index(HttpServletRequest req, HttpServletResponse resp, Model model){
        String pageIndex = req.getParameter("pageIndex");
        // 获得blog总数(分页：上一页，下一页的情况)
        int totalCount = blogService.getBlogCount();

        // 第一次走这个请求，一定是第一页，页面大小固定
        int pageSize = 5; // 可以把这个写在配置文件中，方便后期修改
        int currentPageNo = 1;

        if (pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        // 总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        int totalPageCount = ((int)(totalCount/pageSize))+1; // 总共有几页

        // 控制首页和尾页
        // 如果页面小于1了，就显示第一页数据
        if (totalPageCount<1){
            currentPageNo = 1;
        }else if (currentPageNo > totalPageCount){
            // 当前页面大于最后一页
            currentPageNo = totalPageCount;
        }

        // 获取blog列表展示
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("currentPageNo",(currentPageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Blog> blogList = blogService.getBlogList(map);

        // 判断上一页，下一页是否显示
        int firstItemId = blogList.get(0).getId();
        int lastItemId = blogList.get(blogList.size()-1).getId();
        Blog prePageHave = blogService.getBlogById(firstItemId+1);
        Blog nextPageHave = blogService.getBlogById(lastItemId-1);

        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);
        model.addAttribute("currentPageNo",currentPageNo);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("blogs",blogList);
        List<Tag> tagTops = tagService.listTopTag();
        if (tagTops.size()>8){
            model.addAttribute("tags",tagTops.subList(0,8));
        }else {
            model.addAttribute("tags",tagTops);
        }
        List<Type> typeTops = typeService.listTopType();
        if (typeTops.size()>6){
            model.addAttribute("types",typeTops.subList(0,6));
        }else {
            model.addAttribute("types",typeTops);
        }
        List<Blog> blogTops = blogService.listTopRecommendBlog();
        if (blogTops.size()>6){
            model.addAttribute("recommendBlogs",blogTops.subList(0,6));
        }else {
            model.addAttribute("recommendBlogs",blogTops);
        }
        return "index";
    }

    @PostMapping("/blogs/topage")
    public String topage(HttpServletRequest req, HttpServletResponse resp, Model model){
        String pageIndex = req.getParameter("pageIndex");
        // 获得blog总数(分页：上一页，下一页的情况)
        int totalCount = blogService.getBlogCount();

        // 第一次走这个请求，一定是第一页，页面大小固定
        int pageSize = 5; // 可以把这个写在配置文件中，方便后期修改
        int currentPageNo = 1;

        if (pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        // 总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        int totalPageCount = ((int)(totalCount/pageSize))+1; // 总共有几页

        // 控制首页和尾页
        // 如果页面小于1了，就显示第一页数据
        if (totalPageCount<1){
            currentPageNo = 1;
        }else if (currentPageNo > totalPageCount){
            // 当前页面大于最后一页
            currentPageNo = totalPageCount;
        }

        // 获取blog列表展示
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("currentPageNo",(currentPageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Blog> blogList = blogService.getBlogList(map);

        // 判断上一页，下一页是否显示
        int firstItemId = blogList.get(0).getId();
        int lastItemId = blogList.get(blogList.size()-1).getId();
        Blog prePageHave = blogService.getBlogById(firstItemId+1);
        Blog nextPageHave = blogService.getBlogById(lastItemId-1);
        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);
        model.addAttribute("currentPageNo",currentPageNo);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("blogs",blogList);
        return "index :: blogList";
    }

    @PostMapping("/search")
    public String search(HttpServletRequest req, HttpServletResponse resp, Model model){
        String query = req.getParameter("query");
        String pageIndex = req.getParameter("pageIndex");
        Map searchmap = new HashMap();
        if (query!=null && !query.equals("")){
            searchmap.put("title",query);
        }

        // 获得blog总数(分页：上一页，下一页的情况)
        int totalCount = blogService.getBlogSearchCount(searchmap);

        // 第一次走这个请求，一定是第一页，页面大小固定
        int pageSize = 5; // 可以把这个写在配置文件中，方便后期修改
        int currentPageNo = 1;

        if (pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        // 总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        int totalPageCount = ((int)(totalCount/pageSize))+1; // 总共有几页

        // 控制首页和尾页
        // 如果页面小于1了，就显示第一页数据
        if (totalPageCount<1){
            currentPageNo = 1;
        }else if (currentPageNo > totalPageCount){
            // 当前页面大于最后一页
            currentPageNo = totalPageCount;
        }

        // 获取blog列表展示
        searchmap.put("currentPageNo",(currentPageNo-1)*pageSize);
        searchmap.put("pageSize",pageSize);
        List<Blog> blogList = blogService.getSearchBlogList(searchmap);

        // 判断上一页，下一页是否显示
        Blog prePageHave = null;
        Blog nextPageHave = null;
        if (blogList.size()!=0){
            int firstItemId = blogList.get(0).getId();
            int lastItemId = blogList.get(blogList.size()-1).getId();
            prePageHave = blogService.getBlogById(firstItemId+1);
            nextPageHave = blogService.getBlogById(lastItemId-1);
        }

        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);

        model.addAttribute("totalCount",totalCount);
        model.addAttribute("blogs",blogList);
        model.addAttribute("currentPageNo",currentPageNo);
        model.addAttribute("query",query);
        return "search";
    }

    @PostMapping("/search/topage")
    public String searchTopage(HttpServletRequest req, HttpServletResponse resp, Model model){
        String query = req.getParameter("query");
        String pageIndex = req.getParameter("pageIndex");
        Map searchmap = new HashMap();
        if (query!=null && !query.equals("")){
            searchmap.put("title",query);
        }

        // 获得blog总数(分页：上一页，下一页的情况)
        int totalCount = blogService.getBlogSearchCount(searchmap);

        // 第一次走这个请求，一定是第一页，页面大小固定
        int pageSize = 5; // 可以把这个写在配置文件中，方便后期修改
        int currentPageNo = 1;

        if (pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        // 总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        int totalPageCount = ((int)(totalCount/pageSize))+1; // 总共有几页

        // 控制首页和尾页
        // 如果页面小于1了，就显示第一页数据
        if (totalPageCount<1){
            currentPageNo = 1;
        }else if (currentPageNo > totalPageCount){
            // 当前页面大于最后一页
            currentPageNo = totalPageCount;
        }

        // 获取blog列表展示
        searchmap.put("currentPageNo",(currentPageNo-1)*pageSize);
        searchmap.put("pageSize",pageSize);
        List<Blog> blogList = blogService.getSearchBlogList(searchmap);

        // 判断上一页，下一页是否显示
        Blog prePageHave = null;
        Blog nextPageHave = null;
        if (blogList.size()!=0){
            int firstItemId = blogList.get(0).getId();
            int lastItemId = blogList.get(blogList.size()-1).getId();
            prePageHave = blogService.getBlogById(firstItemId+1);
            nextPageHave = blogService.getBlogById(lastItemId-1);
        }

        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("blogs",blogList);
        model.addAttribute("currentPageNo",currentPageNo);
        model.addAttribute("query",query);
        return "search :: blogList";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") int id, Model model){
        // 更新博客浏览次数
        blogService.updateBlogViews(id);

        model.addAttribute("blog",blogService.getConvertBlogById(id));
        String tag_ids = blogService.getTagIdByBlogId(id);
        String[] split = tag_ids.split("\\,");
        ArrayList<Integer> tagIds = new ArrayList<Integer>();
        for (String s : split) {
            tagIds.add(Integer.parseInt(s));
        }
        List<Tag> showTagsList = tagService.getShowTags(tagIds);
        model.addAttribute("tags",showTagsList);
        return "blog";
    }

}
