package com.kwok.controller;

import com.kwok.pojo.Blog;
import com.kwok.service.BlogService.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveShowController {

    @Autowired
    BlogService blogService;
    
    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> archiveMap = blogService.archiveBlog();
        int blogCount = blogService.getBlogCount();
        model.addAttribute("archiveMap",archiveMap);
        model.addAttribute("blogCount",blogCount);
        return "archives";
    }

}
