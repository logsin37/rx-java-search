package org.logsin37.search.api.controller;

import java.util.Optional;

import org.logsin37.search.app.service.SearchEngine;
import org.logsin37.search.domain.entity.SearchResult;
import org.logsin37.search.infra.repository.SearchResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Search Controller
 *
 * @author logsin37 2019/08/24 16:38
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchResultRepository searchResultRepository;
    private final SearchEngine searchEngine;

    @Autowired
    public SearchController(SearchResultRepository searchResultRepository, SearchEngine searchEngine) {
        this.searchResultRepository = searchResultRepository;
        this.searchEngine = searchEngine;
    }

    /**
     * search information by keyword
     *
     * @param keyword keyword
     * @return batch number
     */
    @PostMapping("/do")
    public ResponseEntity<SearchResult> demoTest(@RequestParam String keyword){
        return ResponseEntity.ok(this.searchEngine.doSearch(keyword));
    }

    /**
     * find search result by batch number
     *
     * @param batchNumber batch number
     * @return find result
     */
    @GetMapping("/result/{batchNumber}")
    public ResponseEntity<SearchResult> findSearchResultByBatchNumber(@PathVariable String batchNumber){
        return ResponseEntity.ok(this.searchResultRepository.find(batchNumber));
    }

    /**
     * find search result by batch number
     *
     * @param batchNumber batch number
     * @return find result
     */
    @GetMapping("/result/{batchNumber}/{engineName}")
    public ResponseEntity<String> findSearchResultPageByBatchNumberAndEngineName(@PathVariable String batchNumber, @PathVariable String engineName){
        return ResponseEntity.ok(
                Optional.ofNullable(this.searchResultRepository.find(batchNumber)).
                        map(result -> result.gainPageByEngineName(engineName))
                        .orElse(null)
        );
    }

}
