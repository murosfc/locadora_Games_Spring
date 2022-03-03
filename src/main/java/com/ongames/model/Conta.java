package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Conta implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false, length = 80, unique =true)
    private String email;
    @Column(nullable=false, length = 32)
    private String senha;
   
    @OneToOne() @JoinColumn(name = "id_jogo")
    private Jogo jogo;
    
    @OneToOne @JsonBackReference 
    private Aluguel aluguel;

    public Conta(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Conta() {
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
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
        final Conta other = (Conta) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
