package com.example.demo.controller;
import com.example.demo.dto.ArticleDataDto;
import com.example.demo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/article/allData")
    public ResponseEntity<List<ArticleDataDto>> getArticleData() throws Exception {
        return new ResponseEntity(articleService.getArticleData(), HttpStatus.OK);
    }
}
