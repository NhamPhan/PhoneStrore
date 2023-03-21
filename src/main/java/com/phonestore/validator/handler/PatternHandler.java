package com.phonestore.validator.handler;

import com.phonestore.util.DataUtil;
import com.phonestore.validator.annotation.Pattern;

public class PatternHandler implements IValidationHandler<Pattern> {

    @Override
    public String handle(Pattern pattern, Object dataInvoked) {
        if (!DataUtil.safeToString(dataInvoked).trim().matches(pattern.value())) {
            return pattern.message();
        }
        return null;
    }
}