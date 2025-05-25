package com.TrabalhoOOP.Controllers;


import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Interfaces.INoticesApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NoticeController {
    private final INoticesApi noticesApi;
    private final List<Notice> notices = new ArrayList<>();
    public NoticeController(INoticesApi noticesApi) {
        this.noticesApi = noticesApi;
    }
    public List<Notice> getAllNotices() throws IOException, InterruptedException {
        notices.clear();
        notices.addAll(noticesApi.getAllNotices());
        return notices;
    }
}
