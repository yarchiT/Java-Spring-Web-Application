package com.example.urlServiceCheck.domain;


import com.example.urlServiceCheck.domain.UrlCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlCheckRepository extends JpaRepository<UrlCheck, Long> {

   // List<UrlCheck> findByCode(int responseCode);
}

