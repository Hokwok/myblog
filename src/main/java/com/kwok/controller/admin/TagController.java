package com.kwok.controller.admin;


import com.kwok.pojo.Tag;
import com.kwok.service.TagService.TagService;
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
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String tags(HttpServletRequest req, HttpServletResponse resp, Model model){

        String pageIndex = req.getParameter("pageIndex");
        // 获得type总数(分页：上一页，下一页的情况)
        int totalCount = tagService.getTagCount();

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

        // 获取type列表展示
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("currentPageNo",(currentPageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Tag> tagList = tagService.getTagList(map);

        // 判断上一页，下一页是否显示
        int firstItemId = tagList.get(0).getId();
        int lastItemId = tagList.get(tagList.size()-1).getId();
        Tag prePageHave = tagService.getTagById(firstItemId+1);
        Tag nextPageHave = tagService.getTagById(lastItemId-1);
        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);

        model.addAttribute("totalCount",totalCount);
        model.addAttribute("tags",tagList);
        model.addAttribute("currentPageNo",currentPageNo);
        return "admin/tags";
    }

    @PostMapping("/tags/topage")
    public String topage(HttpServletRequest req, HttpServletResponse resp, Model model){

        String pageIndex = req.getParameter("pageIndex");
        // 获得type总数(分页：上一页，下一页的情况)
        int totalCount = tagService.getTagCount();

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

        // 获取type列表展示
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("currentPageNo",(currentPageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Tag> tagList = tagService.getTagList(map);

        // 判断上一页，下一页是否显示
        int firstItemId = tagList.get(0).getId();
        int lastItemId = tagList.get(tagList.size()-1).getId();
        Tag prePageHave = tagService.getTagById(firstItemId+1);
        Tag nextPageHave = tagService.getTagById(lastItemId-1);
        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);

        model.addAttribute("totalCount",totalCount);
        model.addAttribute("tags",tagList);
        model.addAttribute("currentPageNo",currentPageNo);
        return "admin/tags :: tagList";
    }

    @GetMapping("/tags/input")
    public String input(){
        return "admin/tags-input";
    }

    @PostMapping("/tags/input")
    public String addTag(String name, RedirectAttributes attributes){

        Tag tagExist = tagService.getTagByName(name);
        if (tagExist != null){
            attributes.addFlashAttribute("message","该标签已存在，请重新输入！");
            return "redirect:/admin/tags/input";
        }else {
            Tag lastTag = tagService.getLastTag();
            int id = lastTag.getId();
            Tag tag = new Tag();
            tag.setName(name);
            tag.setId(id+1);
            int i = tagService.addTag(tag);
            if (i==0){
                attributes.addFlashAttribute("message","添加失败！");
                return "redirect:/admin/tags/input";
            }else {
                attributes.addFlashAttribute("msg","添加成功！");
                return "redirect:/admin/tags";
            }
        }
    }

    @GetMapping("/tags/edit/{id}")
    public String editTag(@PathVariable("id") int id, Model model){
        model.addAttribute("TagResult",tagService.getTagById(id));
        return "admin/tags-update";
    }

    @PostMapping("/tags/edit")
    public String editTag(int id,String name,RedirectAttributes attributes){

        Tag tagExist = tagService.getTagByName(name);
        if (tagExist != null){
            attributes.addFlashAttribute("message","该标签已存在，请重新填写修改名称！");
            return "redirect:/admin/tags/edit/"+id;
        }else {
            Tag tag = new Tag();
            tag.setId(id);
            tag.setName(name);
            int i = tagService.updateTag(tag);
            if (i==0){
                attributes.addFlashAttribute("message","修改失败！");
                return "redirect:/admin/tags/edit/"+id;
            }else {
                attributes.addFlashAttribute("msg","修改成功！");
                return "redirect:/admin/tags";
            }
        }
    }

    @GetMapping("/tags/delete/{id}")
    public String deleteTag(@PathVariable("id") int id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg","删除成功！");
        return "redirect:/admin/tags";
    }

}
