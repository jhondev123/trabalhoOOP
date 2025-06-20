package com.TrabalhoOOP.Adapters;

import com.TrabalhoOOP.Interfaces.IPersist;
import com.TrabalhoOOP.Mappers.Ibge.NoticeMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class JsonPersistAdapter implements IPersist {
    private static final String FILE_PATH = "C:\\Java\\trabalhoOOP\\src\\main\\java\\com\\TrabalhoOOP\\Data\\notices.json";

    private Gson gson;
    public JsonPersistAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void save(String json) throws Exception {
        Path filePath = Paths.get(FILE_PATH);
        Files.createDirectories(filePath.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(json);
        }
    }

    @Override
    public String load() throws Exception {
        Path filePath = Paths.get(FILE_PATH);
        File file = filePath.toFile();

        if (!file.exists()) {
            throw new Exception("Arquivo de dados não encontrado.");
        }

        return new String(Files.readAllBytes(filePath)).trim();
    }

}
