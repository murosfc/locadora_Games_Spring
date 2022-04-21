package com.ongames.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Aluguel implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)    
    @NotNull(message = "Data de início e do fim do aluguel são obrigatórias.")    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInicioAluguel, dataFimAluguel;
    
    @ManyToOne @JoinColumn(name = "id_cliente")   
    private Cliente cliente;
    @OneToMany @JsonIgnore   
    private List<Conta> contas = new ArrayList<>();
    @ManyToOne @JoinColumn(name = "id_funcionario")    
    private Funcionario contatoSuporte;
    @OneToOne(cascade = CascadeType.ALL) @JoinColumn(name = "id_pagamento", referencedColumnName = "id")   
    private Pagamento pagamento;

    public Aluguel(LocalDate dataInicioAluguel, LocalDate dataFimAluguel) {
        this.dataInicioAluguel = dataInicioAluguel;
        this.dataFimAluguel = dataFimAluguel;
        pagamento.setValor(0f);
        this.pagamento = new Pagamento(this);
    }

    public Aluguel() {
        this.pagamento = new Pagamento(this);
        pagamento.setValor(0f);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> contas) {
        this.contas = contas;  
        float total = pagamento.getValor();
        Conta tempConta = new Conta();
        for (Conta conta : contas) {            
            total = total + conta.getJogo().getValor();
        }
        pagamento.setValor(total);
    }
     
    public float getValor(){
        return this.pagamento.getValor();
    }
    

    public Funcionario getContatoSuporte() {
        return contatoSuporte;
    }

    public void setContatoSuporte(Funcionario contatoSuporte) {
        this.contatoSuporte = contatoSuporte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicioAluguel() {
        return dataInicioAluguel;
    }

    public void setDataInicioAluguel(LocalDate dataInicioAluguel) {
        this.dataInicioAluguel = dataInicioAluguel;
        LocalDate datafim = this.dataInicioAluguel;        
        this.dataFimAluguel = datafim.plusDays(7);
    }

    public LocalDate getDataFimAluguel() {
        return dataFimAluguel;
    }

    public void setDataFimAluguel(LocalDate dataFimAluguel) {
        LocalDate dtinicio = this.dataInicioAluguel;
        if (dataFimAluguel.isBefore(dtinicio.plusDays(7)))
        {
            throw new RuntimeException("Data fim de alugel não pode ser anterior a 7 dias após o início");
        }
        this.dataFimAluguel = dataFimAluguel;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
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
        final Aluguel other = (Aluguel) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    } 
    
}
