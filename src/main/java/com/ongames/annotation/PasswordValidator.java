package com.ongames.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordValidator implements ConstraintValidator<Password, String> {

    

    @Override
    public boolean isValid(String pass, ConstraintValidatorContext cvc) {        
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$";
        if (pass == null){
            return false;
        }
        return pass.matches(regex);
    }
    
    public static boolean isValid(String senha) {
         String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$";
        if (senha == null){
            return false;
        }
        return senha.matches(regex);
    }
    
}
