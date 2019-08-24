package org.logsin37.search.domain.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.logsin37.search.config.WebSearchEngines;
import org.logsin37.search.infra.constant.ErrorMessageEnum;
import org.logsin37.search.infra.util.ErrorMessageUtil;
import org.logsin37.search.infra.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Search Result
 *
 * @author logsin37 2019/08/24 22:48
 */
public class SearchResult {

    /**
     * 批次号
     */
    private String batchNumber;
    /**
     * 搜索关键字
     */
    private String keyword;
    /**
     * 已完成标志
     */
    private boolean readyFlag;
    /**
     * 处理进度(0~1)
     */
    private double progressRate;
    /**
     * 处理成功标志
     */
    private boolean successFlag;
    /**
     * 异常信息
     */
    private String errorMessage;
    /**
     * 结果页面集
     */
    private Map<WebSearchEngine, String> resultPool;
    /**
     * logger
     */
    private Logger logger;

    public SearchResult(){
        this.batchNumber = UuidUtil.getUuidWithoutHyphen();
        this.readyFlag = false;
        this.progressRate = 0d;
        this.resultPool = new ConcurrentHashMap<>();
        this.successFlag = true;
        this.logger = LoggerFactory.getLogger(SearchResult.class);
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    /**
     * gain search html page by engine name
     *
     * @param engineName engine name
     * @return search html page
     */
    public String gainPageByEngineName(@NotNull String engineName){
        if(!this.successFlag){
            if (logger.isWarnEnabled()) {
                logger.warn("can not add web search result because this result is on error");
            }
            return null;
        }
        Assert.isTrue(StringUtils.isNotBlank(engineName), ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NOT_EMPTY, "engineName"));
        var webSearchEngine = WebSearchEngines.getWebSearchEngineMap().get(engineName);
        Assert.notNull(webSearchEngine, ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NO_SUCH_ENGINE, engineName));
        return this.resultPool.get(webSearchEngine);
    }

    /**
     * add web search result to pool
     *
     * @param engineName engine name
     * @param page result html page
     * @return this
     */
    public SearchResult addWebSearchResult(String engineName, String page){
        if(!this.successFlag){
            if (logger.isWarnEnabled()) {
                logger.warn("can not add web search result because this result is on error");
            }
            return this;
        }
        var engineMap = WebSearchEngines.getWebSearchEngineMap();
        var webSearchEngine = WebSearchEngines.getWebSearchEngineMap().get(engineName);
        Assert.notNull(webSearchEngine, ErrorMessageUtil.renderErrorMessage(ErrorMessageEnum.NO_SUCH_ENGINE, engineName));

        this.resultPool.put(webSearchEngine, page);

        var totalCount = engineMap.size();
        var currentCount = this.resultPool.size();
        this.progressRate = new BigDecimal(String.valueOf(currentCount)).divide(new BigDecimal(String.valueOf(totalCount)), 2, RoundingMode.HALF_UP).doubleValue();
        if(totalCount == currentCount){
            this.readyFlag = true;
        }
        return this;
    }

    /**
     * record Error Information
     *
     * @param errorMessage error message
     * @return this
     */
    public SearchResult recordError(String errorMessage){
        this.successFlag = false;
        this.errorMessage = errorMessage;
        return this;
    }

    public String getKeyword() {
        return keyword;
    }
    public SearchResult setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public boolean getReadyFlag() {
        return readyFlag;
    }

    public double getProgressRate() {
        return progressRate;
    }

    public boolean getSuccessFlag() {
        return successFlag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Map<WebSearchEngine, String> getResultPool() {
        return resultPool;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SearchResult{");
        sb.append("batchNumber='").append(batchNumber).append('\'');
        sb.append(", keyword='").append(keyword).append('\'');
        sb.append(", readyFlag=").append(readyFlag);
        sb.append(", progressRate=").append(progressRate);
        sb.append(", successFlag=").append(successFlag);
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append(", resultPool=").append(resultPool);
        sb.append('}');
        return sb.toString();
    }
}
