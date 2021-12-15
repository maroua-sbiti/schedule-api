package com.example.demo.service;
import com.example.demo.dto.ArticleDataDto;
import com.example.demo.repositories.ArticleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleDataRepository articleDataRepository;

    public List<ArticleDataDto> getArticleData() throws Exception {
        return articleDataRepository.getArticleData();
    }
}
