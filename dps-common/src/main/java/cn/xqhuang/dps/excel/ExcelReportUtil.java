package cn.xqhuang.dps.excel;


import cn.xqhuang.dps.excel.annotation.CellGroup;
import cn.xqhuang.dps.excel.annotation.RowCell;
import cn.xqhuang.dps.excel.annotation.Rows;
import cn.xqhuang.dps.utils.ReflectionUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ExcelReportUtil {
    private static final Map<Class<?>, List<ReportModelPropertyDefinition>> DECLARED_MODEL_DEFINITION_CACHE = new ConcurrentHashMap(256);


    public static Map<Class<?>, List<ReportModelPropertyDefinitionWrapper>> getModelProperties(final Class<?> clazz,
                                                                                               final String groupName) {
        final Map<Class<?>, List<ReportModelPropertyDefinitionWrapper>> propertyMap = new HashMap<>();
        fillTargetProperties(clazz, groupName, propertyMap);
        return propertyMap;
    }

    public static Map<Class<?>, List<ReportModelPropertyDefinitionWrapper>> getModelProperties(final Class<?> clazz,
                                                                                               final Map<Class<?>, Map<String, Integer>> fieldMap) {
        final Map<Class<?>, List<ReportModelPropertyDefinitionWrapper>> propertyMap = new HashMap<>();
        fillTargetProperties(clazz, fieldMap, propertyMap);
        return propertyMap;
    }

    public static List<ReportModelPropertyDefinition> getModelDefinition(Class<?> clazz) {
        List<ReportModelPropertyDefinition> propertyDefinitions = DECLARED_MODEL_DEFINITION_CACHE.get(clazz);
        if (Objects.nonNull(propertyDefinitions)) {
            return propertyDefinitions;
        } else {
            synchronized (ExcelReportUtil.class) {
                propertyDefinitions = DECLARED_MODEL_DEFINITION_CACHE.get(clazz);
                if (CollectionUtils.isEmpty(propertyDefinitions)) {
                    propertyDefinitions = getModelDefinitionByClass(clazz);
                    DECLARED_MODEL_DEFINITION_CACHE.put(clazz, propertyDefinitions);
                }
            }
        }

        return propertyDefinitions;
    }

    private static void fillTargetProperties(final Class<?> clazz,
                                             final Map<Class<?>, Map<String, Integer>> fieldMap,
                                             final Map<Class<?>, List<ReportModelPropertyDefinitionWrapper>> propertyMap) {
        List<ReportModelPropertyDefinition> properties = getModelDefinition(clazz);
        if (CollectionUtils.isEmpty(properties)) {
            return;
        }

        final Map<String, Integer> curClazzFieldMap = fieldMap.get(clazz);
        if (CollectionUtils.isEmpty(curClazzFieldMap)) {
            return;
        }

        final List<ReportModelPropertyDefinitionWrapper> targetProperties = new ArrayList<>();
        properties.forEach(property -> {
            if (!curClazzFieldMap.containsKey(property.getFieldName())) {
                return;
            }
            if (Objects.nonNull(property.getRows())) {
                fillTargetProperties(property.getRows().value(), fieldMap, propertyMap);
            }

            ReportModelPropertyDefinitionWrapper propertyDefinitionWrapper = new ReportModelPropertyDefinitionWrapper(property, null, curClazzFieldMap.get(property.getFieldName()));
            targetProperties.add(propertyDefinitionWrapper);
        });
        propertyMap.put(clazz, targetProperties);
    }

    private static void fillTargetProperties(final Class<?> clazz,
                                             final String groupName,
                                             final Map<Class<?>, List<ReportModelPropertyDefinitionWrapper>> propertyMap) {
        List<ReportModelPropertyDefinition> properties = getModelDefinition(clazz);
        if (CollectionUtils.isEmpty(properties)) {
            return;
        }

        final List<ReportModelPropertyDefinitionWrapper> targetProperties = new ArrayList<>();
        properties.forEach(property -> {
            if (!StringUtils.isEmpty(groupName) && !property.containsGroup(groupName)) {
                return;
            }

            if (Objects.nonNull(property.getRows())) {
                fillTargetProperties(property.getRows().value(), groupName, propertyMap);
            }

            int index = 0;
            CellGroup cellGroup = property.getCellGroup(groupName);
            if (Objects.nonNull(cellGroup)) {
                index = cellGroup.index();
            } else if (Objects.nonNull(property.getRowCell())) {
                index = property.getRowCell().index();
            }

            ReportModelPropertyDefinitionWrapper propertyDefinitionWrapper = new ReportModelPropertyDefinitionWrapper(property, groupName, index);
            targetProperties.add(propertyDefinitionWrapper);
        });
        propertyMap.put(clazz, targetProperties);
    }


    private static List<ReportModelPropertyDefinition> getModelDefinitionByClass(Class clazz) {
        List<Field> normalFields = ReflectionUtils.findField(clazz, RowCell.class);
        List<Field> listFields = ReflectionUtils.findField(clazz, Rows.class);

        if (CollectionUtils.isEmpty(normalFields) && CollectionUtils.isEmpty(listFields)) {
            return Collections.emptyList();
        }

        List<ReportModelPropertyDefinition> properties = new ArrayList<>();

        if (!CollectionUtils.isEmpty(normalFields)) {
            for (Field field : normalFields) {
                Class<?> fieldClazz = field.getType();
                if (fieldClazz.isArray() || Collection.class.isAssignableFrom(fieldClazz) || Map.class.isAssignableFrom(fieldClazz)) {
                    continue;
                }
                RowCell rowCell = field.getAnnotation(RowCell.class);
                ReportModelPropertyDefinition property = new ReportModelPropertyDefinition();
                property.setClazz(fieldClazz);
                property.setRowCell(rowCell);
                property.setField(field);
                properties.add(property);
            }
        }


        if (!CollectionUtils.isEmpty(listFields)) {
            for (Field field : listFields) {
                Class<?> fieldClazz = field.getType();
                if (fieldClazz.isArray() || Collection.class.isAssignableFrom(fieldClazz) || Map.class.isAssignableFrom(fieldClazz)) {
                    Rows rows = field.getAnnotation(Rows.class);
                    ReportModelPropertyDefinition property = new ReportModelPropertyDefinition();
                    property.setClazz(fieldClazz);
                    property.setRows(rows);
                    property.setField(field);
                    properties.add(property);
                }
            }
        }
        return CollectionUtils.isEmpty(properties) ? Collections.emptyList() : Arrays.asList(properties.toArray(new ReportModelPropertyDefinition[properties.size()]));
    }
}
