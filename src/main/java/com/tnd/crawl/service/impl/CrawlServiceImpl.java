package com.tnd.crawl.service.impl;

import com.tnd.crawl.domain.Link;
import com.tnd.crawl.domain.News;
import com.tnd.crawl.domain.Source;
import com.tnd.crawl.repository.KeywordRepository;
import com.tnd.crawl.service.CrawlService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrawlServiceImpl implements CrawlService {

    @Autowired
    private KeywordRepository keywordRepository;

    @Override
    public List<Link> crawlLinks(Source source) throws IOException {
        Document page = Jsoup.connect(source.getUrl()).get();

        Elements linkElements = page.select(source.getQuery());

        return linkElements
                .stream()
                .map(linkElement -> new Link(source.getUrl() + linkElement.attr("href"), source))
                .distinct()
//                .sorted(Comparator.comparingInt(l -> l.getUrl().length()))
                .collect(Collectors.toList());
    }

    @Override
    public News crawlPages(Link link, List<String> keywords) throws IOException {
        News news = new News();
        news.url = link.getUrl();

//        Document page = Jsoup.connect(link.getUrl()).get();
//        Elements elements = page.select("[data-field]");
//        elements.forEach(element -> {
//            try {
//                String fieldName = element.attr("data-field");
//                Field field = News.class.getField(fieldName);
//
//                if ("body".equals(fieldName)) {
//                    StringBuilder stringBuilder = new StringBuilder();
//                    element.select("p").forEach(p -> stringBuilder.append(p.text()));
//                    field.set(news, stringBuilder.toString());
//                } else {
//                    field.set(news, element.text());
//                }
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        });

        Document doc = Jsoup.connect(link.getUrl()).get();

        // check article
        Elements page = doc.select("article");
        if (page.isEmpty()) {
            return null;
        }

//        Elements page = Jsoup.connect(link.getUrl()).get().select("[data-field]");
        List<String> fields = link.getSource().getFields();

        int index = 0;
        news.title = page.select(fields.get(index++)).text();
        news.createddate = page.select(fields.get(index++)).text();
        news.author = page.select(fields.get(index++)).text();
        news.source = page.select(fields.get(index++)).text();

        StringBuilder stringBuilder = new StringBuilder();
        page.select(fields.get(index)).select("p").forEach(p -> stringBuilder.append(p.text()));
        news.body = stringBuilder.toString();

        // check keywords
        for(String keyword : keywords) {
            if (news.title.toLowerCase().contains(keyword) || news.body.toLowerCase().contains(keyword)) {
                return news;
            }
        }

        return null;
    }
}
