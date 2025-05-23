package com.TrabalhoOOP.Controllers;


import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Interfaces.INoticesApi;

import java.util.List;

public class NoticeController {
    private final INoticesApi noticesApi;
    public NoticeController(INoticesApi noticesApi) {
        this.noticesApi = noticesApi;
    }
    public List<Notice> getAllNotices() {
        return noticesApi.getAllNotices();
    }
}
