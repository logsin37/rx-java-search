package org.logsin37.search.app.service;

import org.logsin37.search.domain.entity.SearchResult;

/**
 * Search Engine
 *
 * @author logsin37 2019/08/24 22:53
 */
public interface SearchEngine {

    /**
     * search by keyword
     *
     * @param keyword keyword
     * @return Search Result
     */
    SearchResult doSearch(String keyword);

}
