package com.TrabalhoOOP;
import com.TrabalhoOOP.Adapters.IbgeNoticeAdapter;
import com.TrabalhoOOP.Controllers.NoticeController;
import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Interfaces.INoticesApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            // variaveis de armazenamento em memória
            List<Notice> notices;

            // usando singleton para serviços
            HttpClient httpClient = HttpClient.newHttpClient();
            INoticesApi NoticeApi = new IbgeNoticeAdapter(
                    httpClient,
                    new Gson()
            );
            // controllers
            NoticeController noticeController = new NoticeController(NoticeApi);

            // consultando as noticias
            notices = GetNoticies(noticeController);

        }catch (Exception e){
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
    public static List<Notice> GetNoticies(NoticeController controller) throws IOException, InterruptedException {
        return controller.getAllNotices();
    }
}
