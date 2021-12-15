package com.example.demo.repositories;
import com.example.demo.dto.ArticleDataDto;


import java.util.List;

public interface ArticleDataRepository {

    List<ArticleDataDto> getArticleData() throws Exception;
}
