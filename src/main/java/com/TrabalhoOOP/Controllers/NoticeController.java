package com.TrabalhoOOP.Controllers;


import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Interfaces.INoticesApi;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NoticeController {
    private final INoticesApi noticesApi;
    private final List<Notice> notices = new ArrayList<>();
    public NoticeController(INoticesApi noticesApi) {
        this.noticesApi = noticesApi;
    }
    public List<Notice> getAllNotices(int qtd) throws IOException, InterruptedException {
        notices.clear();
        notices.addAll(noticesApi.getAllNotices(qtd));
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
}
