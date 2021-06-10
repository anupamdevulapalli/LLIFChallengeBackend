package com.llifinterview.challenge;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.llifinterview.challenge.search.SearchService;
import com.llifinterview.challenge.upload.UploadService;

@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {
    @Resource
    UploadService uploadService;

    @Resource
    SearchService searchService;

    public static void main ( String[] args ) {
        SpringApplication.run( ChallengeApplication.class, args );
    }

    @Override
    public void run ( String... arg ) throws Exception {
        uploadService.init();
        searchService.init();
    }

}
