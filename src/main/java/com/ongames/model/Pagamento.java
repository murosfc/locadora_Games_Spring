package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.persistence.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Pagamento implements Serializable{
    private static final long serialVersionUID = 1L;    
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;        
      
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPagamento;
    @Column(nullable=false, scale=2)
    @NotNull (message ="Valor do pagamento é obrigatório")
    private float valorTotal;
    @Column( length = 32)    
    private String validacao;     
     
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pagamento")    
    @NotNull (message ="Um pagamento precisa ser associado a um aluguel")
    @JsonBackReference  
    private Aluguel aluguel;
   
    public long getId() {
        return id;
    }

    public Pagamento() {
    }

    public Pagamento(Aluguel aluguel) {
        this.aluguel = aluguel;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
        this.aluguel.setDataInicioAluguel(dataPagamento); //se o pagamento foi confirmado então o aluguel iniciou
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valor) {
        this.valorTotal = valor;
    }

    public String getValidacao() {
        return validacao;
    }
    
    public void setValidacao(String validacao) {
        this.validacao = validacao;        
    }    
    
    public Aluguel getAluguel() {
        return aluguel;
    }

    public void setAluguel(Aluguel alguel) {
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
