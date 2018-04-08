package com.tnd.crawl.repository;

import com.tnd.crawl.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Integer> {
    List<Keyword> findAllByLanguage(String language);
}
