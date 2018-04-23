package com.ricoh.orders.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Generated(
	value = "DieselsCodeGenerator",
	comments = "Generated business service class",
	date = "Wed Apr 11 20:11:16 CEST 2018"
)
@Entity
@ApiModel("Entity Order")
@Table(name = "T_ORDERS", uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
public class Order extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("Order´s code that acts as natural key")
	@NotNull
	@Column(name = "code", nullable = false)
	private String code;
	
	@ApiModelProperty("Order´s articles")
	@ManyToMany
	private List<Article> articles = new ArrayList<>();
	
	Order() {}
	
	public Order(String code) {
		super();
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getCode());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(this.getCode(), other.getCode());
	}
	
	@Override
	public String toString() {
		return "Order [code=" + this.getCode() + "]";
	}	
	
	List<Article> _getArticles() {
		return this.articles;
	}
	
	public List<Article> getArticles() {
		return new ArrayList<>(articles);
	}
	
}
