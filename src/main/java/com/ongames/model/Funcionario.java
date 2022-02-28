package com.ongames.model;

import java.io.Serializable;
import java.util.List;

public class Funcionario extends Pessoa implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String nick, whatsapp;
    private List<Aluguel> alugueis;

    public Funcionario() {
        super();
    }

    public Funcionario(String nick, String whatsapp, String cpf, String nome, String email, String senha) {
        super(cpf, nome, email, senha);
        this.nick = nick;
        this.whatsapp = whatsapp;
    }
    
    public List<Aluguel> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(List<Aluguel> alugueis) {
        this.alugueis = alugueis;
    }
    
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }    
}
