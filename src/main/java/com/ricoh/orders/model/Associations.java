package com.ricoh.orders.model;

import javax.annotation.Generated;

@Generated(
	value = "DieselsCodeGenerator",
	comments = "Generated model associations class",
	date = "Wed Apr 11 20:11:16 CEST 2018"
)
public class Associations {

	public static class Belong {
	    public static void link(Article article, Catalogue catalogue) {
	    	catalogue._getArticles().add(article);					
	    	article._setCatalogue(catalogue);
	    }
	
	    public static void unlink(Article article, Catalogue catalogue) {
	    	article._setCatalogue(null);
	    	catalogue._getArticles().remove(article);
	    }
	}
	
	public static class Has {
	    public static void link(Order orders, Article articles) {
	    	orders._getArticles().add(articles);
	    }
	
	    public static void unlink(Order orders, Article articles) {
	    	orders._getArticles().remove(articles);
	    }
	}
	
}