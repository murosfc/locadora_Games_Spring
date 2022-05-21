package com.ongames.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class LocadoraViewControllerAdvice {
    
    @ExceptionHandler(Exception.class)
    public String erroException(Exception e, Model model){
        model.addAttribute("msgErros", e.getMessage());
        return ("error");
    }
    
}
