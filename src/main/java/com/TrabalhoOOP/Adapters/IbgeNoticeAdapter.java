package com.TrabalhoOOP.Adapters;


import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Interfaces.INoticesApi;
import com.TrabalhoOOP.Mappers.Ibge.NoticeMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IbgeNoticeAdapter implements INoticesApi {
    // Aqui daria para usar variaveis de ambiente para não deixar a URL exposta, mas só para estudo então whatever
    private String URL = "http://servicodados.ibge.gov.br/api/v3";
    private HttpClient client;
    private Gson gson;
    public IbgeNoticeAdapter(HttpClient httpClient, Gson gson) {
        this.client = httpClient;
        this.gson = gson;
    }
    @Override
    public List<Notice> getAllNotices(int qtd) throws IOException, InterruptedException {

        // prepara a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/noticias"+ "?qtd=" + qtd))
                .header("accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // transformando as noticias em uma lista que eu consiga manipular
        Type responseType = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> data = gson.fromJson(response.body(), responseType);
        List<Map<String, Object>> items = (List<Map<String, Object>>) data.get("items");

        return NoticeMapper.jsonToNotices(items.toArray());
    }
}
