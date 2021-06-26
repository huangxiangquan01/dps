package cn.xqhuang.dps.utils;

import java.util.HashMap;
import java.util.Map;

public class QueryParamBuilder {

    private static final String LESS_THEN_KEY_FORMAT = "%sLessThan";
    private static final String MORE_THEN_KEY_FORMAT = "%sMoreThan";
    private static final String SHOW_KEY_FORMAT = "%sShow";
    protected HashMap<String, Object> params = new HashMap<>();

    private QueryParamBuilder() {

    }

    public static QueryParamBuilder get() {
        return new QueryParamBuilder();
    }


    public QueryParamBuilder lessThan(String par, Object value) {
        return param(String.format(LESS_THEN_KEY_FORMAT, par), value);
    }

    public QueryParamBuilder removeLessThan(String par) {
        return remove(String.format(LESS_THEN_KEY_FORMAT, par));
    }

    public QueryParamBuilder moreThan(String par, Object value) {
        return param(String.format(MORE_THEN_KEY_FORMAT, par), value);
    }

    public QueryParamBuilder removeMoreThan(String par) {
        return remove(String.format(MORE_THEN_KEY_FORMAT, par));
    }

    public QueryParamBuilder show(String par) {
        return param(String.format(SHOW_KEY_FORMAT, par), true);
    }

    public QueryParamBuilder removeShow(String par) {
        return remove(String.format(SHOW_KEY_FORMAT, par));
    }

    public QueryParamBuilder param(String par, Object value) {
        params.put(par, value);
        return this;
    }

    public QueryParamBuilder remove(String par) {
        params.remove(par);
        return this;
    }


    public Map<String, Object> build() {
        return params;
    }

}
