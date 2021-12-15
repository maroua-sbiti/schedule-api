package com.example.demo.repositories;
import com.example.demo.dto.ArticleDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleDataRepositoryImpl implements ArticleDataRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ArticleDataDto> getArticleData() throws Exception {
        List<ArticleDataDto> articleData = new ArrayList<>();
        StringBuilder query = new StringBuilder();
        query.append("SELECT t_level.name_building,t_level.level_name,t_group.super_family,t_group.family,t_group.name_group,t_article.reference,t_article.description,t_level_article.quantity FROM t_level,t_group,t_article,t_level_article WHERE t_level.id_level=t_level_article.id_level AND t_level_article.id_article=t_article.id_article AND t_article.id_group=t_group.id_group");
        Query q = entityManager.createNativeQuery(query.toString());

        List<Object[]> results = q.getResultList();

        for (Object[] res : results) {
            ArticleDataDto item = new ArticleDataDto();
            item.setBuildingName("" + res[0]);
            item.setLevelName("" + res[1]);
            item.setSuperFamilyName("" + res[2]);
            item.setFamilyName("" + res[3]);
            item.setGroupName("" + res[4]);
            item.setArticleName("" + res[5]);
            item.setArticleDescription((""+res[6]));
            item.setArticleQuantity(Double.valueOf("" + res[7]));
            articleData.add(item);
        }
        return articleData;
    }
}
