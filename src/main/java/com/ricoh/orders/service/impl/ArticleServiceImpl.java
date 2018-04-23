package com.ricoh.orders.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricoh.orders.model.Article;
import com.ricoh.orders.repository.ArticleRepository;
import com.ricoh.orders.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;

	@Override
	public Article read(Long id) {
		return articleRepository.findOne(id);
	}

}
