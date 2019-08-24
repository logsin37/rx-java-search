package org.logsin37.search.infra.constant;

/**
 * Error Message Enumerate
 *
 * @author logsin37 2019/08/24 23:13
 */
public enum ErrorMessageEnum {

    /**
     * "%s should not be null"
     */
    NOT_NULL("%s should not be null"),

    /**
     * "%s should not be empty"
     */
    NOT_EMPTY("%s should not be empty"),

    /**
     * "no such engineL %s
     */
    NO_SUCH_ENGINE("no such engine: %s"),

    /**
     * error during search
     */
    SEARCH_ERROR("<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
            "    <title>Document</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <h1>Some error occurred while do search</h1>\n" +
            "    <p>%s</p>\n" +
            "</body>\n" +
            "</html>");

    /**
     * 错误消息模板
     */
    private final String message;

    public String getMessage(){
        return this.message;
    }

    ErrorMessageEnum(String message){
        this.message = message;
    }

}
