package com.phonestore.validator;

import com.phonestore.util.ReflectionUtil;
import com.phonestore.validator.annotation.*;
import com.phonestore.validator.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Validator {

    private static final Logger logger = LoggerFactory.getLogger(Validator.class);

    private static Map<String, ? super IValidationHandler> validationContext;

    static {
        validationContext = new HashMap<>();
        startup();
    }

    private Validator() {}

    private static void startup() {
        addValidate(Min.class, new MinHandler());
        addValidate(Max.class, new MaxHandler());
        addValidate(Required.class, new RequiredHandler());
        addValidate(Pattern.class, new PatternHandler());
        addValidate(Range.class, new RangeHandler());
    }

    public static void addValidate(Class<? extends Annotation> aClass, IValidationHandler validateHandler) {
        logger.info("add handler validate: key-{}, handler-{}", aClass, validateHandler);
        if (Objects.isNull(aClass)) {
            throw new IllegalArgumentException("class cannot be null");
        }

        if (Objects.isNull(validateHandler)) {
            throw new IllegalArgumentException("handler cannot be null");
        }
        validationContext.put(aClass.getName(), validateHandler);
    }

    public static String validate(Object object) throws Exception {
        logger.info("validating object: {}", object);
        if (Objects.isNull(object)) {
            return null;
        }
        Class<?> clazz = object.getClass();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            String getterName = ReflectionUtil.getGetter(field);
            Method getterMethod = clazz.getDeclaredMethod(getterName);
            Object dataInvoked = getterMethod.invoke(object);

            for (Annotation annotation : field.getAnnotations()) {
                IValidationHandler validateHandler = (IValidationHandler) validationContext.get(annotation.annotationType().getName());
                if (Objects.isNull(validateHandler)) continue;
                String message = validateHandler.handle(annotation, dataInvoked);
                if (Objects.nonNull(message)) return message;
            }
        }
        return null;
    }
}


