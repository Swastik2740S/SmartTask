package com.smarttask;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hello {
    @RequestMapping("/")
    public String backend(){
        return "Welcome to the backend";
    }
}
