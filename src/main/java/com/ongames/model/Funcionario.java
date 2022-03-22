package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import javax.validation.constraints.Pattern;

@Entity
public class Funcionario extends Pessoa implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Column(nullable=false, length=15, unique=true)
    private String nick;
    @Column(nullable=false, length=15, unique=true)
    @Pattern(regexp="\\(?\\d{2,}\\)?[ -]?\\d{4,}[\\-\\s]?\\d{4}")
    private String whatsapp;
    
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
