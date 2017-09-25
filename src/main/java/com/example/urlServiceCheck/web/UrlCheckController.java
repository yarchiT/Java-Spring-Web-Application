package com.example.urlServiceCheck.web;

import com.example.urlServiceCheck.domain.UrlCheck;
import com.example.urlServiceCheck.domain.UrlCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UrlCheckController {

    private final UrlCheckRepository repository;

    @Autowired
    UrlCheckController(UrlCheckRepository repository) {
        this.repository = repository;
    }



       @GetMapping("/urls")
       public List<UrlCheck> send() {
            return repository.findAll();
        }

    @PostMapping("/urls")
    @ResponseStatus(value=HttpStatus.CREATED)
    public void add(@RequestBody UrlCheck urlCheck) {
        urlCheck.pingURL(urlCheck.getUrlS(), 5000);
        repository.save(urlCheck);
    }


}
