package com.ongames.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Error implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private LocalDateTime timestamp;
    private Integer status;
    private String erro;
    private String mensagem;
    private String path;

    public Error(LocalDateTime timestamp, Integer status, String erro, String mensagem, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.erro = erro;
        this.mensagem = mensagem;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
    
}
