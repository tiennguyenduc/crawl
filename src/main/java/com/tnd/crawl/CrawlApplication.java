package com.tnd.crawl;

import com.tnd.crawl.domain.Keyword;
import com.tnd.crawl.domain.Link;
import com.tnd.crawl.domain.News;
import com.tnd.crawl.domain.Source;
import com.tnd.crawl.repository.KeywordRepository;
import com.tnd.crawl.repository.LinkRepository;
import com.tnd.crawl.repository.NewsRepository;
import com.tnd.crawl.service.CrawlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
public class CrawlApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CrawlApplication.class, args);
    }

    @Autowired
    private CrawlService crawlService;

    @Autowired
    private LinkRepository linkService;

    @Autowired
    private NewsRepository newsService;

    @Autowired
    private KeywordRepository keywordRepository;

    @Override
    public void run(String... strings) throws Exception {

        for (Source source : Source.values()) {
            List<String> keywords = keywordRepository.findAllByLanguage(source.getLanguage())
                    .stream().map(Keyword::getKeyword).collect(Collectors.toList());

            List<Link> links = crawlService.crawlLinks(source);
            for (Link link : links) {
                log.info("# BEGIN crawl {}", link.getUrl());
                link = linkService.save(link);
                News news = crawlService.crawlPages(link, keywords);
                if (news != null) {
                    newsService.save(news);
                }
            }
        }
    }
}
