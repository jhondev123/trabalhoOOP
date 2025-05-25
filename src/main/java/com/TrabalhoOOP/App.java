package com.TrabalhoOOP;

import com.TrabalhoOOP.Adapters.IbgeNoticeAdapter;
import com.TrabalhoOOP.Controllers.NoticeController;
import com.TrabalhoOOP.Controllers.UserController;
import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Entities.User;
import com.TrabalhoOOP.Interfaces.INoticesApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Scanner;

public class App {

    private final NoticeController noticeController;
    private final UserController userController;
    private final Scanner sc;

    public App() {
        this.sc = new Scanner(System.in);

        HttpClient httpClient = HttpClient.newHttpClient();
        INoticesApi noticeApi = new IbgeNoticeAdapter(
                httpClient,
                new Gson()
        );

        this.noticeController = new NoticeController(noticeApi);
        this.userController = new UserController();
    }

    public static void main(String[] args) {
        try {
            App app = new App();
            app.run();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    public void run() throws IOException, InterruptedException {
        List<Notice> notices = getNotices();
        User user = createUser();

        showMenu(user);
    }

    private List<Notice> getNotices() throws IOException, InterruptedException {
        return noticeController.getAllNotices();
    }

    private User createUser() {
        System.out.print("Digite seu nome: ");
        String name = sc.nextLine();
        System.out.print("Digite seu apelido: ");
        String nickName = sc.nextLine();
        return userController.CreateUser(name, nickName);
    }

    private void showMenu(User user) {
        System.out.println("Olá " + user.name + ", seja bem-vindo ao sistema!");
        System.out.println("Você pode consultar as notícias, ou sair do sistema.");
        System.out.println("Digite 1 para consultar as notícias, ou 0 para sair do sistema.");
    }
}
