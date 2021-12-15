package com.example.demo.repositories;

import com.example.demo.entity.Level_Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Level_ArticleRepository extends JpaRepository<Level_Article,Long> {
}
