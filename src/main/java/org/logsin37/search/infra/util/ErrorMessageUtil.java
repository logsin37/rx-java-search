package org.logsin37.search.infra.util;

import org.logsin37.search.infra.constant.ErrorMessageEnum;

/**
 * Error Message Util
 *
 * @author logsin37 2019/08/24 23:18
 */
public class ErrorMessageUtil {

    private ErrorMessageUtil(){
        throw new IllegalStateException();
    }

    /**
     * render error message
     *
     * @param errorMessageEnum error message enumerate
     * @param objs parameters
     * @return rendered message
     */
    public static String renderErrorMessage(ErrorMessageEnum errorMessageEnum, Object... objs){
        return String.format(errorMessageEnum.getMessage(), objs);
    }

}
