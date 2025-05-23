package com.TrabalhoOOP;
import com.TrabalhoOOP.Adapters.IbgeNoticeAdapter;
import com.TrabalhoOOP.Interfaces.INoticesApi;

import java.net.http.HttpClient;

public class Main {
    public static void main(String[] args) {
        try {
            // usando singleton
            HttpClient httpClient = HttpClient.newHttpClient();
            INoticesApi NoticeApi = new IbgeNoticeAdapter(
                    httpClient
            );


        }catch (Exception e){
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
}
