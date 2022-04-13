package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;import javax.persistence.OneToOne;
;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Conta implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column(nullable=false, length = 80, unique =true)
    @Email (message = "O e-mail precisa respeitar o padrão nome@provedor.xxx")
    private String email;
    @Column(nullable=false, length = 32) //32 é o tamanho do hash que será salvo no banco de dados
    @NotNull (message="A senha precisa ser informada")
    @Size(min=6, max=20, message="A senha deve conter no mínimo 6 e no máximo 20 caracteres") 
    private String senha;
   
    @ManyToOne() @JoinColumn(name = "id_jogo")
    private Jogo jogo;
    
    @OneToOne @JoinColumn(name = "id_aluguel")
    private Aluguel aluguel;

    public Conta(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Aluguel getAluguel() {
        return aluguel;
    }

    public void setAluguel(Aluguel aluguel) {
        this.aluguel = aluguel;
    }

    public Conta() {
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        int hash = 3;
        hash = 71 * hash + (int) (this.id ^ (this.id >>> 32));
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
