package com.ricoh.orders.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ricoh.orders.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
