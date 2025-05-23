package com.TrabalhoOOP.Entities;


import com.TrabalhoOOP.Enums.NoticeType;

import java.time.LocalDate;
import java.util.List;

public class Notice {
    private NoticeType noticeType;
    private String id;
    private String title;
    private String introduction;
    private LocalDate publishDate;
    private String productId;
    private List<Product> products;
    private List<NoticeImage> images;
    private List <Editory> editorials;
    private List<Product> productsRelated;
    private Boolean contrast;
    private String link;

    public Notice(NoticeType noticeType, String id, String title, String introduction, LocalDate publishDate, String productId, List<Product> products, List<NoticeImage> images, List<Editory> editorials, List<Product> productsRelated, Boolean contrast, String link) {
        this.noticeType = noticeType;
        this.id = id;
        this.title = title;
        this.introduction = introduction;
        this.publishDate = publishDate;
        this.productId = productId;
        this.products = products;
        this.images = images;
        this.editorials = editorials;
        this.productsRelated = productsRelated;
        this.contrast = contrast;
        this.link = link;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "noticeType=" + noticeType +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", introduction='" + introduction + '\'' +
                ", publishDate=" + publishDate +
                ", productId='" + productId + '\'' +
                ", products=" + products +
                ", images=" + images +
                ", editorials=" + editorials +
                ", productsRelated=" + productsRelated +
                ", contrast=" + contrast +
                ", link='" + link + '\'' +
                '}';
    }
}
