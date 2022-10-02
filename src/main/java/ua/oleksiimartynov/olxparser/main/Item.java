package ua.oleksiimartynov.olxparser.main;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class Item {

    private String title, url;
    private int price;

    public void show() {
        System.out.println("Title: " + getTitle());
        System.out.println("Price: " + getPrice());
        System.out.println("URL: " + getUrl());
    }
}
