package com.example.urlServiceCheck.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlCheckRepository extends JpaRepository<UrlCheck, String> {

    List<UrlCheck> findByResponseCode(int responseCode);

   // UrlCheck findOne(String urlString);
}

