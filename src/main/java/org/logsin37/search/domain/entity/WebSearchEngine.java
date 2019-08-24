package org.logsin37.search.domain.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Web Search Engine
 *
 * @author logsin37 2019/08/24 23:09
 */
public class WebSearchEngine {

    /**
     * 引擎名称
     */
    private String name;
    /**
     * 搜索指令
     */
    private String searchCommand;

    public String getName() {
        return name;
    }

    public WebSearchEngine setName(String name) {
        this.name = name;
        return this;
    }

    public String getSearchCommand() {
        return searchCommand;
    }

    public WebSearchEngine setSearchCommand(String searchCommand) {
        this.searchCommand = searchCommand;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WebSearchEngine that = (WebSearchEngine) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .append(searchCommand, that.searchCommand)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(searchCommand)
                .toHashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebSearchEngine{");
        sb.append("name='").append(name).append('\'');
        sb.append(", searchCommand='").append(searchCommand).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
