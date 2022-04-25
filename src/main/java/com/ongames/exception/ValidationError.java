package com.ongames.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends Error{
    
    private List<PropertyError> errors;

    public ValidationError(LocalDateTime timestamp, Integer status, String erro, String mensagem, String path) {
        super(timestamp, status, erro, mensagem, path);
        this.errors = new ArrayList<>();
    }

    public List<PropertyError> getErrors() {
        return errors;
    }

    public void setErrors(List<PropertyError> errors) {
        this.errors = errors;
    }
    
    
    
}
