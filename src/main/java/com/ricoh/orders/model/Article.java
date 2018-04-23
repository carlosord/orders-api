package com.ricoh.orders.model;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Generated(
	value = "DieselsCodeGenerator",
	comments = "Generated model entity class",
	date = "Wed Apr 11 20:11:16 CEST 2018"
)
@Entity
@ApiModel("Entity Article")
@Table(name = "T_ARTICLES", uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
public class Article extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("Article´s code that acts as natural key")
	@NotNull
	@Column(name = "code", nullable = false)
	private String code;
	
	@ApiModelProperty("Article´s name ")
	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	
	@ApiModelProperty("Article´s description")
	@NotNull
	@Column(name = "description", nullable = false)
	private String description;
	
	@ApiModelProperty("Article´s price")
	@NotNull
	@Column(name = "price", nullable = false)
	private Double price;
	
	@ApiModelProperty("Article´s catalogue")
	@JoinColumn(name = "catalogue_id")
	@ManyToOne(optional = true)
	private Catalogue catalogue;
	
	Article() {}
	
	public Article(String code) {
		super();
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
			
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return this.description;
	}
			
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getPrice() {
		return this.price;
	}
			
	public void setPrice(Double price) {
		this.price = price;
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
		Article other = (Article) obj;
		return Objects.equals(this.getCode(), other.getCode());
	}
	
	@Override
	public String toString() {
		return "Article [code=" + this.getCode() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", price=" + this.getPrice() + ", id=" + this.getId() + "]";
	}
	
	
	void _setCatalogue(Catalogue catalogue) {
		this.catalogue = catalogue;
	}
	
	public Catalogue getCatalogue() {
		return this.catalogue;
	}
	
}
