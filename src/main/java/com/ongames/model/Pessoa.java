package com.ongames.model;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L; 
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable=false, length = 11, unique =true, updatable=false)
    @CPF
    private String cpf;
    @Column(nullable=false, length = 150)
    @NotNull
    @Size(min=5, max=150) 
    @Pattern(regexp="^[a-zA-Z0-9]{3}",message="length must be 3") 
    private String nome;
    @Column(nullable=false, length = 80, unique =true)
    @Email
    private String email;
    @Column(nullable=false, length = 32) //32 é o tamanho do hash que será salvo no banco de dados
    @NotNull
    @Size(min=6, max=20) 
    private String senha; 
    

    public Pessoa(String cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Pessoa() {
    }    

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pessoa other = (Pessoa) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }   
}
