package cn.xqhuang.dps.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DataGrid<T> {

    private Integer current;//当前页面号

    private Integer rowCount;//每页行数

    private Long total;//总行数

    private List<T> rows;

}
