package com.TrabalhoOOP;

import com.TrabalhoOOP.Adapters.IbgeNoticeAdapter;
import com.TrabalhoOOP.Adapters.JsonPersistAdapter;
import com.TrabalhoOOP.Controllers.NoticeController;
import com.TrabalhoOOP.Controllers.UserController;
import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Entities.User;
import com.TrabalhoOOP.Interfaces.INoticesApi;
import com.TrabalhoOOP.Interfaces.IPersist;
import com.TrabalhoOOP.Repository.NoticeRepository;
import com.google.gson.Gson;
import java.net.http.HttpClient;
import java.util.Scanner;

public class App {

    private final NoticeController noticeController;
    private final UserController userController;
    private final Scanner sc;
    public App() {

        this.sc = new Scanner(System.in);

        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();
        IPersist persistAdapter = new JsonPersistAdapter();

        INoticesApi noticeApi = new IbgeNoticeAdapter(
                httpClient,
                gson,
                persistAdapter
        );
        NoticeRepository repository = new NoticeRepository(persistAdapter,noticeApi,gson);

        this.noticeController = new NoticeController(noticeApi,repository,sc);
        this.userController = new UserController(sc);
    }

    public static void main(String[] args) {
        try {
            App app = new App();
            app.run();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro na execução do programa: " + e.getMessage());
            System.out.println("Gostaria de tentar reabrir o sistema? (S/N)");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("S")) {
                main(args);
            } else {
                System.out.println("Obrigado por usar o sistema!");
            }
        }
    }

    public void run() throws Exception {
        noticeController.getNotices(sc);
        User user = userController.createUser();
        System.out.println("Olá " + user.name + ", seja bem-vindo ao sistema!");
        showMenu();
    }

   private void showMenu() {
       System.out.println("\n ---------------------------------");

       // todas as noticias
        System.out.println("[1] - Consultar notícias");
        System.out.println("[2] - Filtrar notícias");
        // favoritos
        System.out.println("[3] - Criar favoritos");
        System.out.println("[4] - Remover favoritos");
        System.out.println("[5] - Listar favoritos");

        // todas as noticias
        System.out.println("[6] - Marcar notícias como lidas");

        // ver depois
        System.out.println("[7] - Listar notícias lidas");
        System.out.println("[8] - Criar notícias para ver depois");
        System.out.println("[9] - Remover notícias para ver depois");
        System.out.println("[10] - Listar notícias para ver depois");
        // ordenar listas
        System.out.println("[11] - Ordenar listas de notícias");

        // sair
        System.out.println("[0] - Sair");

        System.out.print("Digite a opção desejada: ");
        String option = sc.nextLine();
        switch (option) {
            case "1":
                // listar todas as noticias
                noticeController.listNotices(noticeController.notices);
                break;
            case "2":
                // filtrar noticias
                noticeController.executeFilterNotices(noticeController.notices);
                break;
            case "3":
                // criar favoritos
                noticeController.createFavorites();
                break;
            case "4":
                // remover favoritos
                noticeController.removeFavorites();
                break;
            case "5":
                // listar favoritos
                noticeController.listNotices(noticeController.favorites);
                break;
            case "6":
                // marcar como lida
                noticeController.MarkAsRead();
                break;
            case "7":
                // listas as lidas
                noticeController.listNoticesRead();
                break;
            case "8":
                // criar para ver depois
                noticeController.createToSeeLater();
                break;
            case "9":
                // remover para ver depois
                noticeController.removeToSeeLater();
                break;
            case "10":
                // listar para ver depois
                noticeController.listNotices(noticeController.toSeeLater);
                break;
            case "11":
                // ordenar listas
                noticeController.listOrderNotices();
                break;
            case "0":
                System.out.println("Saindo...");
                sc.close();
                return;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
        // chama o menu novamente
        showMenu();
    }
}
