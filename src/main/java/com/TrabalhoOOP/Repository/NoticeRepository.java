package com.TrabalhoOOP.Repository;

import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Interfaces.INoticesApi;
import com.TrabalhoOOP.Interfaces.IPersist;

import java.util.List;

public class NoticeRepository {
    IPersist persist;
    INoticesApi noticesApi;
    public NoticeRepository(IPersist persist, INoticesApi noticesApi) {
        this.persist = persist;
        this.noticesApi = noticesApi;
    }
    public void saveNotice(String json) throws Exception {
        persist.save(json);
    }
    public List<Notice> loadNotices() throws Exception {
        String json = persist.load();
        if (json == null || json.isEmpty()) {
            throw new Exception("No data found");
        }
        return noticesApi.getAllNotices(10);
    }
}
