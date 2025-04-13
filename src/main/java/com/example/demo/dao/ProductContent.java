package com.example.demo.dao;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 *
 * @author anton.nilsson
 */
@Entity(name = "ProductContent")
@Table(name = "productcontent")
public class ProductContent {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "productcontentid", unique = true, nullable = false)
    private Integer productcontentid;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "articleid")
    private Article article;
    @Column(name = "articleid", insertable=false, updatable=false, nullable = false)
    private Integer articleid;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "productid")
    private Product product;
    @Column(name = "productid", insertable=false, updatable=false, nullable = false)
    private Integer productid;
    private Integer amountNeeded;

    public ProductContent(Article article, Integer amountNeeded, Product product) {
        this.article = article;
        this.articleid = article.getArticleid();
        this.product = product;
        this.productid = product.getProductid();
        this.amountNeeded = amountNeeded;
    }
    public ProductContent() {}


    public Integer getProductcontentid() {
        return productcontentid;
    }
    public void setProductcontentid(Integer productcontentid) {
        this.productcontentid = productcontentid;
    }
    public Integer getArticleid() {
        return articleid;
    }
    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }
    public Article getArticle() {
        return article;
    }
    public void setArticle(Article article) {
        this.article = article;
    }
    public Integer getAmountNeeded() {
        return amountNeeded;
    }
    public void setAmountNeeded(Integer amount) {
        this.amountNeeded = amount;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    
}
