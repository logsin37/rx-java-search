package org.logsin37.search.config;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.logsin37.search.domain.entity.WebSearchEngine;

/**
 * Web Search Engines registered in application.yml
 *
 * @author logsin37 2019/08/25 00:08
 */
public class WebSearchEngines {

    private static Set<WebSearchEngine> webSearchEngineSet;
    private static Map<String, WebSearchEngine> webSearchEngineMap;

    public WebSearchEngines(Set<WebSearchEngine> webSearchEngineSet){
        WebSearchEngines.webSearchEngineSet = Collections.unmodifiableSet(webSearchEngineSet);
        WebSearchEngines.webSearchEngineMap = webSearchEngineSet.stream().collect(Collectors.toMap(WebSearchEngine::getName, Function.identity(), (a, b) -> b));
    }

    /**
     * get web search engines registered in application.yml
     *
     * @return Web Search Engine Set
     */
    public static Set<WebSearchEngine> getWebSearchEngineSet() {
        return webSearchEngineSet;
    }

    /**
     * get web search engines registered in application.yml
     *
     * @return Web Search Engine Map, key is engine name, value is engine
     */
    public static Map<String, WebSearchEngine> getWebSearchEngineMap() {
        return webSearchEngineMap;
    }
}
