package com.crio.LMSExamManager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hidden-feature")
public class HiddenFreatureController {
    
    @GetMapping("/number")
    public String getRandomFactAboutTheNumber(@PathVariable int number){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://numbersapi.com/" + number;

        return restTemplate.getForObject(url, String.class);
    }
}
