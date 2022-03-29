package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Pagamento implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Data do pagamento é obrigatória.")
    @FutureOrPresent(message = "Data de inicio do aluguel deve ser atual ou no futuro.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Calendar dataPagamento;
    @Column(nullable=false, scale=2)
    @NotNull (message ="Valor do pagamento é obrigatório")
    private float valor;
    @Column( length = 32)    
    private String validacao;
    
    @OneToOne(mappedBy = "pagamento") @JsonBackReference 
    @Cascade(CascadeType.ALL)
    private Aluguel aluguel;

    public long getId() {
        return id;
    }

    public Pagamento() {
    }

    public Pagamento(Aluguel aluguel) {
        this.aluguel = aluguel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Calendar dataPagamento) {
        this.dataPagamento = dataPagamento;
        this.aluguel.setDataInicioAluguel(dataPagamento); //se o pagamento foi confirmado então o aluguel iniciou
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getValidacao() {
        return validacao;
    }
    
    public void setValidacao(String validacao) {
        this.validacao = validacao;        
    }

    public Aluguel getAlguel() {
        return aluguel;
    }

    public void setAlguel(Aluguel alguel) {
        this.aluguel = alguel;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Pagamento other = (Pagamento) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
