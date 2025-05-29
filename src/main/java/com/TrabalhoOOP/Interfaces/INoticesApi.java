package com.TrabalhoOOP.Interfaces;


import com.TrabalhoOOP.Entities.Notice;

import java.io.IOException;
import java.util.List;

public interface INoticesApi {
    public List<Notice> getAllNotices(int qtd) throws IOException, InterruptedException,Exception;
}
