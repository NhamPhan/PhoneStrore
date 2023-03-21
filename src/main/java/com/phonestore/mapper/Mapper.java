package com.phonestore.mapper;

import com.phonestore.mapper.annotation.DBColumn;
import com.phonestore.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Mapper<T> {

    private Class<T> clazz;

    public Mapper(Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("type cannot be null");
        }
        this.clazz = clazz;
    }

    public T mapAsObject(ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Field[] fields = clazz.getDeclaredFields();
        T t = clazz.getConstructor().newInstance();
        for (Field field : fields) {

            if (!field.isAnnotationPresent(DBColumn.class)) {
                continue;
            }
            DBColumn databaseColumn = field.getAnnotation(DBColumn.class);
            String setterName = ReflectionUtil.getSetter(field);
            Method setterMethod = clazz.getDeclaredMethod(setterName, field.getType());

            String columnName = Objects.nonNull(databaseColumn.name()) ? databaseColumn.name() : field.getName();
            Object columnResult = resultSet.getObject(columnName, field.getType());
            setterMethod.invoke(t, columnResult);
        }
        return t;
    }

    public List<T> mapAsList(ResultSet resultSet) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(this.mapAsObject(resultSet));
        }
        return list;
    }
}