package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Funcionario extends Pessoa implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Column(nullable=false, length=20, unique=true)
    @NotNull (message="O nick precisa ser informado")
    @Size(min=4, max=20, message= "O nick deve conter de 4 a 20 caracteres")
    private String nick;
    @Column(nullable=false, length=15, unique=true)
    @NotNull (message= "O Whatsapp precisa ser informado")
    @Pattern(regexp="\\(?\\d{2,}\\)?[ -]?\\d{4,}[\\-\\s]?\\d{4}", message="formato de telefone inválido")
    private String whatsapp;
    
    @JsonIgnore @OneToMany(mappedBy = "contatoSuporte") 
    private List<Aluguel> alugueis = new ArrayList<>();

    public Funcionario() {
        super();
    }

    public Funcionario(String nick, String whatsapp, String cpf, String nome, String email, String senha) {
        super(cpf, nome, email, senha);
        this.nick = nick;
        this.whatsapp = whatsapp;
    }

    public Funcionario(String superHiggs, String string, String string0, String hugo_Villela_Silva, String superhiggsongamescom) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
