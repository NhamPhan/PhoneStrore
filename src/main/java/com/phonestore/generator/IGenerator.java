package com.phonestore.generator;

import com.phonestore.mapper.annotation.ColumnId;
import com.phonestore.mapper.annotation.DBColumn;
import com.phonestore.mapper.annotation.DBTable;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IGenerator<T> {
    String selectAll();
    String selectBy(String... columns);
    String selectById();
    String insert();
    String updateById();
    String updateBy(String... columns);
    String deleteById();
    String deleteBy(String... columns);
    String count();

    default String getTableName(Class<T> table) {
        String tableName = table.getSimpleName();
        if (table.isAnnotationPresent(DBTable.class)) {
            DBTable tableAnnotaion = table.getAnnotation(DBTable.class);
            tableName = tableAnnotaion.name();
        }
        return tableName;
    }

    default List<String> getColumnName(Class<T> table, Predicate<Field> moreCondition) {
        if (Objects.isNull(moreCondition)) {
            moreCondition = (field) -> true;
        }
        return Stream.of(table.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(DBColumn.class))
                .filter(moreCondition)
                .map(field -> field.getAnnotation(DBColumn.class))
                .map(DBColumn::name)
                .collect(Collectors.toList());
    }

    default List<String> getColumnName(Class<T> table) {
        return this.getColumnName(table, null);
    }

    default void checkAnnotatedId(Class<T> table) {
        List<Field> fieldsAnnotatedId = Stream.of(table.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ColumnId.class))
                .collect(Collectors.toList());

        if (fieldsAnnotatedId.isEmpty()) {
            throw new RuntimeException("not found id in: " + table.getSimpleName());
        }
        if (fieldsAnnotatedId.size() > 1) {
            throw new RuntimeException("greater than one ID in: " + table.getSimpleName());
        }
    }

    default String getColumnId(Class<T> table) {
        Field columnId = Stream.of(table.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ColumnId.class))
                .findFirst().orElseThrow(() -> new RuntimeException("not found id column in entity"));

        String idColumnName = columnId.getName();
        if (columnId.isAnnotationPresent(DBColumn.class)) {
            DBColumn annotation = columnId.getAnnotation(DBColumn.class);
            idColumnName = annotation.name();
        }
        return idColumnName;
    }

}


