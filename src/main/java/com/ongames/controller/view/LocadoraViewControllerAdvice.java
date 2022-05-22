package com.ongames.controller.view;

import com.ongames.exception.NotAllowedException;
import com.ongames.exception.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class LocadoraViewControllerAdvice {
    
    @ExceptionHandler(Exception.class)
    public String erroException(Exception e, Model model){
        model.addAttribute("msgErros", "Erro: "+ e.getMessage());
        return ("error");
    }
    
    @ExceptionHandler(NotAllowedException.class)
    public String erroNotAllowed(Exception e, Model model){
        model.addAttribute("msgErros", "Erro: "+ e.getMessage());
        return ("error");
    }
    
    @ExceptionHandler(NotFoundException.class)
    public String erroNotFoud(Exception e, Model model){
        model.addAttribute("msgErros", "Erro: "+ e.getMessage());
        return ("error");
    }
    
}
