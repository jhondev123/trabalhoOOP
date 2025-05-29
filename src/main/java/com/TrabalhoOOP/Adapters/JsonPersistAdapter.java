package com.TrabalhoOOP.Adapters;

import com.TrabalhoOOP.Entities.Entity;
import com.TrabalhoOOP.Interfaces.IPersist;

public class JsonPersistAdapter implements IPersist {
    @Override
    public void save(Entity entity) throws Exception {

    }

    @Override
    public Entity load() throws Exception {
        return null;
    }

    @Override
    public boolean delete() throws Exception {
        return false;
    }
}
