package com.ongames.model;

import com.ongames.annotation.Password;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L; 
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable=false, length = 14, unique =true, updatable=false)
    @CPF (message ="O CPF precisa seguir o padrão 000.000.000-00")
    @NotNull (message ="CPF é obrigatório")
    private String cpf;
    @Column(nullable=false, length = 150)
    @NotNull (message ="O nome precisa ser informado")
    @Size(min=5, max=150, message="O nome precisa possuir entre 5 e 150 caracteres") 
    private String nome;
    @Column(nullable=false, length = 80, unique =true)
    @NotNull (message ="O e-mail precisa ser informado")
    @Email (message = "O e-mail precisa respeitar o padrão nome@provedor.xxx")
    private String email;
    @Column(nullable=false, length = 32) //32 Ã© o tamanho do hash que serÃ¡ salvo no banco de dados
    @Password
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

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
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
