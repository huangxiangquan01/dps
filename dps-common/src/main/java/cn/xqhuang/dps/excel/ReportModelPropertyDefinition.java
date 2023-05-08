package cn.xqhuang.dps.excel;

import cn.xqhuang.dps.excel.annotation.CellGroup;
import cn.xqhuang.dps.excel.annotation.RowCell;
import cn.xqhuang.dps.excel.annotation.Rows;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@Setter
public class ReportModelPropertyDefinition {
    private Class<?> clazz;
    private Field field;
    private RowCell rowCell;
    private Rows rows;

    public boolean containsGroup(String groupName) {
        if (Objects.isNull(rowCell) && Objects.isNull(rows)) {
            throw new RuntimeException("缺失RowCell或Rows属性，请检查");
        }
        if (StringUtils.isEmpty(groupName)) {
            return false;
        }
        CellGroup cellGroup = getCellGroup(groupName);
        return Objects.nonNull(cellGroup);
    }

    public CellGroup getCellGroup(String groupName) {
        if (Objects.isNull(rowCell) && Objects.isNull(rows)) {
            throw new RuntimeException("缺失RowCell或Rows属性，请检查");
        }

        if (StringUtils.isEmpty(groupName)) {
            return null;
        }

        CellGroup[] groups = null;
        if (Objects.nonNull(rowCell)) {
            groups = rowCell.groups();
        } else if (Objects.nonNull(rows)) {
            groups = rows.groups();
        }

        if (Objects.isNull(groups)) {
            return null;
        }

        for (CellGroup group : groups) {
            if (Objects.equals(group.name(), groupName)) {
                return group;
            }
        }
        return null;
    }

    public String getFieldName() {
        if (Objects.isNull(this.field)) {
            return null;
        }
        return field.getName();
    }
}
