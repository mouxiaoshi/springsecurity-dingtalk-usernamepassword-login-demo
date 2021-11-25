package com.example.springdingtalklogin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mouxiaoshi
 */
@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping("admin")
    @PreAuthorize("hasRole('admin')")
    public String admin(){
        return "admin";
    }

    @GetMapping("admin1")
    @PreAuthorize("hasRole('admin1')")
    public String admin1(){
        return "admin1";
    }

    @GetMapping("admin2")
    @PreAuthorize("hasRole('admin2')")
    public String admin2(){
        return "admin2";
    }
}
