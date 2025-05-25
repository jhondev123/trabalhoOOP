package com.TrabalhoOOP.Mappers.Ibge;


import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Entities.Product;
import com.TrabalhoOOP.Enums.NoticeType;
import com.google.gson.internal.LinkedTreeMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NoticeMapper {
    public static Notice jsonToNotice(Map data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        NoticeType noticeType = NoticeType.NOTICE;
        if(Objects.equals(data.get("tipo").toString(), "Release")) {
            noticeType = NoticeType.RELEASE;
        }

        return new Notice(
                noticeType,
                data.get("id").toString(),
                data.get("titulo").toString(),
                data.get("introducao").toString(),
                LocalDate.parse(data.get("data_publicacao").toString(), formatter),
                data.get("produto_id").toString(),
                data.get("produtos").toString(),
                ImagesMapper.jsonToImages(data.get("imagens").toString()),
                EditorialsMapper.jsonToEditorials(data.get("editorias").toString()),
                ProductsMapper.jsonToProducts(data.get("produtos_relacionados").toString()),
                Objects.equals(data.get("destaque").toString(), "true"),
                data.get("link").toString()
        );
    }
    public static List<Notice> jsonToNotices(Object[] data){
       List<Notice> notices = new ArrayList<>();

       for (Object obj : data) {
           Map<String, Object> map = (Map<String, Object>) obj;
           notices.add(jsonToNotice(map));
       }

         return notices;
    }
}
