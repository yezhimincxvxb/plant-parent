package com.moguying.plant.core.resolver;

import com.moguying.plant.constant.MessageEnum;
import com.moguying.plant.core.entity.ResponseData;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidateExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseData<String> validateExceptionHandler(Exception e){
        if(e instanceof MethodArgumentNotValidException){
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
            return new ResponseData<>(allErrors.get(0).getDefaultMessage(), MessageEnum.ERROR.getState());
        }
        return new ResponseData<>(MessageEnum.ERROR.getMessage(),MessageEnum.ERROR.getState());
    }
}
