package com.ongames.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.*;

@Entity
public class Plataforma implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private int id;
    @Column (nullable=false, length=20, unique=true)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
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
        final Plataforma other = (Plataforma) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }    
}
