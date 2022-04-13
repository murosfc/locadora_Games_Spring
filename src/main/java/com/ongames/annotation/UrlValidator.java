package com.ongames.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<UrlValidation, String>{

    @Override
    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        String regex1 = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        String regex2 = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        if (value == null){
            return false;
        }
        return (value.matches(regex1) || value.matches(regex2)) ;
    }
    
}
