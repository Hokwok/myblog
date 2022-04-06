package com.kwok.controller;

import com.kwok.pojo.Blog;
import com.kwok.pojo.Tag;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TagShowController {

    @Autowired
    TagService tagService;

    @Autowired
    TypeService typeService;

    @Autowired
    BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable("id") int id, HttpServletRequest req, Model model){
        String pageIndex = req.getParameter("pageIndex");
        List<Tag> tags = tagService.listTagAndCount();
        if (id == -1){
            id = tags.get(0).getId();
        }
        // 获得blog总数(分页：上一页，下一页的情况)
        int totalCount = blogService.getBlogCountByTagId(id);

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
        Map tagsblogmap = new HashMap();
        tagsblogmap.put("currentPageNo",(currentPageNo-1)*pageSize);
        tagsblogmap.put("id",id);
        tagsblogmap.put("pageSize",pageSize);
        List<Blog> blogList = blogService.getBlogListByTagId(tagsblogmap);

        for (Blog blog : blogList) {
            int type_id = blog.getType_id();
            blog.setTypeName(typeService.getTypeById(type_id).getName());
            int bid = blog.getId();
            blog.setTags(tagService.getTagsByBlogId(bid));
        }
        // 判断上一页，下一页是否显示
        List<Blog> prePageHave = null;
        List<Blog> nextPageHave = null;
        if (blogList.size()!=0){
            int firstItemId = blogList.get(0).getId();
            int lastItemId = blogList.get(blogList.size()-1).getId();

            prePageHave = blogService.getPreBlogsByTagId(firstItemId,id);
            nextPageHave = blogService.getNextBlogsByTagId(lastItemId,id);
            if (prePageHave.size() == 0){
                prePageHave = null;
            }
            if (nextPageHave.size() == 0){
                nextPageHave = null;
            }
        }

        System.out.println("============================");
        System.out.println(tagsblogmap);
        System.out.println("totalCount:"+totalCount);
        System.out.println("totalPageCount:"+totalPageCount);

        model.addAttribute("blogs",blogList);
        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);
        model.addAttribute("currentPageNo",currentPageNo);
        model.addAttribute("tags",tags);
        model.addAttribute("activeId",id);
        return "tags";
    }

    @PostMapping("/tags/topage")
    public String searchTopage(HttpServletRequest req, HttpServletResponse resp, Model model){
        String pageIndex = req.getParameter("pageIndex");
        int id = Integer.parseInt(req.getParameter("id"));
        List<Tag> tags = tagService.listTagAndCount();
        // 获得blog总数(分页：上一页，下一页的情况)
        int totalCount = blogService.getBlogCountByTagId(id);

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
        Map tagsblogmap = new HashMap();
        tagsblogmap.put("currentPageNo",(currentPageNo-1)*pageSize);
        tagsblogmap.put("id",id);
        tagsblogmap.put("pageSize",pageSize);
        List<Blog> blogList = blogService.getBlogListByTagId(tagsblogmap);

        for (Blog blog : blogList) {
            int type_id = blog.getType_id();
            blog.setTypeName(typeService.getTypeById(type_id).getName());
            int bid = blog.getId();
            blog.setTags(tagService.getTagsByBlogId(bid));
        }
        // 判断上一页，下一页是否显示
        List<Blog> prePageHave = null;
        List<Blog> nextPageHave = null;
        if (blogList.size()!=0){
            int firstItemId = blogList.get(0).getId();
            int lastItemId = blogList.get(blogList.size()-1).getId();

            prePageHave = blogService.getPreBlogsByTagId(firstItemId,id);
            nextPageHave = blogService.getNextBlogsByTagId(lastItemId,id);
            if (prePageHave.size() == 0){
                prePageHave = null;
            }
            if (nextPageHave.size() == 0){
                nextPageHave = null;
            }
        }

        System.out.println("============================");
        System.out.println(tagsblogmap);
        System.out.println("totalCount:"+totalCount);
        System.out.println("totalPageCount:"+totalPageCount);

        model.addAttribute("blogs",blogList);
        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);
        model.addAttribute("currentPageNo",currentPageNo);
        model.addAttribute("tags",tags);
        model.addAttribute("activeId",id);
        return "tags :: blogList";
    }


}
