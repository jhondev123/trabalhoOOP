package com.TrabalhoOOP;
import com.TrabalhoOOP.Adapters.IbgeNoticeAdapter;
import com.TrabalhoOOP.Interfaces.INoticesApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpClient;

public class App {
    public static void main(String[] args) {
        try {
            // usando singleton
            HttpClient httpClient = HttpClient.newHttpClient();
            INoticesApi NoticeApi = new IbgeNoticeAdapter(
                    httpClient,
                    new Gson()
            );
            NoticeApi.getAllNotices();


        }catch (Exception e){
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
}
