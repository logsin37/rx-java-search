package org.logsin37.search.infra.constant;

/**
 * Base Constants
 *
 * @author logsin37 2019/08/24 22:58
 */
public class BaseConstant {

    private BaseConstant(){
        throw new IllegalStateException();
    }

    /**
     * Base Symbol
     *
     * @author gaokuo.dai@hand-china.com 2019/08/24
     */
    public static class Symbol{
        private Symbol(){
            throw new IllegalStateException();
        }
        /**
         * ""
         */
        public static final String EMPTY = "";
        /**
         * " "
         */
        public static final String SPACE = " ";
        /**
         * "-"
         */
        public static final String HYPHEN = "-";
    }

}
