package com.moguying.plant.utils.validator;

import com.moguying.plant.utils.annotation.IsMobile;
import com.moguying.plant.utils.CommonUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.isEmpty(phone))
            return false;
        return CommonUtil.INSTANCE.isPhone(phone);
    }


}
