package com.kwok.controller.admin;

import com.kwok.pojo.Blog;
import com.kwok.pojo.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    @GetMapping("/blogs")
    public String blogs(HttpServletRequest req, HttpServletResponse resp, Model model){

        String pageIndex = req.getParameter("pageIndex");
        // 获得type总数(分页：上一页，下一页的情况)
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

        model.addAttribute("totalCount",totalCount);
        model.addAttribute("blogs",blogList);
        model.addAttribute("currentPageNo",currentPageNo);
        model.addAttribute("types",typeService.listType());
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(HttpServletRequest req, HttpServletResponse resp, Model model){
        String title = req.getParameter("titlesearch");
        String type_id = req.getParameter("typesearch");
        String recommend = req.getParameter("recommend");
        String pageIndex = req.getParameter("pageIndex");
        Map searchmap = new HashMap();
        if (title!=null && !title.equals("")){
            searchmap.put("title",title);
        }
        if (type_id!=null && !type_id.equals("")){
            searchmap.put("type_id",Integer.parseInt(type_id));
        }
        int searchRecommend = 0;
        if (recommend.equals("true")){
            searchRecommend = 1;
        }
        searchmap.put("recommend",searchRecommend);

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
        model.addAttribute("titlesearch",title);
        model.addAttribute("typesearch",type_id);
        model.addAttribute("recommend",recommend);
        model.addAttribute("types",typeService.listType());
        return "admin/blogs :: blogList";
    }

    @PostMapping("/blogs/topage")
    public String topage(HttpServletRequest req, HttpServletResponse resp, Model model){

        String title = req.getParameter("titlesearch");
        String type_id = req.getParameter("typesearch");
        String recommend = req.getParameter("recommend");
        String pageIndex = req.getParameter("pageIndex");
        Map searchmap = new HashMap();
        if (title!=null && !title.equals("")){
            searchmap.put("title",title);
        }
        if (type_id!=null && !type_id.equals("")){
            searchmap.put("type_id",Integer.parseInt(type_id));
        }
        int searchRecommend = 0;
        if (recommend.equals("true")){
            searchRecommend = 1;
            searchmap.put("recommend",searchRecommend);
        }else if (recommend.equals("false")){
            searchmap.put("recommend",searchRecommend);
        }

        // 获得查询blog总数(分页：上一页，下一页的情况)
        int totalCount = blogService.getBlogSearchCount(searchmap);

        // 第一次走这个请求，一定是第一页，页面大小固定
        int pageSize = 5; // 可以把这个写在配置文件中，方便后期修改
        int currentPageNo = 2;

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
        model.addAttribute("titlesearch",title);
        model.addAttribute("typesearch",type_id);
        model.addAttribute("recommend",recommend);
        model.addAttribute("types",typeService.listType());
        return "admin/blogs :: blogList";
    }

    // 添加blog
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    @PostMapping("/blogs/input")
    public String post(Blog blog,String tag_id, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setUser_id(blog.getUser().getId());
        System.out.println(blog);
        int i = blogService.saveBlog(tag_id, blog);
        if (i==1){
            attributes.addFlashAttribute("message","操作博客成功");
        } else {
            attributes.addFlashAttribute("message","操作博客失败");
        }
        return REDIRECT_LIST;
    }

    // 跳转到编辑blog
    @GetMapping("/blogs/edit/{id}")
    public String editTag(@PathVariable("id") int id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",blogService.getBlogById(id));
        String tag_ids = blogService.getTagIdByBlogId(id);
        model.addAttribute("tag_id",tag_ids);
        return "admin/blogs-update";
    }

    // 编辑blog保存
    @PostMapping("/blogs/edit")
    public String saveEditBlog(Blog blog,String tag_id, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setUser_id(blog.getUser().getId());
        int i = blogService.updateBlog(tag_id, blog);
        if (i==1){
            attributes.addFlashAttribute("message","修改博客成功");
        } else {
            attributes.addFlashAttribute("message","修改博客失败");
        }
        return REDIRECT_LIST;
    }

    // 删除blog
    @GetMapping("/blogs/delete/{id}")
    public String deleteBlog(@PathVariable("id") int id,RedirectAttributes attributes){
        int result = blogService.deleteBlog(id);
        System.out.println("===============================");
        System.out.println(result);
        if (result==1){
            attributes.addFlashAttribute("message","删除博客成功！");
        } else {
            attributes.addFlashAttribute("message","删除博客失败！");
        }
        return REDIRECT_LIST;
    }

}
