package com.ricoh.orders.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Generated(
	value = "DieselsCodeGenerator",
	comments = "Generated model entity class",
	date = "Wed Apr 11 20:11:16 CEST 2018"
)
@Entity
@ApiModel("Entity Catalogue")
@Table(name = "T_CATALOGUES", uniqueConstraints = @UniqueConstraint(columnNames = {"code", "name"}))
public class Catalogue extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("Catalogue´s code that acts as natural key")
	@NotNull
	@Column(name = "code", nullable = false)
	private String code;
	
	@ApiModelProperty("Catalogue´s name")
	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	
	@JsonIgnore
	@ApiModelProperty("Cataloge´s articles")
	@OneToMany(mappedBy="catalogue")
	private List<Article> articles = new ArrayList<>();
	
	Catalogue() {}
	
	public Catalogue(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getCode(), this.getName());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Catalogue other = (Catalogue) obj;
		return Objects.equals(this.getCode(), other.getCode()) && Objects.equals(this.getName(), other.getName());
	}
	
	@Override
	public String toString() {
		return "Catalogue [code=" + this.getCode() + ", name=" + this.getName() + "]";
	}
	
	List<Article> _getArticles() {
		return this.articles;
	}
	
	public List<Article> getArticles() {
		return new ArrayList<>(articles);
	}
	
}
