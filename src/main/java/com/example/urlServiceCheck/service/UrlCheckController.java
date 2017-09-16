package com.example.urlServiceCheck.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


// controller
@Controller
public class UrlCheckController {

    /*
    Constantly check the urls in the list and add to the database, with @schedule, and get take info from database
     */
    // return List and parse JSON in front-end
    // List<String url,String status>

    @GetMapping("/urlCheck")
    public String greetingForm(Model model) {
        model.addAttribute("urlCheck", new UrlCheck());
        return "urlCheck";
    }

    // POST add new url to database
    @PostMapping("/urlCheck")
    public String greetingSubmit(@ModelAttribute UrlCheck urlCheck) {
        urlCheck.pingURL(urlCheck.getUrlS(),5000);
        return "urlCheck";
    }


}
