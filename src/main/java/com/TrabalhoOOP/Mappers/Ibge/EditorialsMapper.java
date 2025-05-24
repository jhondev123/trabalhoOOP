package com.TrabalhoOOP.Mappers.Ibge;

import com.TrabalhoOOP.Entities.Editory;
import com.TrabalhoOOP.Entities.Notice;
import com.TrabalhoOOP.Enums.NoticeType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditorialsMapper {
    public static Editory jsonToEditory(String data){
        return new Editory(data);
    }

    public static List<Editory> jsonToEditorials(String data) {
        List<Editory> editorials = new ArrayList<>();

        for (String editoryText : data.split(";")) {
            editorials.add(jsonToEditory(editoryText.trim()));
        }

        return editorials;
    }
}
