package com.ricoh.orders.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ricoh.orders.model.Article;
import com.ricoh.orders.service.ArticleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/article")
@Api(value = "article", description = "Operations over article objects")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@ApiOperation(value = "Show an article based on the given id", response = Article.class)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Article obtained correctly"),
			@ApiResponse(code = 401, message = "You are not authorized to obtain an article"),
			@ApiResponse(code = 401, message = "Forbidden article"),
			@ApiResponse(code = 404, message = "Article not found") })
	@GetMapping("/{id}") // @RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Article> read(@PathVariable("id") Long id) {
		Article article = articleService.read(id);
		return article == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(article);
	}

	@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = { EmptyResultDataAccessException.class, EntityNotFoundException.class })
	public void handleNotFound() {
		//TODO Error response with ApiError
	}

}
