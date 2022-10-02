package ua.oleksiimartynov.olxparser.main;

import java.util.Scanner;

public class OlxParser {

    public static void main(String[] args) {
        System.out.println("Write a request to search on olx.ua:");
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        parser.parse(scanner.nextLine());
        parser.getItems().forEach(Item::show);
    }
}
