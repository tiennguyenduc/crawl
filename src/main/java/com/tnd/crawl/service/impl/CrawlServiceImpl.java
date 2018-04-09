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
import org.springframework.stereotype.Service;

import java.io.IOException;
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
                .map(linkElement -> new Link(source.getBaseUrl() + linkElement.attr("href"), source))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public News crawlPages(Link link, List<String> keywords) throws IOException {
        News news = new News();
        news.url = link.getUrl();

        Document page = Jsoup.connect(link.getUrl()).get();

        // check article
        if (link.getSource() == Source.SOHA && page.select("article").isEmpty()) {
            return null;
        }
        if (link.getSource() == Source.XINHUANET && page.select("div.h-news").isEmpty()) {
            return null;
        }

        List<String> fields = link.getSource().getFields();

        int index = 0;
        news.title = page.select(fields.get(index++)).text();
        news.publishedAt = page.select(fields.get(index++)).text();
        news.author = page.select(fields.get(index++)).text();
        news.copyright = page.select(fields.get(index++)).text();

        StringBuilder stringBuilder = new StringBuilder();
        page.select(fields.get(index)).select("p").forEach(p -> stringBuilder.append(p.text()));
        news.content = stringBuilder.toString();

        news.source = link.getSource();

        // check keywords
//        for(String keyword : keywords) {
//            if (news.title.toLowerCase().contains(keyword) || news.content.toLowerCase().contains(keyword)) {
//                return news;
//            }
//        }
//
//        return null;
        return news;
    }
}
