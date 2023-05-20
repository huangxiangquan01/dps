package cn.xqhuang.dps.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author huangxq
 */
@Getter
@Setter
public class RangeQuery extends QueryCommand{

    private List<RangeValue>  rangeValues;

}
