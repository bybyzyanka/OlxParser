package ua.oleksiimartynov.olxparser.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import lombok.Getter;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    private static final String OLX_URL = "https://www.olx.ua",
        LINKS_XPATH = "//a[@class='css-1bbgabe']",
        TITLE_XPATH = "//h1[@class='css-r9zjja-Text eu5v0x0']",
        PRICE_XPATH = "//h3[@class='css-okktvh-Text eu5v0x0']";

    @Getter
    private List<Item> items = new ArrayList<>();

    public void parse(String request) {
        request = request.toLowerCase().replace(" ", "-");
        HtmlPage searchPage = getDocument(OLX_URL + "/d/uk/list/q-" + request + "/");
        List<HtmlAnchor> links = searchPage.getByXPath(LINKS_XPATH);
        for(HtmlAnchor link : links) {
            HtmlPage itemPage = getDocument(OLX_URL + link.getHrefAttribute());
            String title = ((HtmlElement) itemPage.getFirstByXPath(TITLE_XPATH))
                    .asNormalizedText();

            if(!Arrays.stream(request.split("-")).anyMatch(value -> title.toLowerCase().contains(value)))
                continue;

            int price = parsePrice(((HtmlElement) itemPage.getFirstByXPath(PRICE_XPATH))
                    .asNormalizedText());

            items.add(new Item(title, OLX_URL + link.getHrefAttribute(), price));
        }
    }

    private int parsePrice(String price) {
        return Integer.parseInt(price.replaceAll("\\D", ""));
    }

    private HtmlPage getDocument(String url) {
        HtmlPage page;
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);
            page = webClient.getPage(url);
        } catch (Exception exception) {
            throw new RuntimeException(new Exception("Error while getting a page by URL: " + url));
        }

        return page;
    }
}
