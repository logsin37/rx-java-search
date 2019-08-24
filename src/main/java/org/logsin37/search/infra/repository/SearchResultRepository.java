package org.logsin37.search.infra.repository;

import org.logsin37.search.domain.entity.SearchResult;

/**
 * Search Result Repository
 *
 * @author logsin37 2019/08/25 00:18
 */
public interface SearchResultRepository {

    /**
     * insert a search result
     *
     * @param searchResult search result
     * @return insert count
     */
    int insert(SearchResult searchResult);

    /**
     * update a search result
     *
     * @param searchResult search result
     * @return update count
     */
    int update(SearchResult searchResult);

    /**
     * find a search result by batch number
     *
     * @param batchNumber batch number
     * @return find result
     */
    SearchResult find(String batchNumber);

}
