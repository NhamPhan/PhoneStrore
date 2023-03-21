package com.phonestore.generator;

import com.phonestore.mapper.annotation.IgnoreInsert;
import com.phonestore.mapper.annotation.IgnoreUpdate;
import com.phonestore.util.DataUtil;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MysqlGenerator<T> implements IGenerator<T> {

    private Class<T> table;

    public MysqlGenerator(Class<T> table) {
        if (Objects.isNull(table)) {
            throw new IllegalArgumentException("Table cannot be null");
        }
        this.checkAnnotatedId(table);
        this.table = table;
    }

    @Override
    public String selectAll() {
        String tableName = this.getTableName(this.table);
        return new StringBuilder("SELECT * FROM ")
                .append(tableName).toString();
    }

    @Override
    public String selectBy(String...columns) {
        String tableName = this.getTableName(this.table);
        List<String> columnList = Stream.of(columns).map(column -> column.concat(" = ?")).collect(Collectors.toList());
        return new StringBuilder("SELECT * FROM ")
                .append(tableName)
                .append(" WHERE ")
                .append(String.join(" AND ", columnList))
                .toString();
    }

    @Override
    public String selectById() {
        return this.selectBy(this.getColumnId(this.table));
    }

    @Override
    public String insert() {
        String tableName = this.getTableName(this.table);
        List<String> columnName =
                this.getColumnName(this.table, field -> !field.isAnnotationPresent(IgnoreInsert.class));

        return new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append("(")
                .append(String.join(",", columnName))
                .append(") VALUES(")
                .append(DataUtil.repeat(columnName.size() - 1, "?,"))
                .append("?)").toString();
    }

    @Override
    public String updateById() {
        return this.updateBy(
                this.getColumnId(this.table)
        );
    }

    @Override
    public String updateBy(String...columns) {
        String tableName = this.getTableName(this.table);
        List<String> columnName = this.getColumnName(this.table, field -> !field.isAnnotationPresent(IgnoreUpdate.class));

        columnName = columnName.stream().map(cl -> cl.concat(" = ?")).collect(Collectors.toList());

        List<String> wheres = Stream.of(columns).map(column -> column.concat(" = ?")).collect(Collectors.toList());
        return new StringBuilder("UPDATE ").append(tableName)
                .append(" SET ")
                .append(String.join(",", columnName))
                .append(" WHERE ")
                .append(String.join(" AND ", wheres))
                .toString();
    }

    @Override
    public String deleteById() {
        return this.deleteBy(this.getColumnId(this.table));
    }

    @Override
    public String deleteBy(String...columns) {
        String tableName = this.getTableName(this.table);
        List<String> wheres = Stream.of(columns).map(column -> column.concat(" = ?")).collect(Collectors.toList());

        return new StringBuilder("DELETE FROM ").append(tableName)
                .append(" WHERE ")
                .append(String.join(" AND ", wheres))
                .toString();
    }

    @Override
    public String count() {
        String tableName = this.getTableName(this.table);
        return new StringBuilder("SELECT count(*) FROM ").append(tableName).toString();
    }
}