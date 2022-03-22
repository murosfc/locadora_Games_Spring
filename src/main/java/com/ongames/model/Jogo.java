package com.ongames.model;

import annotation.UrlValidation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


@Entity
public class Jogo implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length=22, unique=true)
    @NotNull (message="O sku precisa ser informado")
    @Size(min=1, max=22) 
    private String sku;
    @Column(nullable=false, length=50)
    @NotNull (message="O título precisa ser informado")
    @Size(min=1, max=50) 
    private String titulo;
    @Lob
    @Column(nullable=false, length=512)
    @NotNull (message="É obrigatório inserir o link da imagem do jogo")
    @UrlValidation
    private String imgURL;
    @Column(nullable=false, scale=2)
    @NotNull (message="Informar o valor do aluguel é obrigatório")
    @Positive (message ="O valor precisa ser positivo")
    private float valor;
    @Column(nullable=false, length=20)   
    @Enumerated(EnumType.STRING)
    @NotNull (message="A nick plataforma ser informada")
    private PlataformaEnum plataforma;
    
    @OneToMany(fetch = FetchType.EAGER)     
    private List<Categoria> categorias = new ArrayList<>();
    
    public Jogo() {
    }

    public Jogo(String sku, String titulo, String imgURL, float valor) {
        this.sku = sku;
        this.titulo = titulo;
        this.imgURL = imgURL;
        this.valor = valor;
    } 

    public PlataformaEnum getPlataforma() {
        return plataforma;
    }

    public void setPlataformaEnum (PlataformaEnum plataforma) {
        this.plataforma = plataforma;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }       
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
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
        final Jogo other = (Jogo) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
