package com.phonestore.validator.handler;

import com.phonestore.util.DataUtil;
import com.phonestore.validator.annotation.Min;

public class MinHandler implements IValidationHandler<Min> {

    @Override
    public String handle(Min min, Object dataInvoked) {
        double i = DataUtil.safeToDouble(dataInvoked);
        if (min.value() > i) {
            return min.message();
        }
        return null;
    }
}