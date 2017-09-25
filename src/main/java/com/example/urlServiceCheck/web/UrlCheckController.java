package com.example.urlServiceCheck.web;

import com.example.urlServiceCheck.domain.UrlCheck;
import com.example.urlServiceCheck.domain.UrlCheckRepository;
import com.example.urlServiceCheck.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UrlCheckController {

    private final UrlCheckRepository repository;

    private final PingService pingService;

    @Autowired
    UrlCheckController(UrlCheckRepository repository, PingService pingService) {
        this.repository = repository;
        this.pingService = pingService;
    }


       @GetMapping("/urls")
       public List<UrlCheck> send() {
            return repository.findAll();
        }

    @PostMapping("/urls")
    @ResponseStatus(value=HttpStatus.CREATED)
    public void add(@RequestBody UrlCheck urlCheck) {
        int responseCode = pingService.pingURL(urlCheck.getUrlString(), 5000);
        urlCheck.setResponseCode(responseCode);
        repository.save(urlCheck);
    }


}
