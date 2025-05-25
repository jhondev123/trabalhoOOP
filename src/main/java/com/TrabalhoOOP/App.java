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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App {

    private final NoticeController noticeController;
    private final UserController userController;
    private final Scanner sc;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private List<Notice> notices;
    private List<Notice> favorites = new ArrayList<Notice>();
    private List<Notice> toSeeLater = new ArrayList<Notice>();

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
        notices = getNotices();
        User user = createUser();
        showMenu(user);
    }

    private List<Notice> getNotices() throws IOException, InterruptedException {
        return noticeController.getAllNotices(3);
    }
    private void ListNotices(List<Notice> notices) {
        for (Notice notice : notices) {
            System.out.println(notice.toString());
        }
    }

    private User createUser() {
        System.out.print("Digite seu nome: ");
        String name = sc.nextLine();
        System.out.print("Digite seu apelido: ");
        String nickName = sc.nextLine();
        return userController.CreateUser(name, nickName);
    }
    private void filterNotices(List<Notice> notices) {
        System.out.println("Digite a data da noticia no formato dd/MM/yyyy: ");
        String date = sc.nextLine();
        LocalDate localDate;
        // caso não passe a data não filtra por ela
        if(date.isEmpty()){
            localDate = null;
        } else {
            try {
                localDate = LocalDate.parse(date, formatter);
            } catch (Exception e) {
                System.out.println("Data inválida, por favor digite novamente.");
                filterNotices(notices);
                return;
            }
        }

        System.out.println("Digite o título da noticia: ");
        String title = sc.nextLine();

        System.out.println("Digite as palavras-chave separadas por vírgula: ");
        String keyWords = sc.nextLine();

        List<Notice> filteredNotices = noticeController.filterNotices(
                localDate,
                title,
                keyWords,
                notices
        );

        ListNotices(filteredNotices);
    }
    private void createFavorites()
    {
        System.out.println("Digite o id da noticia que deseja adicionar aos favoritos: ");
        String id = sc.nextLine();
        Optional<Notice> matchedNotice = notices.stream()
                .filter(notice -> notice.getId().equals(id))
                .findFirst();

        Optional<Notice> matchedNoticeFavorite = favorites.stream()
                .filter(notice -> notice.getId().equals(id))
                .findFirst();

        if(!matchedNotice.isPresent())
        {
            System.out.println("Noticia não encontrada.");
            createFavorites();
        }
        if(matchedNoticeFavorite.isPresent())
        {
            System.out.println("Noticia já está nos favoritos.");
            return;
        }
        favorites.add(matchedNotice.get());
    }
    private void removeFavorites()
    {
        System.out.println("Digite o id da noticia que deseja remover dos favoritos: ");
        String id = sc.nextLine();
        Optional<Notice> matchedNotice = favorites.stream()
                .filter(notice -> notice.getId().equals(id))
                .findFirst();

        if(!matchedNotice.isPresent())
        {
            System.out.println("Noticia não encontrada nos favoritos.");
            return;
        }
        favorites.remove(matchedNotice.get());
    }
    private void showMenu(User user) {
        System.out.println("Olá " + user.name + ", seja bem-vindo ao sistema!");
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
        System.out.println("[7] - Criar notícias para ver depois");
        System.out.println("[8] - Remover notícias para ver depois");
        System.out.println("[9] - Listar notícias para ver depois");

        // sair
        System.out.println("[0] - Sair");

        System.out.print("Digite a opção desejada: ");
        String option = sc.nextLine();
        switch (option) {
            case "1":
                // listar todas as noticias
                ListNotices(notices);
                break;
            case "2":
                // filtrar noticias
                filterNotices(notices);
                break;
            case "3":
                // criar favoritos
                createFavorites();
                break;
            case "4":
                // remover favoritos
                removeFavorites();
                break;
            case "5":
                // listar favoritos
                ListNotices(favorites);
                break;
            case "6":
                // marcar como lidas
                break;
            case "7":
                // criar para ver depois
                break;
            case "8":
                // remover para ver depois
                break;
            case "9":
                // listar para ver depois
                ListNotices(toSeeLater);
                break;
            case "10":
                // ordenar listas
                break;
            case "0":
                System.out.println("Saindo...");
                sc.close();
                return;
            default:
                System.out.println("Opção inválida, tente novamente.");
        }
        // chama o menu novamente
        showMenu(user);
    }
}
