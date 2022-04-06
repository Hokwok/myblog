package com.kwok.controller;

import com.kwok.pojo.Blog;
import com.kwok.pojo.Type;
import com.kwok.service.BlogService.BlogService;
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
public class TypeShowController {

    @Autowired
    TypeService typeService;

    @Autowired
    BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable("id") int id, HttpServletRequest req, Model model){
        List<Type> types = typeService.listType();
        for (Type type : types) {
            type.setNum(blogService.getBlogCountByTypeId(type.getId()));
        }
        String pageIndex = req.getParameter("pageIndex");
        Map typesblogmap = new HashMap();
        if (id == -1){
            id = types.get(0).getId();
        }
        // 获得blog总数(分页：上一页，下一页的情况)
        int totalCount = blogService.getBlogCountByTypeId(id);

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
        typesblogmap.put("currentPageNo",(currentPageNo-1)*pageSize);
        typesblogmap.put("id",id);
        typesblogmap.put("pageSize",pageSize);
        List<Blog> blogList = blogService.getBlogListByTypeId(typesblogmap);

        /*System.out.println("++++++++++++++++++++");
        System.out.println("totalCount:"+totalCount);
        System.out.println("typeid:"+id);
        System.out.println("types:"+types);
        System.out.println(typesblogmap);
        for (Blog blog : blogList) {
            System.out.println(blog.getTitle());
        }*/

        // 判断上一页，下一页是否显示
        List<Blog> prePageHave = null;
        List<Blog> nextPageHave = null;
        if (blogList.size()!=0){
            int firstItemId = blogList.get(0).getId();
            int lastItemId = blogList.get(blogList.size()-1).getId();
            prePageHave = blogService.getPreBlogsById(firstItemId,id);
            nextPageHave = blogService.getNextBlogsById(lastItemId,id);
            if (prePageHave.size() == 0){
                prePageHave = null;
            }
            if (nextPageHave.size() == 0){
                nextPageHave = null;
            }
        }

        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("blogs",blogList);
        model.addAttribute("currentPageNo",currentPageNo);

        model.addAttribute("types",types);
        model.addAttribute("activeId",id);
        return "types";
    }

    @PostMapping("/types/topage")
    public String searchTopage(HttpServletRequest req, HttpServletResponse resp, Model model){
        String pageIndex = req.getParameter("pageIndex");
        int id = Integer.parseInt(req.getParameter("id"));

        List<Type> types = typeService.listType();
        for (Type type : types) {
            type.setNum(blogService.getBlogCountByTypeId(type.getId()));
        }
        Map typesblogmap = new HashMap();
        // 获得blog总数(分页：上一页，下一页的情况)
        int totalCount = blogService.getBlogCountByTypeId(id);

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
        typesblogmap.put("currentPageNo",(currentPageNo-1)*pageSize);
        typesblogmap.put("id",id);
        typesblogmap.put("pageSize",pageSize);
        List<Blog> blogList = blogService.getBlogListByTypeId(typesblogmap);

        /*System.out.println("++++++++++++++++++++");
        System.out.println("totalCount:"+totalCount);
        System.out.println("typeid:"+id);
        System.out.println("types:"+types);
        System.out.println(typesblogmap);
        for (Blog blog : blogList) {
            System.out.println(blog.getTitle());
        }*/

        // 判断上一页，下一页是否显示
        List<Blog> prePageHave = null;
        List<Blog> nextPageHave = null;
        if (blogList.size()!=0){
            int firstItemId = blogList.get(0).getId();
            int lastItemId = blogList.get(blogList.size()-1).getId();
            prePageHave = blogService.getPreBlogsById(firstItemId,id);
            nextPageHave = blogService.getNextBlogsById(lastItemId,id);
            if (prePageHave.size() == 0){
                prePageHave = null;
            }
            if (nextPageHave.size() == 0){
                nextPageHave = null;
            }
        }

        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("blogs",blogList);
        model.addAttribute("currentPageNo",currentPageNo);

        model.addAttribute("types",types);
        model.addAttribute("activeId",id);
        return "types :: blogList";
    }

}
