package com.TrabalhoOOP.Mappers.Ibge;

import com.TrabalhoOOP.Entities.Editory;
import com.TrabalhoOOP.Entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsMapper {
    public static Product jsonToProduct(String data){
        return new Product(data);
    }

    public static List<Product> jsonToProducts(String data) {
        List<Product> editorials = new ArrayList<>();

        for (String editoryText : data.split(", ")) {
            editorials.add(jsonToProduct(editoryText.trim()));
        }

        return editorials;
    }
}
