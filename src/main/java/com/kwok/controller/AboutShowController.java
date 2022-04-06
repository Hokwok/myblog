package com.kwok.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowController {

    @GetMapping("/about")
    public String archives(){
        return "about";
    }

}
