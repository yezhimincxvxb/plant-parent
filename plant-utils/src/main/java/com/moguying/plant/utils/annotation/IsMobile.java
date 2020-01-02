package com.moguying.plant.utils.annotation;


import com.moguying.plant.utils.validator.IsMobileValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile {

    String message() default "手机号码格式错误";

    boolean isRequired() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

