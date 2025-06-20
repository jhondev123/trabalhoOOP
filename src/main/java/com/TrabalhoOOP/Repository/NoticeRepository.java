package com.TrabalhoOOP.Repository;

import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Interfaces.INoticesApi;
import com.TrabalhoOOP.Interfaces.IPersist;
import com.TrabalhoOOP.Mappers.Ibge.NoticeMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class NoticeRepository {
    IPersist persist;
    INoticesApi noticesApi;
    Gson gson;
    public NoticeRepository(IPersist persist, INoticesApi noticesApi,Gson gson) {
        this.persist = persist;
        this.noticesApi = noticesApi;
        this.gson = gson;
    }
    public void saveNotice(String json) throws Exception {
        persist.save(json);
    }
    public List<Notice> loadNotices() throws Exception {
        String json = persist.load();
        if (json == null || json.isEmpty()) {
            throw new Exception("No data found");
        }
        Type responseType = new TypeToken<Map<String, Object>>(){}.getType();

        Map<String, Object> data = gson.fromJson(json, responseType);
        List<Map<String, Object>> items = (List<Map<String, Object>>) data.get("items");
        return NoticeMapper.jsonToNotices(items.toArray());
    }
}
