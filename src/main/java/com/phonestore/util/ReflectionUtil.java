package com.phonestore.util;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

public class ReflectionUtil {

    public static final String TYPE_SETTER = "set";
    public static final String TYPE_GETTER = "get";

    private ReflectionUtil() {}

    public static <T> T requestParamToObject(HttpServletRequest request, Class<T> clazz) {

        try {
            T t = clazz.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                Class<?> type = field.getType();
                String parameter = request.getParameter(field.getName());
                if (Objects.isNull(parameter) || parameter.length() == 0) {
                    continue;
                }

                String methodName = getMethodName(field, TYPE_SETTER);
                Method declaredMethod = clazz.getDeclaredMethod(methodName, type);

                if (Date.class.equals(type)) {
                    Date format = DateUtil.format(parameter);
                    declaredMethod.invoke(t, format);
                }

                if (java.sql.Date.class.equals(type)) {
                    Date format = DateUtil.format(parameter);
                    declaredMethod.invoke(t, new java.sql.Date(format.getTime()));
                }

                if (Timestamp.class.equals(type)) {
                    Date format = DateUtil.format(parameter);
                    declaredMethod.invoke(t, new Timestamp(format.getTime()));
                }

                if (Instant.class.equals(type)) {
                    Date format = DateUtil.format(parameter);
                    declaredMethod.invoke(t, format.toInstant());
                }

                if (Long.class.equals(type)) {
                    Long aLong = Long.valueOf(parameter);
                    declaredMethod.invoke(t, aLong);
                }

                if (Integer.class.equals(type)) {
                    Integer integer = Integer.valueOf(parameter);
                    declaredMethod.invoke(t, integer);
                }

                if (Double.class.equals(type)) {
                    Double aDouble = Double.valueOf(parameter);
                    declaredMethod.invoke(t, aDouble);
                }

                if (Float.class.equals(type)) {
                    Float aFloat = Float.valueOf(parameter);
                    declaredMethod.invoke(t, aFloat);
                }

                if (String.class.equals(type)) {
                    declaredMethod.invoke(t, parameter);
                }
            }
            return t;
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String getMethodName(Field field, String type) {
        String fieldName = field.getName();
        return type + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static String getSetter(Field field) {
        return getMethodName(field, TYPE_SETTER);
    }

    public static String getGetter(Field field) {
        return getMethodName(field, TYPE_GETTER);
    }
}