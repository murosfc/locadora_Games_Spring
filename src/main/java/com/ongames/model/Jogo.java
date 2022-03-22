package com.ongames.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Jogo implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length=22, unique=true)
    @NotNull
    @Size(min=1, max=22) 
    private String sku;
    @Column(nullable=false, length=50)
    @NotNull
    @Size(min=1, max=50) 
    private String titulo;
    @Lob
    @Column(nullable=false, length=512)
    @NotNull
    @Pattern (regexp="https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)")
    private String imgURL;
    @Column(nullable=false, scale=2)
    @NotNull
    @Positive
    private float valor;
    @Column(nullable=false, length=20)   
    @Enumerated(EnumType.STRING)
    @NotNull
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
        hash = 11 * hash + this.id;
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
