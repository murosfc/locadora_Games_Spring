package com.ongames.controller.apirest;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ongames.exception.Error;
import com.ongames.exception.NotAllowedException;
import com.ongames.exception.NotFoundException;
import com.ongames.exception.PropertyError;
import com.ongames.exception.ValidationError;
import java.time.LocalDateTime;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestController;

@RestControllerAdvice(annotations = RestController.class)
public class LocadoraRestControllerAdvice {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> erroPadrao (Exception e, HttpServletRequest request){
        Error erro = new Error(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                e.getMessage(),
                request.getRequestURI());       
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
    
    @ExceptionHandler(NotAllowedException.class)
    public ResponseEntity<?> erroPadrao (NotAllowedException e, HttpServletRequest request){
        Error erro = new Error(LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                e.getMessage(),
                request.getRequestURI());       
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> erroValidacao (ConstraintViolationException e, HttpServletRequest request){
        ValidationError erro = new ValidationError(LocalDateTime.now(), 
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.name(),                
                "Erro de validação",                
                request.getRequestURI());
        for (ConstraintViolation cv : e.getConstraintViolations()){
            PropertyError p = new PropertyError(cv.getPropertyPath().toString(),cv.getMessage());
            erro.getErrors().add(p);
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> erroValidacao (MethodArgumentNotValidException e, HttpServletRequest request){
        ValidationError erro = new ValidationError(LocalDateTime.now(), 
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.name(),                
                "Erro de validação",                
                request.getRequestURI());
        for (FieldError fe : e.getBindingResult().getFieldErrors()){
            PropertyError p = new PropertyError(fe.getField() ,fe.getDefaultMessage());
            erro.getErrors().add(p);
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> erroPadrao (NotFoundException e, HttpServletRequest request){
        Error erro = new Error(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage(),
                request.getRequestURI());       
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }
  
    
}
