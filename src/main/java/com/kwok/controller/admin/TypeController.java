package com.kwok.controller.admin;

import com.kwok.pojo.Type;
import com.kwok.service.TypeService.TypeService;
import com.kwok.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    TypeService typeService;

    @GetMapping("/types")
    public String types(HttpServletRequest req,HttpServletResponse resp, Model model){

        String pageIndex = req.getParameter("pageIndex");
        // 获得type总数(分页：上一页，下一页的情况)
        int totalCount = typeService.getTypeCount();

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
        List<Type> typeList = typeService.getTypeList(map);

        // 判断上一页，下一页是否显示
        int firstItemId = typeList.get(0).getId();
        int lastItemId = typeList.get(typeList.size()-1).getId();
        Type prePageHave = typeService.getTypeById(firstItemId+1);
        Type nextPageHave = typeService.getTypeById(lastItemId-1);
        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);

        model.addAttribute("totalCount",totalCount);
        model.addAttribute("types",typeList);
        model.addAttribute("currentPageNo",currentPageNo);
        return "admin/types";
    }

    @PostMapping("/types/topage")
    public String topage(HttpServletRequest req,HttpServletResponse resp, Model model){

        String pageIndex = req.getParameter("pageIndex");
        // 获得type总数(分页：上一页，下一页的情况)
        int totalCount = typeService.getTypeCount();

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
        List<Type> typeList = typeService.getTypeList(map);

        // 判断上一页，下一页是否显示
        int firstItemId = typeList.get(0).getId();
        int lastItemId = typeList.get(typeList.size()-1).getId();
        Type prePageHave = typeService.getTypeById(firstItemId+1);
        Type nextPageHave = typeService.getTypeById(lastItemId-1);
        model.addAttribute("prePageHave",prePageHave);
        model.addAttribute("nextPageHave",nextPageHave);

        model.addAttribute("totalCount",totalCount);
        model.addAttribute("types",typeList);
        model.addAttribute("currentPageNo",currentPageNo);
        return "admin/types :: typeList";
    }

    @GetMapping("/types/input")
    public String input(){
        return "admin/types-input";
    }

    @PostMapping("/types/input")
    public String addType(String name,RedirectAttributes attributes){

        Type typeExist = typeService.getTypeByName(name);
        if (typeExist != null){
            attributes.addFlashAttribute("message","该类名已存在，请重新输入！");
            return "redirect:/admin/types/input";
        }else {
            Type lastType = typeService.getLastType();
            int id = lastType.getId();
            Type type = new Type();
            type.setName(name);
            type.setId(id+1);
            System.out.println(id+1);
            int i = typeService.addType(type);
            if (i==0){
                attributes.addFlashAttribute("message","添加失败！");
                return "redirect:/admin/types/input";
            }else {
                attributes.addFlashAttribute("msg","添加成功！");
                return "redirect:/admin/types";
            }
        }
    }

    @GetMapping("/types/edit/{id}")
    public String editType(@PathVariable("id") int id,Model model){
        model.addAttribute("TypeResult",typeService.getTypeById(id));
        return "admin/types-update";
    }

    @PostMapping("/types/edit")
    public String editType(int id,String name,RedirectAttributes attributes){

        Type typeExist = typeService.getTypeByName(name);
        if (typeExist != null){
            attributes.addFlashAttribute("message","该类名已存在，请重新填写修改名称！");
            return "redirect:/admin/types/edit/"+id;
        }else {
            Type type = new Type();
            type.setId(id);
            type.setName(name);
            int i = typeService.updateType(type);
            if (i==0){
                attributes.addFlashAttribute("message","修改失败！");
                return "redirect:/admin/types/edit/"+id;
            }else {
                attributes.addFlashAttribute("msg","修改成功！");
                return "redirect:/admin/types";
            }
        }
    }

    @GetMapping("/types/delete/{id}")
    public String deleteType(@PathVariable("id") int id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg","删除成功！");
        return "redirect:/admin/types";
    }

}
