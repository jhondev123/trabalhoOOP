package com.TrabalhoOOP.Interfaces;


public interface IPersist {
    void save(String json) throws Exception;

    String load() throws Exception;

}
