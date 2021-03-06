package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
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
    private Long id;        
    
    @Column(nullable=true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPagamento;
    @Column(nullable=false, scale=2)
    @NotNull (message ="Valor do pagamento é obrigatório")
    private float valorTotal = 0f;
    @Column( length = 32)    
    private String validacao;     
     
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pagamento")    
    @JsonBackReference  
    private Aluguel aluguel;
   
    public Long getId() {
        return id;
    }

    public Pagamento() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;        
    }
    
    public boolean iniciaAluguel(){
        if (this.aluguel.getDataInicioAluguel() == null && this.getDataPagamento() != null){
           this.aluguel.setDataInicioAluguel(this.getDataPagamento()); //se o pagamento foi confirmado então o aluguel iniciou
           LocalDate dtfim = (LocalDate) this.getDataPagamento();
           this.aluguel.setDataFimAluguel(dtfim.plusDays(7));
           return true;
        } 
        return false;
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
