package com.example.urlServiceCheck.service;

import com.example.urlServiceCheck.domain.UrlCheck;
import com.example.urlServiceCheck.domain.UrlCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateScheduler {

    private final PingService pingService;
    private final UrlCheckRepository repository;

    @Autowired
    public UpdateScheduler(UrlCheckRepository repository, PingService pingService) {
        this.repository = repository;
        this.pingService = pingService;
    }

    @Scheduled(fixedRate = 5000)
    public void updateStatus() {
        for (UrlCheck url : repository.findAll()) {
            int responseCode = pingService.pingURL(url.getUrlString(), 5000);
            if (url.getResponseCode() != responseCode) {
                url.setResponseCode(responseCode);
                repository.save(url);
            }
        }

    }
}
