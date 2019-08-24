package org.logsin37.search.infra.util;

import java.util.UUID;

import org.logsin37.search.infra.constant.BaseConstant;

/**
 * Uuid Utils
 *
 * @author logsin37 2019/08/24 23:03
 */
public class UuidUtil {

    private UuidUtil(){
        throw new IllegalStateException();
    }

    /**
     * get a random uuid
     *
     * @return random uuid
     */
    public static String getUuid(){
        return UUID.randomUUID().toString();
    }

    /**
     * get a random uuid without hyphen
     *
     * @return random uuid without hyphen
     */
    public static String getUuidWithoutHyphen(){
        return UuidUtil.getUuid().replace(BaseConstant.Symbol.HYPHEN, BaseConstant.Symbol.EMPTY);
    }

}
