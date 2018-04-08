package com.tnd.crawl.service;

import com.tnd.crawl.domain.Link;
import com.tnd.crawl.domain.News;
import com.tnd.crawl.domain.Source;

import java.io.IOException;
import java.util.List;

public interface CrawlService {

    List<Link> crawlLinks(Source source) throws IOException;

    News crawlPages(Link link, List<String> keywords) throws IOException;
}
