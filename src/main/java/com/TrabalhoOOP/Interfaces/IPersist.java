package com.TrabalhoOOP.Interfaces;

import com.TrabalhoOOP.Entities.Entity;

public interface IPersist {
    void save(Entity entity) throws Exception;

    Entity load() throws Exception;

    boolean delete() throws Exception;
}
