package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(value ="senha", allowGetters = false, allowSetters = true)
public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L; 
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable=false, length = 14, unique =true, updatable=false)
    @CPF (message ="O CPF precisa conter 11 numeros, e apenas números")
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
    //@Password com a criptografia da senha a validação precisa ser em níveis superiores
    @Column(nullable=false, length = 60)    
    private String senha; 
    
    @ManyToMany(fetch = FetchType.EAGER)
    @Size (min = 1, message= "Pessoa precisa ter ao menos uma perimissão")
    private List<Permissao> permissoes = new ArrayList<>();  
    

    public Pessoa(String cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Pessoa() {
    } 
    
    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
