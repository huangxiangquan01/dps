package cn.xqhuang.dps.excel;

import cn.xqhuang.dps.excel.annotation.CellGroup;
import cn.xqhuang.dps.excel.annotation.RowCell;
import cn.xqhuang.dps.excel.annotation.Rows;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public class ReportModelPropertyDefinitionWrapper {
    private ReportModelPropertyDefinition targetPropertyDefinition;
    private String targetGroupName;
    private int targetIndex;

    public ReportModelPropertyDefinitionWrapper(final ReportModelPropertyDefinition targetPropertyDefinition,
                                                final String targetGroupName,
                                                final int targetIndex) {
        this.targetPropertyDefinition = targetPropertyDefinition;
        this.targetGroupName = targetGroupName;
        this.targetIndex = targetIndex;
    }

    public Class<?> getClazz() {
        return this.targetPropertyDefinition.getClazz();
    }

    public Field getField() {
        return this.targetPropertyDefinition.getField();
    }

    public Rows getRows() {
        return this.targetPropertyDefinition.getRows();
    }

    public RowCell getRowCell() {
        return this.targetPropertyDefinition.getRowCell();
    }

    public CellGroup getCellGroup() {
        return this.targetPropertyDefinition.getCellGroup(targetGroupName);
    }

    public String getGroupName() {
        return this.targetGroupName;
    }

    public int getIndex() {
        return this.targetIndex;
    }

    public String getFieldName() {
        return targetPropertyDefinition.getFieldName();
    }

    public String getTitle() {
        CellGroup cellGroup = getCellGroup();
        if (Objects.nonNull(cellGroup)) {
            if (!StringUtils.isEmpty(cellGroup.title())) {
                return cellGroup.title();
            }
        }

        RowCell rowCell = getRowCell();
        return rowCell.title();
    }
}
