package org.logsin37.search.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Search Configuration Properties
 *
 * @author logsin37 2019/08/24 23:28
 */
@ConfigurationProperties(prefix = SearchConfigurationProperties.SEARCH_CONFIGURATION_PROPERTIES_PREFIX)
public class SearchConfigurationProperties {

    public static final String SEARCH_CONFIGURATION_PROPERTIES_PREFIX = "search";

    /**
     * web search engine setting
     */
    private Map<String, String> engines = new HashMap<>();

    public Map<String, String> getEngines() {
        return engines;
    }

    public SearchConfigurationProperties setEngines(Map<String, String> engines) {
        this.engines = engines;
        return this;
    }
}
