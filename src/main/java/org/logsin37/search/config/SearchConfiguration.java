package org.logsin37.search.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.logsin37.search.app.service.SearchEngine;
import org.logsin37.search.app.service.impl.SearchEngineImpl;
import org.logsin37.search.domain.entity.WebSearchEngine;
import org.logsin37.search.infra.constant.ErrorMessageEnum;
import org.logsin37.search.infra.net.http.RestTemplateHeaderInterceptor;
import org.logsin37.search.infra.repository.SearchResultRepository;
import org.logsin37.search.infra.util.ErrorMessageUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/**
 * Search Configuration
 *
 * @author logsin37 2019/08/24 16:51
 */
@Configuration
@EnableConfigurationProperties(SearchConfigurationProperties.class)
public class SearchConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestTemplateHeaderInterceptor restTemplateHeaderInterceptor(){
        return new RestTemplateHeaderInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateHeaderInterceptor restTemplateHeaderInterceptor){
        var restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(restTemplateHeaderInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSearchEngines webSearchEngines(SearchConfigurationProperties properties){
        var engineSet = properties.getEngines()
                .entrySet()
                .stream()
                .map(entry -> new WebSearchEngine().setName(entry.getKey()).setSearchCommand(entry.getValue()))
                .collect(Collectors.toSet());
        return new WebSearchEngines(engineSet);
    }

    @Bean
    @ConditionalOnMissingBean
    public SearchEngine searchEngine(WebSearchEngines webSearchEngines, SearchResultRepository searchResultRepository, RestTemplate restTemplate){
        Assert.notNull(webSearchEngines, ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NOT_NULL,"webSearchEngines"));
        return new SearchEngineImpl(WebSearchEngines.getWebSearchEngineSet(), searchResultRepository, restTemplate);
    }

}
