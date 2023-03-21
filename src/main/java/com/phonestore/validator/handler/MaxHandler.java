package com.phonestore.validator.handler;

import com.phonestore.util.DataUtil;
import com.phonestore.validator.annotation.Max;

public class MaxHandler implements IValidationHandler<Max> {

    @Override
    public String handle(Max max, Object dataInvoked) {
        double i = DataUtil.safeToDouble(dataInvoked);
        if (max.value() < i) {
            return max.message();
        }
        return null;
    }
}