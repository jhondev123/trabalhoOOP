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
        Gson gson = new Gson();
        IPersist persistAdapter = new JsonPersistAdapter(gson);

        INoticesApi noticeApi = new IbgeNoticeAdapter(
                httpClient,
                gson,
                persistAdapter
        );
        NoticeRepository repository = new NoticeRepository(persistAdapter,noticeApi,gson);

        this.noticeController = new NoticeController(noticeApi,repository);
        this.userController = new UserController();
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
        notices = getNotices(sc);
        User user = createUser();
        System.out.println("Olá " + user.name + ", seja bem-vindo ao sistema!");
        showMenu();
    }

    private List<Notice> getNotices(Scanner sc) throws Exception {
        System.out.println("Quer consultar notícias locais? (S/N) ");
        String option = sc.nextLine();
        if (option.equalsIgnoreCase("S")) {
            try {
                return noticeController.getLocalNotices();
            } catch (Exception e) {
                System.out.println("Erro ao carregar notícias locais: " + e.getMessage());
            }
        }
        System.out.println("Consultando notícias do IBGE...");
        return noticeController.getAllNotices(3);
    }
    private void listNotices(List<Notice> notices) {
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

        listNotices(filteredNotices);
    }
    private void createFavorites() {
        System.out.println("Digite o id da noticia que deseja adicionar aos favoritos: ");
        int id = GetId(sc);


        Optional<Notice> matchedNotice = notices.stream()
                .filter(notice -> notice.getId() == id)
                .findFirst();

        Optional<Notice> matchedNoticeFavorite = favorites.stream()
                .filter(notice -> notice.getId() == id)
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
        System.out.println("Notícia adicionada aos favoritos.");
    }
    private void removeFavorites() {
        System.out.println("Digite o id da noticia que deseja remover dos favoritos: ");
        int id = GetId(sc);

        Optional<Notice> matchedNotice = favorites.stream()
                .filter(notice -> notice.getId() == id)
                .findFirst();

        if(!matchedNotice.isPresent())
        {
            System.out.println("Noticia não encontrada nos favoritos.");
            return;
        }
        favorites.remove(matchedNotice.get());
    }
    private void MarkAsRead() {
        System.out.println("Digite o id da noticia que deseja marcar como lida: ");
        int id = GetId(sc);

        Optional<Notice> matchedNotice = notices.stream()
                .filter(notice -> notice.getId() == id)
                .findFirst();

        if(!matchedNotice.isPresent())
        {
            System.out.println("Noticia não encontrada.");
            return;
        }
        matchedNotice.get().read = true;
        System.out.println("Notícia marcada como lida.");
    }
   private void listNoticesRead() {
         List<Notice> readNotices = notices.stream()
                .filter(notice -> notice.read)
                .toList();
         if(readNotices.isEmpty()) {
              System.out.println("Nenhuma notícia lida encontrada.");
              return;
         }
         listNotices(readNotices);
   }
   private void createToSeeLater(){
        System.out.println("Digite o id da noticia que deseja criar para ver depois: ");
       int id = GetId(sc);

       Optional<Notice> matchedNotice = notices.stream()
                .filter(notice -> notice.getId() == id)
                .findFirst();

        if(!matchedNotice.isPresent())
        {
            System.out.println("Noticia não encontrada.");
            return;
        }
        toSeeLater.add(matchedNotice.get());
        System.out.println("Notícia adicionada para ver depois.");
   }
   private void removeToSeeLater() {
        System.out.println("Digite o id da noticia que deseja remover para ver depois: ");
       int id = GetId(sc);

       Optional<Notice> matchedNotice = toSeeLater.stream()
                .filter(notice -> notice.getId() == id)
                .findFirst();

        if(!matchedNotice.isPresent())
        {
            System.out.println("Noticia não encontrada para ver depois.");
            return;
        }
        toSeeLater.remove(matchedNotice.get());
        System.out.println("Notícia removida para ver depois.");
   }
   private void listOrderNotices() {
       System.out.println("Qual lista deseja ordenar? ");
       System.out.println("[1] - Todas as notícias");
       System.out.println("[2] - As favoritas");
       System.out.println("[3] - As notícias para ver depois");
       System.out.println("[4] - Cancelar");
       String option = sc.nextLine();
       List<Notice> noticesToOrder;
       switch (option) {
              case "1":
                noticesToOrder = notices;
                break;
              case "2":
                noticesToOrder = favorites;
                break;
              case "3":
                noticesToOrder = toSeeLater;
                break;
              case "4":
                return;
              default:
                System.out.println("Opção inválida, tente novamente.");
                listOrderNotices();
                return;
       }

        boolean alphabet = false;
        boolean publishDate = false;
        boolean type = false;
        System.out.println("Deseja ordenar as notícias por ordem Alfabética? (S/N): ");
        String alphabetOption = sc.nextLine();
        if (alphabetOption.equalsIgnoreCase("S")) {
            alphabet = true;
        } else if (!alphabetOption.equalsIgnoreCase("N")) {
            System.out.println("Opção inválida, tente novamente.");
            listOrderNotices();
            return;
        }
        System.out.println("Deseja ordar as notícias por data de publicação? (S/N): ");
        String publishDateOption = sc.nextLine();
        if (publishDateOption.equalsIgnoreCase("S")) {
            publishDate = true;
        } else if (!publishDateOption.equalsIgnoreCase("N")) {
            System.out.println("Opção inválida, tente novamente.");
            listOrderNotices();
            return;
        }
        System.out.println("Deseja Ordenar pelo tipo da notícia? (S/N): ");
        String typeOption = sc.nextLine();
        if (typeOption.equalsIgnoreCase("S")) {
            type = true;
        } else if (!typeOption.equalsIgnoreCase("N")) {
            System.out.println("Opção inválida, tente novamente.");
            listOrderNotices();
            return;
        }
        List<Notice> orderedNotices = noticeController.orderNotices(noticesToOrder, alphabet, publishDate, type);
        listNotices(orderedNotices);
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
                listNotices(notices);
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
                listNotices(favorites);
                break;
            case "6":
                // marcar como lida
                MarkAsRead();
                break;
            case "7":
                // listas as lidas
                listNoticesRead();
                break;
            case "8":
                // criar para ver depois
                createToSeeLater();
                break;
            case "9":
                // remover para ver depois
                removeToSeeLater();
                break;
            case "10":
                // listar para ver depois
                listNotices(toSeeLater);
                break;
            case "11":
                // ordenar listas
                listOrderNotices();
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
// utils
    private int GetId(Scanner sc)
    {
        String id = sc.nextLine();
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("Id inválido, por favor digite um número.");
            return GetId(sc);
        }
    }
}
