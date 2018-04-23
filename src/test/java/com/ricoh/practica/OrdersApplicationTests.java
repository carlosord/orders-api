package com.ricoh.practica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.ricoh.orders.OrdersApplication;
import com.ricoh.orders.model.Article;
import com.ricoh.orders.model.Associations;
import com.ricoh.orders.model.Order;
import com.ricoh.orders.service.ArticleService;
import com.ricoh.orders.service.OrderService;
import com.ricoh.orders.service.impl.ArticleServiceImpl;
import com.ricoh.orders.service.impl.OrderServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = OrdersApplication.class)
public class OrdersApplicationTests {
	
	@TestConfiguration
    static class ServicesImplTestContextConfiguration {
  
        @Bean
        public ArticleService articleService() {
            return new ArticleServiceImpl();
        }
        
        @Bean
        public OrderService orderService() {
            return new OrderServiceImpl();
        }
        
    }
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private OrderService orderService;
	
	
	/* Testing application services */
	
	/* Article´s service */
	@Test
	public void findArticle() {		
	    Article a = articleService.read(1L);
	    assertEquals("ART001", a.getCode());
	    
	    Article b = articleService.read(100L);
	    assertNull(b);
	}
	
	/* Order´s service */
	@Test
	@Rollback(true)
	public void createOrder() {		
		Article a = articleService.read(1L);
		Article b = articleService.read(2L);
		Article c = articleService.read(3L);
	
	    List<Article> articles = Arrays.asList(a, b, c);
	    
	    Order o = new Order("ORD010");
	    articles.forEach(art -> Associations.Has.link(o, art));
	    		
	    Order result = orderService.create(o);
	    assertEquals(o, result);
	    
	    result = orderService.read(result.getId());
	    assertEquals(o, result);
	    assertTrue(result.getArticles().size() == 3);
	}
	
	@Test
	public void readOrder() {		
		Order a = orderService.read(1L);
	    assertEquals("ORD001", a.getCode());
	    
	    Order b = orderService.read(100L);
	    assertNull(b);
	}
	
	@Test
	@Rollback(true)
	public void updateOrder() {		
		Article a = articleService.read(1L);
		Article b = articleService.read(2L);
		Article c = articleService.read(3L);
	
	    List<Article> articles = Arrays.asList(a, b, c);
	    
	    Order o = new Order("ORD010");
	    articles.forEach(art -> Associations.Has.link(o, art));
	    		
	    Order result = orderService.create(o);	    
	    result = orderService.read(result.getId());
	    assertTrue(result.getArticles().size() == 3);
	    
	    Order toUpdate = new Order("ORD010");
	    Associations.Has.link(toUpdate, c);
	    
	    result = orderService.update(result.getId(), toUpdate);
	    assertEquals(o, result);
	    
	    result = orderService.read(result.getId());
	    assertEquals(o, result);
	    assertTrue(result.getArticles().size() == 1);
	}
	
	@Test
	@Rollback(true)
	public void deleteOrder() {		
		Order a = orderService.read(1L);
	    assertEquals("ORD001", a.getCode());
	    
	    a = orderService.delete(1L);
	    assertEquals("ORD001", a.getCode());
	    
	    a = orderService.read(1L);
	    assertNull(a);
	    
	    Order b = orderService.delete(100L);
	    assertNull(b);
	}
	
	/* Final application service test */

}
