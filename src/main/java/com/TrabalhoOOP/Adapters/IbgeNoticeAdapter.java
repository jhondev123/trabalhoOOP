package com.TrabalhoOOP.Adapters;

import Src.Entities.Notice;
import Src.Interfaces.INoticesApi;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class IbgeNoticeAdapter implements INoticesApi {
    // Aqui daria para usar variaveis de ambiente para não deixar a URL exposta, mas só para estudo então whatever
    private String URL = "http://servicodados.ibge.gov.br/api/v3";
    private HttpClient client;
    public IbgeNoticeAdapter(HttpClient httpClient) {
        this.client = httpClient;
    }
    @Override
    public List<Notice> getAllNotices() throws IOException, InterruptedException {
        List<Notice> notices = new ArrayList<>();

        // prepara a request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + "/noticias"))
                .header("accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        return notices;
    }
}
