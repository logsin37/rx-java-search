package org.logsin37.search.infra.repository.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.logsin37.search.domain.entity.SearchResult;
import org.logsin37.search.infra.constant.ErrorMessageEnum;
import org.logsin37.search.infra.repository.SearchResultRepository;
import org.logsin37.search.infra.util.ErrorMessageUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Search Result Repository Implement
 *
 * @author logsin37 2019/08/25 00:18
 */
@Repository
public class SearchResultRepositoryImpl implements SearchResultRepository {

    private final Map<String, SearchResult> memoryDb;

    public SearchResultRepositoryImpl(){
        memoryDb = new ConcurrentHashMap<>();
    }

    @Override
    public int insert(SearchResult searchResult) {
        Assert.notNull(searchResult, ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NOT_NULL, searchResult));
        Assert.isTrue(StringUtils.isNotBlank(searchResult.getBatchNumber()), ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NOT_EMPTY, "searchResult"));

        if(this.memoryDb.containsKey(searchResult.getBatchNumber())){
            return 0;
        }

        this.memoryDb.put(searchResult.getBatchNumber(), searchResult);
        return 1;
    }

    @Override
    public int update(SearchResult searchResult) {
        Assert.notNull(searchResult, ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NOT_NULL, searchResult));
        Assert.isTrue(StringUtils.isNotBlank(searchResult.getBatchNumber()), ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NOT_EMPTY, "searchResult"));

        if(!this.memoryDb.containsKey(searchResult.getBatchNumber())){
            return 0;
        }

        this.memoryDb.put(searchResult.getBatchNumber(), searchResult);

        return 1;
    }

    @Override
    public SearchResult find(String batchNumber) {
        Assert.isTrue(StringUtils.isNotBlank(batchNumber), ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NOT_EMPTY, "batchNumber"));
        return this.memoryDb.get(batchNumber);
    }
}
