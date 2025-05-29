package com.TrabalhoOOP.Repository;

import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Interfaces.IPersist;

public class NoticeRepository {
    IPersist persist;
    public NoticeRepository(IPersist persist) {
        this.persist = persist;
    }
    public void saveNotice(Notice notice) throws Exception {
        persist.save(notice);
    }
}
