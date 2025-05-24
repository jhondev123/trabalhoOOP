package com.TrabalhoOOP.Mappers.Ibge;

import com.TrabalhoOOP.Entities.Editory;
import com.TrabalhoOOP.Entities.NoticeImage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ImagesMapper {
    public static NoticeImage jsonToImage(String data){
        return new NoticeImage(
                data
        );
    }

    public static List<NoticeImage> jsonToImages(String data) {
        // tinha que chegar aqui por dependência em um mundo ideal
        List<NoticeImage> images = new ArrayList<>();
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> imagens = gson.fromJson(data, type);

        for (Map.Entry<String, String> entry : imagens.entrySet()) {
            String value = entry.getValue();
            images.add(jsonToImage(value));
        }

        return images;
    }
}
