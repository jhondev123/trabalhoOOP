package com.TrabalhoOOP.Controllers;


import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Entities.User;
import com.TrabalhoOOP.Interfaces.INoticesApi;
import com.TrabalhoOOP.Repository.NoticeRepository;
import com.TrabalhoOOP.Utils.InputUtilities;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class NoticeController {
    private final INoticesApi noticesApi;
    private NoticeRepository repository;
    public List<Notice> notices = new ArrayList<Notice>();
    public List<Notice> favorites = new ArrayList<Notice>();
    public List<Notice> toSeeLater = new ArrayList<Notice>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final Scanner sc;
    private final int QUANTIDADE_REGISTROS = 10;

    public NoticeController(INoticesApi noticesApi, NoticeRepository repository, Scanner sc) {
        this.noticesApi = noticesApi;
        this.repository = repository;
        this.sc = sc;
    }

    public List<Notice> getAllNotices(int qtd) throws Exception {
        notices.clear();
        notices.addAll(noticesApi.getAllNotices(qtd));
        return notices;
    }
    public List<Notice> getLocalNotices() throws Exception {
        notices.clear();
        notices.addAll(repository.loadNotices());
        return notices;
    }
    public List<Notice> filterNotices(LocalDate date,String title, String keyWords, List<Notice> noticesToFilter) {
        List<Notice> filteredNotices = new ArrayList<>();
        for (Notice notice : noticesToFilter) {
            if (date != null) {
                if (notice.getPublishDate().isEqual(date)) {
                    filteredNotices.add(notice);
                }
            }
            if (title != null && !title.isEmpty()) {
                if (notice.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    filteredNotices.add(notice);
                }
            }
            if (keyWords != null && !keyWords.isEmpty()) {
                // pega as keywords passadas separadas por virgula, quebra elas, percorre e procura noticias com o
                // titulo que contenha alguma das keywords
                String[] keywords = keyWords.split(",");
                for (String keyword : keywords) {
                    if (notice.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                        filteredNotices.add(notice);
                    }
                }
            }
        }
        return filteredNotices;
    }
    public List<Notice> orderNotices(List<Notice> noticesToOrder, Boolean alphabet, Boolean publishDate, Boolean type) {
        Comparator<Notice> comparator = Comparator.comparing(n -> 0);

        if (alphabet) {
            comparator = comparator.thenComparing(Notice::getTitle);
        }
        if (publishDate) {
            comparator = comparator.thenComparing(Notice::getPublishDate);
        }
        if (type) {
            comparator = comparator.thenComparing(Notice::getNoticeType);
        }

        return noticesToOrder.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
    public List<Notice> getNotices(Scanner sc) throws Exception {
        System.out.println("Quer consultar notícias locais? (S/N) ");
        String option = InputUtilities.GetSorN(sc);
        if (option.equalsIgnoreCase("S")) {
            try {
                return getLocalNotices();
            } catch (Exception e) {
                System.out.println("Erro ao carregar notícias locais: " + e.getMessage());
            }
        }
        System.out.println("Consultando notícias do IBGE...");
        return getAllNotices(QUANTIDADE_REGISTROS);
    }
    public void listNotices(List<Notice> notices) {
        for (Notice notice : notices) {
            System.out.println(notice.toString());
        }
    }

    public void executeFilterNotices(List<Notice> notices) {
        System.out.println("Digite a data da noticia no formato dd/MM/yyyy, Caso não informe o filtro de data será ignorado: ");
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
                executeFilterNotices(notices);
                return;
            }
        }

        System.out.println("Digite o título da noticia: ");
        String title = sc.nextLine();

        System.out.println("Digite as palavras-chave separadas por vírgula: ");
        String keyWords = sc.nextLine();

        List<Notice> filteredNotices = filterNotices(
                localDate,
                title,
                keyWords,
                notices
        );

        listNotices(filteredNotices);
    }
    public void createFavorites() {
        System.out.println("Digite o id da noticia que deseja adicionar aos favoritos: ");
        int id = InputUtilities.GetId(sc);

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
    public void removeFavorites() {
        System.out.println("Digite o id da noticia que deseja remover dos favoritos: ");
        int id = InputUtilities.GetId(sc);

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
    public void MarkAsRead() {
        System.out.println("Digite o id da noticia que deseja marcar como lida: ");
        int id = InputUtilities.GetId(sc);

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
    public void listNoticesRead() {
        List<Notice> readNotices = notices.stream()
                .filter(notice -> notice.read)
                .toList();
        if(readNotices.isEmpty()) {
            System.out.println("Nenhuma notícia lida encontrada.");
            return;
        }
        listNotices(readNotices);
    }
    public void createToSeeLater(){
        System.out.println("Digite o id da noticia que deseja criar para ver depois: ");
        int id = InputUtilities.GetId(sc);

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
    public void removeToSeeLater() {
        System.out.println("Digite o id da noticia que deseja remover para ver depois: ");
        int id = InputUtilities.GetId(sc);

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
    public void listOrderNotices() {
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
        List<Notice> orderedNotices = orderNotices(noticesToOrder, alphabet, publishDate, type);
        listNotices(orderedNotices);
    }
}
