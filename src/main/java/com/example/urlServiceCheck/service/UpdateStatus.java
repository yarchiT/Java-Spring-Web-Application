package com.example.urlServiceCheck.service;

import com.example.urlServiceCheck.domain.UrlCheck;
import com.example.urlServiceCheck.domain.UrlCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateStatus {

    private final UrlCheckRepository repository;

    @Autowired
    public UpdateStatus(UrlCheckRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 5000)
    public void updateStatus(){
       int oldResponseCode;

        for (UrlCheck url : repository.findAll()) {
            oldResponseCode= url.getResponseCode();
            url.pingURL(url.getUrlS(), 5000);

            if(oldResponseCode!= url.getResponseCode())
                repository.save(url);
        }
    }


}
