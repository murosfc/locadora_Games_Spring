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
public class Cliente extends Pessoa implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Column(length = 8)
    @Pattern(regexp= "[0-9]{8}", message="Digite apenas números")
    private String cep;
    @Column(length = 10)
    @NotNull (message = "É necessário informar o número, ou S/N, caso não tenha")
    @Size(min=1, max=10, message="Limite de caracteres, mínimo 1 e máximo 10") 
    private String numero;
    @Column(length = 50)
    @Size(min=0, max=10, message="O complemento precisa ser menor que 50 caracteres")    
    private String complemento;    
    
    @JsonIgnore @OneToMany(mappedBy = "cliente")   
    private List<Aluguel> alugueis = new ArrayList<>();

    public Cliente(String cep, String numero, String complemento, String cpf, String nome, String email, String senha) {
        super(cpf, nome, email, senha);
        this.cep = cep;
        this.numero = numero;
        this.complemento = complemento;
    }
    
    public Cliente() {
        super();
    }    

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(List<Aluguel> alugueis) {
        this.alugueis = alugueis;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }    
}
