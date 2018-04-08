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
        List<String> keywords = keywordRepository.findAllByLanguage("vi")
                .stream().map(Keyword::getKeyword).collect(Collectors.toList());

//        String url = "http://soha.vn/lukaku-tra-mon-no-old-trafford-giang-don-chi-mang-vao-man-city-20180405154713833.htm";
//        News news = crawlService.crawlPages(new Link(url, Source.SOHA), keywords);

//        String url = "http://www.xinhuanet.com/fortune/2018-04/08/c_1122648124.htm";
//        News news = crawlService.crawlPages(new Link(url, Source.XINHUANET));

//        String url = "http://www.scmp.com/news/china/article/2140756/can-president-xi-jinping-succeed-chinas-hawaii-where-deng-xiaoping-failed";
//        News news = crawlService.crawlPages(new Link(url, Source.SCMP));

//        log.info("# crawled: {}", news);
//        newsService.save(news);

        List<Link> links = crawlService.crawlLinks(Source.SOHA);
        links.forEach(link -> log.info("#{}", link.getUrl()));
        links.forEach(link -> {
            try {
                News news = crawlService.crawlPages(link, keywords);
                if (news != null) {
                    newsService.save(news);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
