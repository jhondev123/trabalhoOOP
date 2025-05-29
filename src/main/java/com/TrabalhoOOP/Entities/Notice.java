package com.TrabalhoOOP.Entities;


import com.TrabalhoOOP.Enums.NoticeType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Notice extends  Entity {
    private NoticeType noticeType;
    private int id;
    private String title;
    private String introduction;
    private LocalDate publishDate;
    private int productId;
    private String products;
    private List<NoticeImage> images;
    private List <Editory> editorials;
    private List<Product> productsRelated;
    private Boolean contrast;
    private String link;
    public Boolean read = false;

    public Notice(NoticeType noticeType, int id, String title, String introduction, LocalDate publishDate, int productId, String products, List<NoticeImage> images, List<Editory> editorials, List<Product> productsRelated, Boolean contrast, String link) {
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
        return  "Titulo: " + title + '\n' +
                "Id: " + id + '\n' +
                "Introdução: " + introduction + '\n' +
                "Data de publicação: " + publishDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + '\n' +
                "Link: " + link + '\n' +
                "Tipo de Notícia: " + noticeType + '\n' +
                "Fonte: " + editorials.toString() + '\n';
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public String getTitle() {
        return title;
    }

    public NoticeType getNoticeType() {
        return noticeType;
    }

    public int getId() {
        return id;
    }

}
