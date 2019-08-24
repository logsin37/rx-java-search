package org.logsin37.search.app.service.impl;

import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.logsin37.search.app.service.SearchEngine;
import org.logsin37.search.domain.entity.SearchResult;
import org.logsin37.search.domain.entity.WebSearchEngine;
import org.logsin37.search.infra.constant.ErrorMessageEnum;
import org.logsin37.search.infra.repository.SearchResultRepository;
import org.logsin37.search.infra.util.ErrorMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import io.reactivex.internal.operators.observable.ObservableFromIterable;
import io.reactivex.schedulers.Schedulers;

/**
 * Search Engine Implement
 *
 * @author logsin37 2019/08/24 22:54
 */
public class SearchEngineImpl implements SearchEngine {

    private final Set<WebSearchEngine> webSearchEngines;
    private final SearchResultRepository searchResultRepository;
    private final RestTemplate restTemplate;
    private final Logger logger;

    public SearchEngineImpl(Set<WebSearchEngine> webSearchEngines, SearchResultRepository searchResultRepository, RestTemplate restTemplate){
        Assert.notNull(webSearchEngines, ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NOT_NULL, "webSearchEngines"));
        Assert.isTrue(CollectionUtils.isNotEmpty(webSearchEngines), ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NOT_EMPTY, "webSearchEngines"));
        this.webSearchEngines = webSearchEngines;
        this.searchResultRepository = searchResultRepository;
        this.restTemplate = restTemplate;
        this.logger = LoggerFactory.getLogger(SearchEngineImpl.class);
    }

    @Override
    public SearchResult doSearch(String keyword) {
        var searchResult = new SearchResult().setKeyword(keyword);
        this.searchResultRepository.insert(searchResult);
        this.doSearchByReactive(keyword, searchResult.getBatchNumber());
        return searchResult;
    }

    /**
     * do search by reactive
     *
     * @param keyword keyword
     * @param batchNumber batch number
     */
    private void doSearchByReactive(String keyword, String batchNumber){
        new ObservableFromIterable<>(this.webSearchEngines)
                // schedule
                .subscribeOn(Schedulers.single())
                .observeOn(Schedulers.io())
                // map engine info to search dto
                .map(engine -> {
                    var dto = new ReactiveSearchDto();
                    dto.engineName = engine.getName();
                    dto.searchCommand = engine.getSearchCommand();
                    return dto;
                })
                // do search
                .map(dto -> {
                    try{
                        dto.page = this.restTemplate.getForEntity(dto.searchCommand, String.class, keyword).getBody();
                    }catch (Exception ex){
                        var errorMessage = ex.getMessage();
                        this.logger.error(errorMessage, ex);
                        dto.errorFlag = true;
                        dto.message = errorMessage;
                    }
                    return dto;
                })
                // process error message
                .map(dto -> {
                    if(dto.errorFlag){
                        dto.page = ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.SEARCH_ERROR, dto.message);
                    }
                    return dto;
                })
                .retry(3)
                // record result to repository
                .observeOn(Schedulers.computation())
                .subscribe(
                        // on next
                        dto -> {
                            var result = this.searchResultRepository.find(batchNumber);
                            result.addWebSearchResult(dto.engineName, dto.page);
                            this.searchResultRepository.update(result);
                        },
                        // on error
                        ex -> {
                            logger.error(ex.getMessage(), ex);
                            var result = this.searchResultRepository.find(batchNumber);
                            result.recordError(ex.getMessage());
                            this.searchResultRepository.update(result);
                        }
                )
        ;
    }

    /**
     * <p>
     * Reactive Search Dto
     * </p>
     *
     * @author gaokuo.dai@hand-china.com 2019/08/25
     */
    private class ReactiveSearchDto{
        /**
         * 错误标志
         */
        private boolean errorFlag = false;
        /**
         * (错误)消息
         */
        private String message;
        /**
         * web搜索引擎名称
         */
        private String engineName;
        /**
         * web搜索引擎搜索指令
         */
        private String searchCommand;
        /**
         * 搜索结果页面
         */
        private String page;
    }
}
