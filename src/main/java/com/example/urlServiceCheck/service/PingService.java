package com.example.urlServiceCheck.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PingService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public int pingURL(String url, int timeout) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.setRequestMethod(RequestMethod.HEAD.name());
            return connection.getResponseCode();
        } catch (IOException exception) {
            log.error("Failed to read response code", exception);
        }
        return 0;
    }

}
