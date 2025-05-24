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

public class NoticeMapper {
    public static Notice jsonToNotice(Map data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");



        return new Notice(
                NoticeType.valueOf(data.get("tipo").toString()),
                data.get("id").toString(),
                data.get("titulo").toString(),
                data.get("introducao").toString(),
                LocalDate.parse(data.get("data_publicacao").toString(), formatter),
                data.get("produto_id").toString(),
                data.get("produtos").toString(),
                ImagesMapper.jsonToImages(data.get("imagens").toString()),
                EditorialsMapper.jsonToEditorials(data.get("editorias").toString()),
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
