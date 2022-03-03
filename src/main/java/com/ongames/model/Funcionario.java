package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Funcionario extends Pessoa implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Column(nullable=false, length=15)
    private String nick, whatsapp;
    
    @JsonBackReference @OneToMany(mappedBy = "contatoSuporte") 
    private List<Aluguel> alugueis = new ArrayList<>();

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
